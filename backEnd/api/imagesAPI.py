import json
import os

from flask import Blueprint, request

from . import database_object

image_opt = Blueprint("image_opt", __name__)

db = database_object
img_save_path = "/static/imgs"


@image_opt.route("/upload_avatar", methods=["POST"])
def upload_avatar():
    data = json.loads(request.get_data())
    cursor = db.cursor()
    try:
        img = request.files.get('file')
        file_path = os.path.join(img_save_path, img.filename)
        img.save(file_path)

        return {"msg": "success", "data": [{"url": file_path}]}
    except:
        return {"msg": "failed", "data": []}


@image_opt.route("/upload_imgs", methods=["POST"])
def upload_img():
    data = json.loads(request.get_data())
    cursor = db.cursor()
    try:
        img = request.files.get('file')
        file_path = os.path.join(img_save_path, img.filename)
        img.save(file_path)

        return {"msg": "success", "data": [{"url": file_path}]}
    except:
        return {"msg": "failed", "data": []}
