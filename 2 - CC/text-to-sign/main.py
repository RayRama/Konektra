from flask import Flask, request, jsonify
from PIL import Image, UnidentifiedImageError
import os
import base64
import re
from waitress import serve
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

host = '127.0.0.1'
port = 5000

wordlist_path = os.path.join(os.path.dirname(__file__), 'data/wordlist.txt')
wordlist = open(wordlist_path, 'r').read().splitlines()

def preprocessing_text(text):
  text = text.lower()
  # remove extra whitespace, punktuation, and non-ascii chars
  text = re.sub(r'\s+', ' ', text)
  text = re.sub(r'[^\w\s]', '', text)
  text = re.sub(r'[^a-zA-Z]', ' ', text)
  # text = word_tokenize(text)
  return text.split(" ")

def text_to_list(text):
    result = []
    text = preprocessing_text(text)
    for word in text:
        if word in wordlist:
            result.append(word)
        else:
            for char in word:
                result.append(char)

    return result

def retrieve_images_by_titles(file_titles):
    images = []
    for file_title in file_titles:
        try:
            image_path = os.path.join(os.path.dirname(__file__), 'assets', file_title + '.gif')
            img = Image.open(image_path)
            images.append((file_title, img))  # Menambahkan nama file ke dalam tuple
        except FileNotFoundError:
            pass  
        except PermissionError:
            pass  
        except UnidentifiedImageError as e:
            print(f"UnidentifiedImageError: {e}")
            pass  # Lewati file yang menyebabkan kesalahan dan lanjutkan ke file berikutnya

    return images, 201 if images else 404

@app.route('/text_to_sign', methods=['GET'])
def text_to_sign():
    text = request.args.get('text', default='halo', type=str)

    # Mengubah teks menjadi list kata
    text_list = text_to_list(text)

    # Mengambil gambar dari direktori source
    images, status_code = retrieve_images_by_titles(text_list)

    if images:
        response_data = []

        for file_title, img in images:
            with open(os.path.join(os.path.dirname(__file__), 'assets', file_title + '.gif'), 'rb') as f:
                img_byte_array = f.read()
                img_base64 = base64.b64encode(img_byte_array).decode('utf-8')

            file_tanpa_ekstensi = os.path.splitext(file_title)[0].lower()

            response_data.append({'title': file_tanpa_ekstensi, 'image': img_base64})

        # Menggabungkan judul menjadi satu dictionary
        # combined_response = {
        #     'options': [{'title': entry['title']} for entry in response_data],
        #     'images': [entry['image'] for entry in response_data]
        # }
        
        combined_response = [
            {'title': entry['title'], 'image': entry['image']} for entry in response_data
        ]

        response = jsonify(combined_response)
        return response, 202
    else:
        return jsonify({'error': f"Tidak ada gambar ditemukan untuk judul yang diberikan."}), status_code

# @app.route('/get_images', methods=['GET'])
# def get_images():
    # file_titles = request.args.getlist('titles')
    # images, status_code = retrieve_images_by_titles(file_titles)

    # if images:
    #     response_data = []

    #     for file_title, img in images:
    #         img_byte_array = Image.Image.tobytes(img)
    #         img_base64 = base64.b64encode(img_byte_array).decode('utf-8')

    #         file_tanpa_ekstensi = os.path.splitext(file_title)[0].lower()

    #         response_data.append({'title': file_tanpa_ekstensi, 'image': img_base64})

    #     # Menggabungkan judul menjadi satu dictionary
    #     combined_response = {
    #         'options': [{'title': entry['title']} for entry in response_data],
    #         'images': [entry['image'] for entry in response_data]
    #     }

    #     response = jsonify(combined_response)
    #     return response, 202
    # else:
    #     return jsonify({'error': f"Tidak ada gambar ditemukan untuk judul yang diberikan."}), status_code

if __name__ == '__main__':
    serve(app, host=host, port=port)
