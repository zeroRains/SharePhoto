import os

from flask import Blueprint, request

from . import database_pool

image_opt = Blueprint("image_opt", __name__)

dbp = database_pool
img_save_path = "static/imgs"


@image_opt.route("/upload_avatar", methods=["POST"])
def upload_avatar():
    data = request.values
    try:
        img = request.files.get('file')
        file_path = os.path.join(img_save_path, img.filename)
        img.save(file_path)

        return {"msg": "success", "data": [{"url": file_path}]}
    except Exception as e:
        return {"msg": "failed\n" + str(e), "data": []}


@image_opt.route("/upload_imgs", methods=["POST"])
def upload_img():
    data = request.values
    try:
        img = request.files.get('file')
        file_path = os.path.join(img_save_path, img.filename)
        img.save(file_path)

        return {"msg": "success", "data": [{"url": file_path}]}
    except Exception as e:
        return {"msg": "failed\n" + str(e), "data": []}
