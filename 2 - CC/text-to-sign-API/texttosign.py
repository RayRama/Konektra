from flask import Flask, request, jsonify
from PIL import Image, UnidentifiedImageError
import os
import base64

app = Flask(__name__)

def retrieve_images_by_titles(file_titles):
    images = []
    for file_title in file_titles:
        try:
            image_path = os.path.join(os.path.dirname(__file__), 'source', file_title)
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

@app.route('/get_images', methods=['GET'])
def get_images():
    file_titles = request.args.getlist('titles')
    images, status_code = retrieve_images_by_titles(file_titles)

    if images:
        response_data = []

        for file_title, img in images:
            img_byte_array = Image.Image.tobytes(img)
            img_base64 = base64.b64encode(img_byte_array).decode('utf-8')

            file_tanpa_ekstensi = os.path.splitext(file_title)[0].lower()

            response_data.append({'title': file_tanpa_ekstensi, 'image': img_base64})

        # Menggabungkan judul menjadi satu dictionary
        combined_response = {
            'options': [{'title': entry['title']} for entry in response_data],
            'images': [entry['image'] for entry in response_data]
        }

        response = jsonify(combined_response)
        return response, 202
    else:
        return jsonify({'error': f"Tidak ada gambar ditemukan untuk judul yang diberikan."}), status_code

if __name__ == '__main__':
    app.run(debug=True)
