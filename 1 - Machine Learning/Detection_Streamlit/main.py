# pip install streamlit-webrtc opencv-python mediapipe opencv-python-headless av streamlit

from streamlit_webrtc import webrtc_streamer, VideoProcessorBase, RTCConfiguration, WebRtcMode
import cv2
import numpy as np
import streamlit as st
import tensorflow as tf
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import LSTM, Dense
import mediapipe as mp
import av

mp_holistic = mp.solutions.holistic # Holistic model
mp_drawing = mp.solutions.drawing_utils # Drawing utilities
mp_drawing_styles = mp.solutions.drawing_styles

# WebRTC settings
RTC_CONFIGURATION = RTCConfiguration(
    {"iceServers": [{"urls": ["stun:stun.l.google.com:19302"]}]}
)

# MediaPipe settings
holistic = mp_holistic.Holistic(
    min_detection_confidence=0.5, min_tracking_confidence=0.5)

def mediapipe_detection(image, model):
  image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB) # COLOR CONVERSION BGR 2 RGB
  image.flags.writeable = False                  # Image is no longer writeable
  results = model.process(image)                 # Make prediction
  image.flags.writeable = True                   # Image is now writeable
  image = cv2.cvtColor(image, cv2.COLOR_RGB2BGR) # COLOR COVERSION RGB 2 BGR
  return image, results

def draw_landmarks(image, results):
  mp_drawing.draw_landmarks(image, results.left_hand_landmarks, mp_holistic.HAND_CONNECTIONS) # Draw left hand connections
  mp_drawing.draw_landmarks(image, results.right_hand_landmarks, mp_holistic.HAND_CONNECTIONS) # Draw right hand connections

def draw_styled_landmarks(image, results):
  mp_drawing.draw_landmarks(image, results.left_hand_landmarks, mp_holistic.HAND_CONNECTIONS,
                            mp_drawing.DrawingSpec(color=(121,22,76), thickness=2, circle_radius=4),
                            mp_drawing.DrawingSpec(color=(121,44,250), thickness=2, circle_radius=2)
                            )
  
  mp_drawing.draw_landmarks(image, results.right_hand_landmarks, mp_holistic.HAND_CONNECTIONS,
                            mp_drawing.DrawingSpec(color=(245,117,66), thickness=2, circle_radius=4),
                            mp_drawing.DrawingSpec(color=(245,66,230), thickness=2, circle_radius=2)
                            )
  
def extract_keypoints(results):
  lh = np.array([[res.x, res.y, res.z] for res in results.left_hand_landmarks.landmark]).flatten() if results.left_hand_landmarks else np.zeros(21*3)
  rh = np.array([[res.x, res.y, res.z] for res in results.right_hand_landmarks.landmark]).flatten() if results.right_hand_landmarks else np.zeros(21*3)
  return np.concatenate([lh, rh])

# Load model
def load_model(model_path, actions):
  model = Sequential()
  model.add(LSTM(64, return_sequences=True, activation='relu', input_shape=(30,126)))
  model.add(LSTM(128, return_sequences=True, activation='relu'))
  model.add(LSTM(64, return_sequences=False, activation='relu'))
  model.add(Dense(64, activation='relu'))
  model.add(Dense(32, activation='relu'))
  model.add(Dense(actions.shape[0], activation='softmax'))

  model.compile(optimizer='Adam', loss='categorical_crossentropy', metrics=['categorical_accuracy'])
  model.load_weights(model_path)

  return model

def process_image(image, model, actions, sequence, sentences, threshold):
  image.flags.writeable = False
  image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
  results = holistic.process(image)
  image.flags.writeable = True
  image = cv2.cvtColor(image, cv2.COLOR_RGB2BGR)

  draw_styled_landmarks(image, results)
  keypoints = extract_keypoints(results)

  sequence.append(keypoints)
  sequence = sequence[-30:]

  if len(sequence) == 30:
    res = model.predict(np.expand_dims(sequence, axis=0))[0]
    if res[np.argmax(res)] > threshold:
      if len(sentences) > 0:
        if actions[np.argmax(res)] != sentences[-1]:
          sentences.append(actions[np.argmax(res)])
      else:
        sentences.append(actions[np.argmax(res)])
    
    if len(sentences) > 0:
      sentences = sentences[-1:]

  image = cv2.flip(image, 1)

  image = cv2.rectangle(image, (0,0), (640, 40), (245, 117, 16), -1)

  image = cv2.putText(image, ' '.join(sentences), (10, 30), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 255), 2, cv2.LINE_AA)

  return image

def main():
  class VideoTransformer(VideoProcessorBase):
    def __init__(self) -> None:
        self.actions = np.array(['halo', 'apa', 'kabar'])
        self.model = load_model('model.h5', self.actions)
        self.sequence = []
        self.sentences = []
        self.threshold = 0.3

    def recv(self, frame: av.VideoFrame) -> av.VideoFrame:
        img = frame.to_ndarray(format="bgr24")

        img = process_image(img, self.model, self.actions, self.sequence, self.sentences, self.threshold)

        return av.VideoFrame.from_ndarray(img, format="bgr24")

  webrtc_ctx = webrtc_streamer(
    key="loopback",
    mode=WebRtcMode.SENDRECV,
    rtc_configuration=RTC_CONFIGURATION,
    media_stream_constraints={"video": True, "audio": False},
    video_processor_factory=VideoTransformer,
    async_processing=True,
  )   

if __name__ == "__main__":
  main()

