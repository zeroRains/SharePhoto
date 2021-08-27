from . import server_info
from model.user import User
from flask import Blueprint

user_opt = Blueprint("user_opt", __name__)

@user_opt.route("")
def query():
    pass
