import json
import time

from flask import Blueprint, request

from . import database_object

comment_opt = Blueprint("comment_opt", __name__)

db = database_object


def get_timestamp():
    time_stamp = time.time()
    time_stamp = time.localtime(time_stamp)
    formated_time_stamp = time.strftime("%Y-%m-%d %H:%M:%S", time_stamp)
    return formated_time_stamp


@comment_opt.route("/publish_comments", methods=["POST"])
def publish_comments():
    data = json.loads(request.get_data())
    cursor = db.cursor()
    time_stamp = get_timestamp()

    cursor.execute(
        f"insert into sharingphoto.comments values ({time_stamp}, 0, {data['content']}, {data['user']}, {data['id']})")
    try:
        db.commit()
        return {"msg": "success", "data": []}
    except:
        db.rollback()
        return {"msg": "failed", "data": []}


@comment_opt.route("/get_comments", methods=["GET"])
def get_comments():
    comments_list = list()
    data = request.args
    cursor = db.cursor()

    cursor.execute(
        f"select date, thumbsupNum, content, author from sharingphoto.comments where shuoshuoId={data.get('id')}")
    res = cursor.fetchall()
    if res is not None:
        for item in res:
            comments_list.append({"date": item[0], "thumbsupNum": item[1], "content": item[2], "author": item[3]})
        return {"msg": "success", "data": comments_list}
    else:
        return {"msg": "failed", "data": []}


@comment_opt.route("/thumbsup_comments", methods=["POST"])
def thumbsup_comments():
    data = json.loads(request.get_data())
    cursor = db.cursor()
    if data['add']:
        cursor.execute(f"select great from sharingphoto.thumbsup_comments where commentsId={data['id']}")
        if cursor.fetchone() is None:
            cursor.execute(f"insert into sharingphoto.thumbsup_comments value ({data['user']}, {data['id']}, {data['add']})")
        else:
            cursor.execute(f"update sharingphoto.favor set great={data['add']} where shuoshuoId={data['id']}")
        cursor.execute(f"update sharingphoto.shuoshuo set great=great+1 where shuoshuo.id={data['id']}")
    else:
        cursor.execute(f"update sharingphoto.favor set great={data['add']} where shuoshuoId={data['id']}")
        cursor.execute(f"update sharingphoto.shuoshuo set great=great-1 where shuoshuo.id={data['id']}")

    try:
        db.commit()
        return {"msg": "success", "data": []}
    except:
        db.rollback()
        return {"msg": "failed", "data": []}
