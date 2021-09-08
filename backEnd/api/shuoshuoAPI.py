import time

from flask import Blueprint, request

from . import database_object

shuoshuo_opt = Blueprint("shuoshuo_opt", __name__)

db = database_object


@shuoshuo_opt.route("/recommended", methods=["GET"])
def show_recommend_page():
    data = []
    cursor = db.cursor()

    cursor.execute(
        f"select shuoshuo.id, shuoshuo.title, u.url, u.username, shuoshuo.star, f.star, p.url from sharingphoto.shuoshuo "
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


@shuoshuo_opt.route("/concern", methods=["GET"])
def show_follow_page():
    data = request.args
    cursor = db.cursor()
    shuoshuo_list = []

    cursor.execute(
        f"select id, title, u.url, u.username, star, f.star, p.url from sharingphoto.shuoshuo "
        f"join sharingphoto.photo p on shuoshuo.id = p.shuoshuoId "
        f"join sharingphoto.users u on u.uid = shuoshuo.author "
        f"join sharingphoto.concern c on c.user = u.uid "
        f"join sharingphoto.favor f on p.shuoshuoId = f.shuoshuoId where id >= (" +
        f"select floor(rand() * (select max(id) from shuoshuo))" +
        f") order by id limit 20")
    res = cursor.fetchall()
    if res is not None:
        for item in res:
            shuoshuo_list.append(
                {"id": item[0], "title": item[1], "iconId": item[2], "author": item[3], "starNum": item[4],
                 "star": item[5],
                 "thumbnail": item[6]})
        return {"msg": "success", "data": shuoshuo_list}
    else:
        return {"msg": "failed", "data": []}


@shuoshuo_opt.route("/detail", methods=["GET"])
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
        f"select u.url, u.username, shuoshuo.date, shuoshuo.great, shuoshuo.star, shuoshuo.title, shuoshuo.description from sharingphoto.shuoshuo "
        f"join sharingphoto.users u on u.uid = shuoshuo.author where shuoshuo.id='{data.get('id')}'")
    res = cursor.fetchone()
    if res is None: return {"msg": "failed", "data": []}
    content["icon"] = res[0]
    content["username"] = res[1]
    content["date"] = res[2]
    content["zanNum"] = res[3]
    content["starNum"] = res[4]
    content["title"] = res[5]
    content["description"] = res[6]

    cursor.execute(f"select f.great, f.star from sharingphoto.shuoshuo " +
                   f"join sharingphoto.favor f on shuoshuo.id = f.shuoshuoId where shuoshuo.id='{data.get('id')}'")

    res2 = cursor.fetchone()
    if res2 is None: return {"msg": "failed", "data": []}
    content["zanStatus"] = res2[0]
    content["starStatus"] = res2[1]

    cursor.execute(
        f"select c.followed from concern c " +
        f"join users u on u.uid = c.user " +
        f"join shuoshuo s on s.author = u.uid where s.id='{data.get('id')}'")

    res3 = cursor.fetchone()
    if res3 is None: return {"msg": "failed", "data": []}
    content["follow"] = res[0]

    cursor.execute(
        f"select photo.url from photo "
        f"join sharingphoto.shuoshuo s on s.id = photo.shuoshuoId where shuoshuoId='{data.get('id')}'")
    image_list = []
    res4 = cursor.fetchall()
    if res4 is None: return {"msg": "failed", "data": []}
    for image in res4:
        image_list.append(image[0])

    cursor.execute(
        f"select u.url, u.username, comments.thumbsupNum, comments.content, comments.date, tc.great from sharingphoto.comments "
        f"join users u on u.uid = comments.author "
        f"join shuoshuo s on s.id = comments.shuoshuoId "
        f"join thumbsup_comments tc on u.uid = tc.user where s.id='{data.get('id')}'")

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


@shuoshuo_opt.route("/thumbsup", methods=["POST"])
def thumbsup_shuoshuo():
    data = request.values
    cursor = db.cursor()
    if data['add']:
        cursor.execute(f"select great from favor where shuoshuoId='{data.get('id')}'")
        if cursor.fetchone() is None:
            cursor.execute(
                f"insert into favor value ('{data.get('user')}', '{data.get('id')}', 'F', '{data.get('add')}')")
        else:
            cursor.execute(
                f"update favor set great='{data.get('add')}' where shuoshuoId='{data.get('id')}'")
        cursor.execute(f"update shuoshuo set great=great+1 where shuoshuo.id='{data.get('id')}'")
        cursor.execute(f"update users set thumbsup=thumbsup+1 where uid='{data.get('user')}'")
    else:
        cursor.execute(f"update favor set great='{data.get('add')}' where shuoshuoId='{data.get('id')}'")
        cursor.execute(f"update shuoshuo set great=great-1 where shuoshuo.id='{data.get('id')}'")
        cursor.execute(f"update users set thumbsup=thumbsup-1 where uid='{data.get('user')}'")

    try:
        db.commit()
        return {"msg": "success", "data": []}
    except:
        db.rollback()
        return {"msg": "failed", "data": []}


@shuoshuo_opt.route("/follow", methods=["POST"])
def follow_person():
    data = request.values
    cursor = db.cursor()
    if data.get('user') == data.get('author'):
        return {"msg": "You can't follow yourself.", "data": []}

    if data.get('add'):
        cursor.execute(
            f"select * from concern where user='{data.get('user')}' and followed='{data.get('author')}'")
        if cursor.fetchone() is None:
            cursor.execute(f"insert into concern values ('{data.get('user')}', '{data.get('author')}')")
            cursor.execute(f"update users set fan=fan+1 where uid='{data.get('user')}'")
        else:
            return {"msg": "You can't follow again.", "data": []}
    else:
        cursor.execute(
            f"delete from concern where user='{data.get('user')}' and followed='{data.get('author')}'")
        cursor.execute(f"update users set fan=fan-1 where uid='{data.get('user')}'")

    try:
        db.commit()
        return {"msg": "success", "data": []}
    except:
        db.rollback()
        return {"msg": "failed", "data": []}


@shuoshuo_opt.route("/favor", methods=["POST"])
def star_shuoshuo():
    data = request.values
    cursor = db.cursor()
    if data['add']:
        cursor.execute(f"select star from favor where shuoshuoId='{data.get('id')}'")
        if cursor.fetchone() is None:
            cursor.execute(
                f"insert into favor value ('{data.get('user')}', '{data.get('id')}', '{data.get('add')}', 'F')")
        else:
            cursor.execute(
                f"update favor set star='{data.get('add')}' where shuoshuoId='{data.get('id')}'")
        cursor.execute(f"update shuoshuo set star=star+1 where shuoshuo.id='{data.get('id')}'")
        cursor.execute(f"update users set star=star+1 where uid='{data.get('user')}'")
    else:
        cursor.execute(f"update favor set star='{data.get('add')}' where shuoshuoId='{data.get('id')}'")
        cursor.execute(f"update shuoshuo set star=star-1 where shuoshuo.id='{data.get('id')}'")
        cursor.execute(f"update users set star=star-1 where uid='{data.get('user')}'")


@shuoshuo_opt.route("/publish", methods=["POST"])
def publish_shuoshuo():
    data = request.values
    cursor = db.cursor()

    time_stamp = time.time()
    formated_time_stamp = time.localtime(time_stamp)
    formated_time_stamp = time.strftime("%Y-%m-%d %H:%M:%S", formated_time_stamp)

    image_list = list(data.get('photo'))

    cursor.execute(
        f"insert into shuoshuo values ('{data.get('category')}', '{data.get('topic')}', 0, 0, '{data.get('title')}', '{data.get('description')}', '{formated_time_stamp}', '{data.get('id')}')")
    try:
        db.commit()
    except:
        print("insert failed")
        db.rollback()

    cursor.execute(f"select id from shuoshuo where author='{data.get('id')}'")
    shuoshuoId = cursor.fetchone()[0]
    for img in image_list:
        cursor.execute(f"insert into photo values ('{img}', '{shuoshuoId}')")
    try:
        db.commit()
        return {"msg": "success", "data": []}
    except:
        db.rollback()
        return {"msg": "failed", "data": []}
