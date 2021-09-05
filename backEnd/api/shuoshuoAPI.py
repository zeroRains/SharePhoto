import json

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
    content = {"msg": "success",
               "data": [{
                   "icon": "",
                   "username": "",
                   "date": "",
                   "follow": "",
                   "photos": [],
                   "zanStatus": "",
                   "zanNum": "",
                   "starStatus": "",
                   "starNum": "",
                   "title": "",
                   "description": "",
                   "comments": [
                       {
                           "rIcon": "",
                           "rUsername": "",
                           "rNum": "",
                           "rContent": "",
                           "rDate": "",
                           "rStatus": "",
                       }
                   ],
               }]}
    cursor = db.cursor()

    cursor.execute(
        f"select sharingphoto.users.url, sharingphoto.users.username, shuoshuo.date, shuoshuo.great, shuoshuo.star, shuoshuo.title, shuoshuo.description from sharingphoto.shuoshuo "
        f"join sharingphoto.users u on u.uid = shuoshuo.author where shuoshuo.id={data.get('id')}")
    res = cursor.fetchone()
    if res is None: return {"msg": "failed", "data": []}
    content["icon"] = res[0]
    content["username"] = res[1]
    content["date"] = res[2]
    content["zanNum"] = res[3]
    content["starNum"] = res[4]
    content["title"] = res[5]
    content["description"] = res[6]

    cursor.execute(f"select sharingphoto.favor.great, sharingphoto.favor.star from sharingphoto.shuoshuo " +
                   f"join sharingphoto.favor f on shuoshuo.id = f.shuoshuoId where shuoshuo.id={data.get('id')}")

    res2 = cursor.fetchone()
    if res2 is None: return {"msg": "failed", "data": []}
    content["zanStatus"] = res2[0]
    content["starStatus"] = res2[1]

    cursor.execute(
        f"select sharingphoto.concern.followed from sharingphoto.concern c " +
        f"join sharingphoto.users u on u.uid = c.user " +
        f"join sharingphoto.shuoshuo s on s.author = u.uid where s.id={data.get('id')}")

    res3 = cursor.fetchone()
    if res3 is None: return {"msg": "failed", "data": []}
    content["follow"] = res[0]

    cursor.execute(
        f"select sharingphoto.photo.url from sharingphoto.photo "
        f"sjoin sharingphoto.shuoshuo s on s.id = photo.shuoshuoId where shuoshuoId={data.get('id')}")
    image_list = []
    res4 = cursor.fetchall()
    if res4 is None: return {"msg": "failed", "data": []}
    for image in res4:
        image_list.append(image[0])

    cursor.execute(
        f"select sharingphoto.users.url, sharingphoto.users.username, sharingphoto.comments.thumbsupNum, sharingphoto.comments.content, sharingphoto.comments.date, sharingphoto.thumbsup_comments.great from sharingphoto.comments "
        f"join sharingphoto.users u on u.uid = comments.author "
        f"join sharingphoto.shuoshuo s on s.id = comments.shuoshuoId "
        f"join sharingphoto.thumbsup_comments tc on u.uid = tc.user where s.id={data.get('id')}")

    res5 = cursor.fetchall()
    if res5 is None: return {"msg": "failed", "data": []}
    comments_list = []
    for item in res5:
        comments_list.append(
            {"rIcon": item[0], "rUsername": item[1], "rNum": item[2], "rContent": item[3], "rDate": item[4],
             "rStatus": item[5]})

    content["photos"] = image_list
    content["comments"] = comments_list
    return content


@shuoshuo_opt.route("/shuoshuo/thumbsup", methods=["POST"])
def thumbsup_shuoshuo():
    data = json.loads(request.get_data())
    cursor = db.cursor()
    if data['add']:
        cursor.execute(f"update sharingphoto.favor set great={data['add']} where shuoshuoId={data['id']}")
        cursor.execute(f"update sharingphoto.shuoshuo set great=great+1 where shuoshuo.id={data['id']}")
        cursor.execute(f"update sharingphoto.users set thumbsup=thumbsup+1 where uid={data['user']}")
    else:
        cursor.execute(f"update sharingphoto.favor set great={data['add']} where shuoshuoId={data['id']}")
        cursor.execute(f"update sharingphoto.shuoshuo set great=great-1 where shuoshuo.id={data['id']}")
        cursor.execute(f"update sharingphoto.users set thumbsup=thumbsup-1 where uid={data['user']}")

    try:
        db.commit()
        return {"msg": "success", "data": []}
    except:
        db.rollback()
        return {"msg": "failed", "data": []}


@shuoshuo_opt.route("/shuoshuo/follow", methods=["POST"])
def follow_person():
    data = json.loads(request.get_data())
    cursor = db.cursor()

    if data["add"]:
        cursor.execute(f"")
    else:
        cursor.execute(f"")

    try:
        db.commit()
        return {"msg": "success", "data": []}
    except:
        db.rollback()
        return {"msg": "failed", "data": []}
