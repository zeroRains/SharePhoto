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
    data = request.values
    cursor = db.cursor()
    time_stamp = get_timestamp()

    cursor.execute(
        f"insert into sharingphoto.comments values ('{time_stamp}', 0, '{data.get('content')}', '{data.get('user')}', '{data.get('id')}')")
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
        f"select date, thumbsupNum, content, author, u.url, tc.great, comments.id from sharingphoto.comments "
        f"join sharingphoto.users u on comments.author = u.uid "
        f"join sharingphoto.thumbsup_comments tc on tc.commentsId=comments.id where shuoshuoId={data.get('id')}")
    res = cursor.fetchall()
    if res is not None:
        for item in res:
            comments_list.append({"date": item[0], "thumbsupNum": item[1], "content": item[2], "author": item[3], "iconId": item[4], "isThumbsup": item[5], "commentId": item[6]})
        return {"msg": "success", "data": comments_list}
    else:
        return {"msg": "failed", "data": []}


@comment_opt.route("/thumbsup_comments", methods=["POST"])
def thumbsup_comments():
    data = request.values
    cursor = db.cursor()
    cursor.execute(f"select id from sharingphoto.comments where shuoshuoId='{data.get('id')}' and author='{data.get('user')}'")
    comment_id = cursor.fetchone()
    if comment_id is not None:
        comment_id = comment_id[0]
    else:
        return {"msg": "failed", "data": []}
    if data.get('add').lower() == "true":
        is_add = "T"
        cursor.execute(f"select great from sharingphoto.thumbsup_comments where commentsId='{data.get('id')}'")
        if len(cursor.fetchone()) == 0:
            cursor.execute(
                f"insert into sharingphoto.thumbsup_comments value ('{data.get('user')}', '{data.get('id')}', '{is_add}')")
        else:
            pass
            cursor.execute(
                f"update sharingphoto.thumbsup_comments set great='{is_add}' where commentsId='{comment_id}'")
        cursor.execute(f"update sharingphoto.comments set thumbsupNum=thumbsupNum+1 where shuoshuoId='{data.get('id')}' and author='{data.get('user')}'")
    else:
        is_add = "F"
        cursor.execute(f"update sharingphoto.thumbsup_comments set great='{is_add}' where commentsId='{comment_id}'")
        cursor.execute(f"update sharingphoto.comments set thumbsupNum=thumbsupNum-1 where shuoshuoId='{data.get('id')}' and author='{data.get('user')}'")

    try:
        db.commit()
        return {"msg": "success", "data": []}
    except:
        db.rollback()
        return {"msg": "failed", "data": []}
