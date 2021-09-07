import json
import random
from hashlib import md5

from flask import Blueprint, request

from . import database_object

db = database_object
m = md5()

user_opt = Blueprint("user_opt", __name__)


def get_usernickname(bit: int):
    pattern = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'
    salt = str()
    for i in range(bit):
        salt += random.choice(pattern)
    return salt


@user_opt.route("/login", methods=["POST"])
def login():
    data = request.values
    cursor = db.cursor()
    cursor.execute(f"select passwd from users where uid='{data['id']}'")
    selected_data = cursor.fetchone()
    if selected_data is not None:
        m.update(selected_data[0].encode("utf-8"))
        isAuthorized = True if m.hexdigest() == data.get("passwd") else False
        if isAuthorized:
            return {"msg": "success", "data": []}
        else:
            return {"msg": "failed", "data": []}
    else:
        return {"msg": "failed", "data": []}


@user_opt.route("/register", methods=["POST"])
def register():
    data = request.values
    cursor = db.cursor()

    nickname = get_usernickname(bit=12)
    passwd = data["passwd"].encode("utf-8")
    m.update(passwd)
    cursor.execute(f"insert into users(uid, passwd, username) values ('{data.get('id')}', '{m.hexdigest()}', '{nickname}')")
    try:
        db.commit()
        return {"msg": "success", "data": []}
    except:
        db.rollback()
        return {"msg": "failed", "data": []}


@user_opt.route("/show_user_info", methods=["GET"])
def show_user_info():
    data_args = request.args
    cursor = db.cursor()

    cursor.execute(
        f"select sex, thumbsup, concern, fan, username, introduction, url from users where uid='{data_args.get('id')}'")
    res = cursor.fetchone()
    if res is not None:
        return {"msg": "success",
                "data": [{"sex": res[0], "great": res[1], "concern": res[2], "fan": res[3], "username": res[4],
                          "introduction": res[5], "url": res[6]}]}
    else:
        return {"msg": "failed", "data": []}


@user_opt.route("/modify_user_info", methods=["POST"])
def modify():
    data = request.values
    cursor = db.cursor()

    cursor.execute(
        f"update users set username='{data.get('username')}', sex='{data['sex']}', introduction='{data.get('introduction')}' where uid='{data.get('id')}'")

    try:
        db.commit()
        return {"msg": "success", "data": []}
    except:
        db.rollback()
        return {"msg": "failed", "data": []}


@user_opt.route("/modify_avatar", methods=["POST"])
def modify_avatar():
    data = request.values
    cursor = db.cursor()

    cursor.execute(f"update sharingphoto.users set url='{data.get('url')}' where uid='{data.get('id')}'")

    try:
        db.commit()
        return {"msg": "success", "data": []}
    except:
        db.rollback()
        return {"msg": "failed", "data": []}
