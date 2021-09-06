import json
import os

from flask import Blueprint, request

from . import database_object

image_opt = Blueprint("image_opt", __name__)

db = database_object


@image_opt.route("/image/upload_avatar", methods=["POST"])
def upload_avatar():
    data = json.loads(request.get_data())
    cursor = db.cursor()
    try:
        img = request.files.get('file')
        path = "/static/img"
        file_path = os.path.join(path, img.filename)
        img.save(file_path)

        return {"msg": "success", "data": [{"url": file_path}]}
    except:
        return {"msg": "failed", "data": []}
