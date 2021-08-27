from . import server_info
from model.comment import Comment
from flask import Blueprint

comment_opt = Blueprint("comment_opt", __name__)


@comment_opt.route("")
def query():
    pass

