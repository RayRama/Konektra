def retrieve_images_by_titles(file_titles):
    images = []
    for file_title in file_titles:
        try:
            image_path = os.path.join(os.path.dirname(__file__), 'source', file_title)
            img = Image.open(image_path)
            images.append(img)
        except FileNotFoundError:
            pass  # Handle the error accordingly
        except PermissionError:
            pass  # Handle the error accordingly

    return images, 201 if images else 404