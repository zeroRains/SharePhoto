from flask import Blueprint, request

from . import database_object

shuoshuo_opt = Blueprint("shuoshuo_opt", __name__)

db = database_object


@shuoshuo_opt.route("/shuoshuo/recommanded", methods=["GET"])
def show_recommend_page():
    data = []
    cursor = db.cursor()

    cursor.execute(
        f"select id, title, sharingphoto.users.url, sharingphoto.users.username, star, sharingphoto.favor.star, sharingphoto.photo.url from sharingphoto.shuoshuo "
        f"join sharingphoto.photo p on shuoshuo.id = p.shuoshuoId " +
        f"join sharingphoto.favor f on p.shuoshuoId = f.shuoshuoId " +
        f"join sharingphoto.users u on u.uid = shuoshuo.author where id >= (" +
        f"select floor(rand() * (select max(id) from shuoshuo))" +
        f") order by id limit 20")

    res = cursor.fetchall()
    if res is not None:
        for item in res:
            data.append(
                {"id": item[0], "title": item[1], "iconId": item[2], "author": item[3], "starNum": item[4],
                 "star": item[5],
                 "thumbnail": item[6]})
        return {"msg": "success", "data": data}
    else:
        return {"msg": "failed", "data": []}


@shuoshuo_opt.route("/shuoshuo/concern", methods=["GET"])
def show_follow_page():
    data = request.args
    cursor = db.cursor()

    cursor.execute(
        f"select id, title, sharingphoto.users.url, sharingphoto.users.username, star, sharingphoto.favor.star, sharingphoto.photo.url from sharingphoto.shuoshuo "
        f"join sharingphoto.photo p on shuoshuo.id = p.shuoshuoId "
        f"join sharingphoto.users u on u.uid = shuoshuo.author "
        f"join sharingphoto.concern c on c.user = u.uid "
        f"join sharingphoto.favor f on p.shuoshuoId = f.shuoshuoId where id >= (" +
        f"select floor(rand() * (select max(id) from shuoshuo))" +
        f") order by id limit 20")
    res = cursor.fetchall()
    if res is not None:
        for item in res:
            data.append(
                {"id": item[0], "title": item[1], "iconId": item[2], "author": item[3], "starNum": item[4],
                 "star": item[5],
                 "thumbnail": item[6]})
        return {"msg": "success", "data": data}
    else:
        return {"msg": "failed", "data": []}

@shuoshuo_opt.route("/shuoshuo/detail", methods=["GET"])
def show_shuoshuo_detail():
    data = request.args
    cursor = db.cursor()

    cursor.execute()

