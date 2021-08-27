from . import server_info
from model.shoushuo import ShuoShuo
from flask import Blueprint

shuoshuo_opt = Blueprint("shuoshuo_opt", __name__)

@shuoshuo_opt.route("")
def query():
    pass
