from flask import Flask, request, jsonify
from PIL import Image
import os
import base64

app = Flask(__name__)

def retrieve_image_by_title(file_title):
    try:
        # Assuming images are in the 'source' folder within the same directory as the script
        image_path = os.path.join(os.path.dirname(__file__), 'source', file_title)

        # Open the image file
        img = Image.open(image_path)

        # You can return the image object if further processing is required
        return img, 201
    except FileNotFoundError:
        return None, 404
    except PermissionError:
        return None, 403

@app.route('/get_image', methods=['GET'])
def get_image():
    file_title = request.args.get('title', default='', type=str)
    img, status_code = retrieve_image_by_title(file_title)

    if img is not None:
        # Convert the image to bytes
        img_byte_array = Image.Image.tobytes(img)

        # Convert bytes to base64
        img_base64 = base64.b64encode(img_byte_array).decode('utf-8')

        # Remove file format extension and convert title to lowercase
        file_title_without_extension = os.path.splitext(file_title)[0].lower()

        # Return the response with options first, followed by the image in base64 format
        response = jsonify({'options': {'title': file_title_without_extension}, 'image': img_base64})
        return response, 202
    else:
        return jsonify({'error': f"Image with title '{file_title}' not found."}), status_code

if __name__ == '__main__':
    app.run(debug=True)
