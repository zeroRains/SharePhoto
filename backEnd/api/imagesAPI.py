from . import server_info
from model.image import Image
from flask import Blueprint

image_opt = Blueprint("image_opt", __name__)

@image_opt.route("")
def query():
    pass
