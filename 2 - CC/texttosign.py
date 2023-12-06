from flask import Flask, request, jsonify
from PIL import Image

app = Flask(__name__)

def retrieve_image_by_title(file_title):
    try:
        # Assuming images are in the 'source' folder within the same directory as the script
        image_path = f"source/{file_title}.jpg"  # You can adjust the file extension as needed

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

        # Return the image as a response
        response = jsonify({'image': img_byte_array.decode('latin-1')})
        return response, 202
    else:
        return jsonify({'error': f"Image with title '{file_title}' not found."}), status_code

if __name__ == '__main__':
    app.run(debug=True)
