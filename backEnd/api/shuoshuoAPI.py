import time

from flask import Blueprint, request

from . import database_pool

shuoshuo_opt = Blueprint("shuoshuo_opt", __name__)

dbp = database_pool


@shuoshuo_opt.route("/thumbsuped_shuoshuo", methods=["GET"])
def get_thumbsuped_shuoshuo():
    content = []
    data = request.values
    db = dbp.connection()
    cursor = db.cursor()
    cursor.execute(
        f"select s.id from favor join shuoshuo s on s.id=shuoshuoId join users u on u.uid = s.author where user='admin2' and favor.great='T';")
    res = cursor.fetchall()
    if res is not None:
        if len(res) != 0:
            for item in res:
                shuoshuo = {}
                shuoshuo["id"] = item[0]
                cursor.execute(
                    f"select url from photo where shuoshuoId='{item[0]}'")
                res1 = cursor.fetchone()
                shuoshuo["thumbnail"] = res1[0]
                content.append(shuoshuo)
            cursor.close()
            db.close()
            return {"msg": "success", "data": content}
        else:
            cursor.close()
            db.close()
            return {"msg": "no data", "data": []}
    else:
        cursor.close()
        db.close()
        return {"msg": "failed", "data": []}


@shuoshuo_opt.route("/stared_shuoshuo", methods=["GET"])
def get_stared_shuoshuo():
    content = []
    data = request.values
    db = dbp.connection()
    cursor = db.cursor()
    cursor.execute(
        f"select s.id from favor join shuoshuo s on s.id=shuoshuoId join users u on u.uid = s.author where user='admin2' and favor.star='T';")
    res = cursor.fetchall()
    if res is not None:
        if len(res) != 0:
            for item in res:
                shuoshuo = {}
                shuoshuo["id"] = item[0]
                cursor.execute(
                    f"select url from photo where shuoshuoId='{item[0]}'")
                res1 = cursor.fetchone()
                shuoshuo["thumbnail"] = res1[0]
                content.append(shuoshuo)
            cursor.close()
            db.close()
            return {"msg": "success", "data": content}
        else:
            cursor.close()
            db.close()
            return {"msg": "no data", "data": []}
    else:
        cursor.close()
        db.close()
        return {"msg": "failed", "data": []}


@shuoshuo_opt.route("/self_published", methods=["GET"])
def get_self_published_shuoshuo():
    content = []
    data = request.values
    db = dbp.connection()
    cursor = db.cursor()
    cursor.execute(
        f"select s.id from shuoshuo s " +
        f"join users u on u.uid = s.author where s.author='{data.get('user')}'")
    res = cursor.fetchall()
    if res is not None:
        for item in res:
            shuoshuo = {}
            shuoshuo["id"] = item[0]
            cursor.execute(
                f"select url from photo where shuoshuoId='{item[0]}'")
            res1 = cursor.fetchone()
            if res1 is not None:
                shuoshuo["thumbnail"] = res1[0]
                content.append(shuoshuo)
        cursor.close()
        db.close()
        return {"msg": "success", "data": content}
    else:
        cursor.close()
        db.close()
        return {"msg": "failed", "data": []}


@shuoshuo_opt.route("/category", methods=["GET"])
def get_page_from_category():
    content = []
    data = request.values
    db = dbp.connection()
    cursor = db.cursor()
    cursor.execute(
        f"select s.author, u.url, s.id, s.star, s.topic from shuoshuo s " +
        f"join users u on u.uid = s.author where s.category='{data.get('category')}'")
    res = cursor.fetchall()
    if res is not None:
        for item in res:
            shuoshuo = {
                "author": item[0],
                "iconId": item[1],
                "id": item[2],
                "starNum": item[3],
                "title": item[4]
            }
            cursor.execute(
                f"select url from photo where shuoshuoId='{item[2]}' limit 1")
            res1 = cursor.fetchall()
            if len(res1) == 0:
                continue
            shuoshuo["thumbnail"] = res1[0][0]
            cursor.execute(
                f"select star from favor where shuoshuoId='{item[2]}' and user='{data.get('user')}'"
            )
            res2 = cursor.fetchall()
            if len(res2) == 0:
                shuoshuo["star"] = "F"
            else:
                shuoshuo["star"] = res2[0][0]
            content.append(shuoshuo)
        cursor.close()
        db.close()
        return {"msg": "success", "data": content}
    else:
        cursor.close()
        db.close()
        return {"msg": "failed", "data": []}


@shuoshuo_opt.route("/recommended", methods=["GET"])
def show_recommend_page():
    data = []
    data_values = request.values
    db = dbp.connection()
    cursor = db.cursor()
    cursor.execute(
        f"select s.author, u.url, s.id, s.star, s.topic from shuoshuo s " +
        f"join users u on u.uid = s.author"
    )
    res = cursor.fetchall()
    if res is not None:
        for item in res:
            shuoshuo = {
                "author": item[0],
                "iconId": item[1],
                "id": item[2],
                "starNum": item[3],
                "title": item[4]
            }
            cursor.execute(
                f"select url from photo where shuoshuoId='{item[2]}' limit 1")
            res1 = cursor.fetchall()
            if len(res1) == 0:
                continue
            shuoshuo["thumbnail"] = res1[0][0]
            cursor.execute(
                f"select star from favor where shuoshuoId='{item[2]}' and user='{data_values.get('user')}'"
            )
            res2 = cursor.fetchall()
            if len(res2) == 0:
                shuoshuo["star"] = "F"
            else:
                shuoshuo["star"] = res2[0][0]
            data.append(shuoshuo)
        cursor.close()
        db.close()
        return {"msg": "success", "data": data}
    else:
        cursor.close()
        db.close()
        return {"msg": "failed", "data": []}


@shuoshuo_opt.route("/concern", methods=["GET"])
def show_follow_page():
    data = []
    data_values = request.values
    db = dbp.connection()
    cursor = db.cursor()
    cursor.execute(
        f"select s.author, u.url, s.id, s.star, s.topic from shuoshuo s " +
        f"join users u on u.uid = s.author " +
        f"where s.author in (select followed from concern where user='{data_values.get('id')}')"
    )

    res = cursor.fetchall()
    if res is not None:
        for item in res:

            shuoshuo = {
                "author": item[0],
                "iconId": item[1],
                "id": item[2],
                "starNum": item[3],
                "title": item[4]
            }
            cursor.execute(
                f"select url from photo where shuoshuoId='{item[2]}' limit 1")
            res1 = cursor.fetchall()
            if len(res1) == 0:
                continue
            shuoshuo["thumbnail"] = res1[0][0]
            cursor.execute(
                f"select star from favor where shuoshuoId='{item[2]}' and user='{data_values.get('user')}'"
            )
            res2 = cursor.fetchall()
            if len(res2) == 0:
                shuoshuo["star"] = "F"
            else:
                shuoshuo["star"] = res2[0][0]
            data.append(shuoshuo)
        cursor.close()
        db.close()
        return {"msg": "success", "data": data}
    else:
        cursor.close()
        db.close()
        return {"msg": "failed", "data": []}


@shuoshuo_opt.route("/detail", methods=["GET"])
def show_shuoshuo_detail():
    data = request.args
    db = dbp.connection()
    content = {
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
        "description": ""
    }
    cursor = db.cursor()
    cursor.execute(
        f"select u.url, u.username, s.date, s.great, s.star, s.title, s.description, u.uid from shuoshuo s "
        f"join sharingphoto.users u on u.uid = s.author where s.id='{data.get('id')}'")
    res = cursor.fetchone()
    if res is None:
        cursor.close()
        db.close()
        return {"msg": "failed1", "data": []}
    content["icon"] = res[0]
    content["username"] = res[1]
    content["date"] = res[2]
    content["zanNum"] = res[3]
    content["starNum"] = res[4]
    content["title"] = res[5]
    content["description"] = res[6]
    content["uid"] = res[7]

    cursor.execute(f"select f.great, f.star from sharingphoto.shuoshuo " +
                   f"join sharingphoto.favor f on shuoshuo.id = f.shuoshuoId where shuoshuo.id='{data.get('id')}'")

    res2 = cursor.fetchone()
    if res2 is not None:
        content["zanStatus"] = res2[0]
        content["starStatus"] = res2[1]
    else:
        content["zanStatus"] = "F"
        content["starStatus"] = "F"

    cursor.execute(f"select followed from concern where user='{data.get('user')}' and followed='{res[7]}'")
    res3 = cursor.fetchone()
    if res3 is not None:
        content["follow"] = "T"
    else:
        content["follow"] = "F"

    cursor.execute(
        f"select photo.url from photo "
        f"join sharingphoto.shuoshuo s on s.id = photo.shuoshuoId where shuoshuoId='{data.get('id')}'")
    image_list = []
    res4 = cursor.fetchall()
    if len(res4) == 0:
        return {"msg": "failed4", "data": []}
    for image in res4:
        image_list.append(image[0])

    content["photos"] = image_list
    cursor.close()
    db.close()
    return {"msg": "success", "data": [content]}


@shuoshuo_opt.route("/thumbsup", methods=["POST"])
def thumbsup_shuoshuo():
    data = request.values
    db = dbp.connection()
    cursor = db.cursor()
    cursor.execute(f"select author from shuoshuo where id='{data.get('id')}'")
    author = cursor.fetchone()[0]
    if data.get('add').lower() == "true":
        is_add = "T"
        cursor.execute(f"select great from favor where shuoshuoId='{data.get('id')}' and user='{data.get('user')}'")
        if cursor.fetchone() is None:
            cursor.execute(
                f"insert into favor value ('{data.get('user')}', '{data.get('id')}', 'F', '{is_add}')")
        else:
            cursor.execute(
                f"update favor set great='{is_add}' where shuoshuoId='{data.get('id')}' and user='{data.get('user')}'")
        cursor.execute(f"update shuoshuo set great=great+1 where shuoshuo.id='{data.get('id')}'")
        cursor.execute(f"update users set thumbsup=thumbsup+1 where uid='{author}'")
    else:
        is_add = "F"
        cursor.execute(
            f"update favor set great='{is_add}' where shuoshuoId='{data.get('id')}' and user='{data.get('user')}'")
        cursor.execute(f"update shuoshuo set great=great-1 where shuoshuo.id='{data.get('id')}'")
        cursor.execute(f"update users set thumbsup=thumbsup-1 where uid='{author}'")

    try:
        db.commit()
        cursor.close()
        db.close()
        return {"msg": "success", "data": []}
    except:
        db.rollback()
        cursor.close()
        db.close()
        return {"msg": "failed", "data": []}


@shuoshuo_opt.route("/follow", methods=["POST"])
def follow_person():
    data = request.values
    db = dbp.connection()
    cursor = db.cursor()
    if data.get('user') == data.get('author'):
        db.close()
        return {"msg": "You can't follow yourself.", "data": []}

    if data.get('add').lower() == "true":
        cursor.execute(
            f"select * from concern where user='{data.get('user')}' and followed='{data.get('author')}'")
        if cursor.fetchone() is None:
            cursor.execute(f"insert into concern values ('{data.get('user')}', '{data.get('author')}')")
            cursor.execute(f"update users set fan=fan+1 where uid='{data.get('author')}'")
        else:
            db.close()
            return {"msg": "You can't follow again.", "data": []}
    else:
        cursor.execute(
            f"delete from concern where user='{data.get('user')}' and followed='{data.get('author')}'")
        cursor.execute(f"update users set fan=fan-1 where uid='{data.get('author')}'")

    try:
        db.commit()
        cursor.close()
        return {"msg": "success", "data": []}
    except:
        db.rollback()
        cursor.close()
        db.close()
        return {"msg": "failed", "data": []}


@shuoshuo_opt.route("/favor", methods=["POST"])
def star_shuoshuo():
    data = request.values
    db = dbp.connection()
    cursor = db.cursor()
    if data.get('add').lower() == "true":
        is_add = "T"
        cursor.execute(f"select star from favor where shuoshuoId='{data.get('id')}'")
        if cursor.fetchone() is None:
            cursor.execute(
                f"insert into favor value ('{data.get('user')}', '{data.get('id')}', '{is_add}', 'F')")
        else:
            cursor.execute(
                f"update favor set star='{is_add}' where shuoshuoId='{data.get('id')}' and user='{data.get('user')}'")
        cursor.execute(f"update shuoshuo set star=star+1 where shuoshuo.id='{data.get('id')}'")
        cursor.execute(f"update users set star=star+1 where uid='{data.get('user')}'")
    else:
        is_add = "F"
        cursor.execute(
            f"update favor set star='{is_add}' where shuoshuoId='{data.get('id')}' and user='{data.get('user')}'")
        cursor.execute(f"update shuoshuo set star=star-1 where shuoshuo.id='{data.get('id')}'")
        cursor.execute(f"update users set star=star-1 where uid='{data.get('user')}'")

    try:
        db.commit()
        cursor.close()
        db.close()
        return {"msg": "success", "data": []}
    except:
        db.rollback()
        cursor.close()
        db.close()
        return {"msg": "failed", "data": []}


@shuoshuo_opt.route("/publish", methods=["POST"])
def publish_shuoshuo():
    data = request.values
    db = dbp.connection()
    cursor = db.cursor()

    time_stamp = time.time()
    formated_time_stamp = time.localtime(time_stamp)
    formated_time_stamp = time.strftime("%Y-%m-%d %H:%M:%S", formated_time_stamp)

    image_list = data.get('photo').split(",")

    cursor.execute(
        f"insert into shuoshuo values (default, '{data.get('category')}', '{data.get('topic')}', 0, 0, '{data.get('title')}', '{data.get('description')}', '{formated_time_stamp}', '{data.get('id')}')")
    try:
        db.commit()
    except:
        print("insert failed")
        db.rollback()
    cursor.execute(f"select max(id) from shuoshuo where author='{data.get('id')}'")
    shuoshuoId = cursor.fetchone()
    print(image_list)
    if shuoshuoId is not None:
        for img in image_list:
            cursor.execute(f"insert into photo values ('{img}', '{shuoshuoId[0]}')")
            try:
                db.commit()
            except:
                db.rollback()
                return {"msg": "failed", "data": []}
        try:
            cursor.close()
            db.close()
            return {"msg": "success", "data": []}
        except:
            cursor.close()
            db.close()
            return {"msg": "failed", "data": []}
    else:
        cursor.close()
        db.close()
        return {"msg": "failed", "data": []}
