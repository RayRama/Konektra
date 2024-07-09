## Konektra
[![Project Presentation](https://img.shields.io/badge/Project%20Presentation-Youtube-red)](https://youtu.be/3PeMEjTAM-k)

Konektra (Koneksi Tuna Rungu dan Tunawicara) comes as an innovative solution to bridge the **communication between** **"Teman Bisu or Teman Tuli"** and **"Teman Dengar"**. This application is specifically designed to facilitate interaction and enrich relationships between individuals with different hearing needs.

## Members of Team Konektra

- (ML) M547BKY4419 – Ray Ramadita – UIN Sunan Gunung Djati Bandung
- (ML) M614BSY0843 – Muhammad Feraldi. N – Universitas Nurdin Hamzah
- (ML) M179BSY0873 – Fajar Riansyah Aryda Putra – Universitas Ahmad Dahlan
- (CC) C179BSY3148 – Ibnu Zaman – Universitas Ahmad Dahlan
- (CC) C295BSY4120 – Lintang Aji Delamansyah – Universitas Pembangunan Nasional Veteran Jakarta
- (MD) A179BSY2564 – Dimas Thaqif Attaulah – Universitas Ahmad Dahlan
- (MD) A296BSX2668 – Afrida Lailiyah Hanim – Universitas Pembangunan Nasional Veteran Jawa Timur

## Project Structure

```bash
.
├── README.md - This file.
├── "1 - Machine Learning"
│   ├── "Detection"
│   │   ├── "datasets" because of the size of the dataset, we can't upload it to github.
│   ├── "Detection_Streamlit"
│   ├── "TTS"
├── "2 - CC - Cloud Computing"
│   ├── "Text-to-Sign"
├── "3 - Android - Mobile Development"

```

## Prequisites:

- Python 3.8 or above
- Tensorflow 2.10.0 or above

## How to run:

- Clone this repository

```bash
git clone https://github.com/RayRama/Konektra.git
```

- For Machine Learning

  - Detection (Detecting on your local computer)

    - You can collect your own dataset or use our dataset. If you want to use our dataset, download the dataset from [here](google.com) TODO: Add the link. **But, our recommendation is to collect your own dataset because there are some another ways to run the code.**
    - Open `Collecting.ipynb` and run the code if you want to collect your own dataset.
    - Open `Testing.ipynb` and run the code if you want to test the model (your own or our model).
    - Open `refine_data.ipynb` and run the code if you want to refine or improve the dataset.

  - Detection_Streamlit (Detecting on web browser. Can be accessed from anywhere)

    - Run the code below to install the required packages

    ```bash
    pip install -r requirements.txt
    ```

    - Run the code below to run the web app

    ```bash
    streamlit run main.py
    ```

    Note: If you want to deploy the web app, you can use GCP, Heroku or another platform.

  - TTS (Text to Speech)
    Because this code have many limitations, we recommend you to don't use this code. We have agreed to use another way to convert text to speech (Using built-in function from Android)

- For Cloud Computing

  - Text-to-Sign

    - Run the code below to install the required packages

    ```bash
    pip install -r requirements.txt
    ```

    - Run the code below to run the endpoint

    ```bash
    python main.py
    ```

    - If you want to deploy this feature to the cloud, you can use GCP, Heroku or another platform. But, we recommend you to use GCP because we have tried to deploy this feature to GCP and it works.

    - If you want to deploy this feature with Docker, you can use the Dockerfile in this repository.

- For Android
  - You can download the apk from [here](google.com) TODO: Add the link.
  - If you want to build the apk by yourself, you can use Android Studio. But, you need to install the required packages first. You can install the required packages by using the code below.
  - Instalation and Running Apps
      - Open the project in Android Studio and select “Open an existing Android Studio project”
      - Navigate to the project”s directory and select it.
      - Allow Android Studio to sync the project, downloading any necessary dependencies.
      - Connect your Android device to your computer or use an emulator.
      - Click the "Run" button (green play icon) to build and run the app on your device or emulator.
      - Allow the app to build and install; it should automatically launch on your chosen device or emulator.
