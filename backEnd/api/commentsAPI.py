import time

from flask import Blueprint, request

from . import database_pool

comment_opt = Blueprint("comment_opt", __name__)

dbp = database_pool


def get_timestamp():
    time_stamp = time.time()
    time_stamp = time.localtime(time_stamp)
    formated_time_stamp = time.strftime("%Y-%m-%d", time_stamp)
    return formated_time_stamp


@comment_opt.route("/publish_comments", methods=["POST"])
def publish_comments():
    data = request.values
    db = dbp.connection()
    cursor = db.cursor()
    time_stamp = get_timestamp()

    cursor.execute(
        f"insert into sharingphoto.comments values (default, '{time_stamp}', 0, '{data.get('content')}', '{data.get('user')}', '{data.get('id')}')")
    try:
        db.commit()
    except:
        db.rollback()
        return {"msg": "failed", "data": []}
    cursor.execute(
        f"select max(id) from sharingphoto.comments where author='{data.get('user')}' and shuoshuoId='{data.get('id')}'")
    commentId = cursor.fetchone()[0]
    cursor.execute(f"insert into sharingphoto.thumbsup_comments values ('{data.get('user')}','{commentId}','F')")

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


@comment_opt.route("/get_comments", methods=["GET"])
def get_comments():
    comments_list = list()
    data = request.args
    db = dbp.connection()
    cursor = db.cursor()
    cursor.execute(
        f"select date, thumbsupNum, content, u.username, u.url, c.id from sharingphoto.comments c"
        f"join sharingphoto.users u on comments.author = u.uid where shuoshuoId='{data.get('id')}'")
    res = cursor.fetchall()
    if res is not None:
        for item in res:
            cursor.execute(f"select great from sharingphoto.thumbsup_comments tc where user='{data.get('user')}' and commentsId='{data.get('id')}'")
            tc_res = cursor.fetchall()
            if len(tc_res) == 0:
                tmp = "F"
            else:
                tmp = tc_res[0]
            it = {"date": item[0], "thumbsupNum": item[1], "content": item[2], "username": item[3], "iconId": item[4],
                  "isThumbsup": tmp, "commentId": item[5]}
            comments_list.append(it)
        cursor.close()
        db.close()
        return {"msg": "success", "data": comments_list}
    else:
        db.close()
        return {"msg": "failed", "data": []}


@comment_opt.route("/thumbsup_comments", methods=["POST", "GET"])
def thumbsup_comments():
    data = request.values
    db = dbp.connection()
    cursor = db.cursor()
    if data.get('add').lower() == "true":
        is_add = "T"
        cursor.execute(
            f"select great from sharingphoto.thumbsup_comments where commentsId='{data.get('id')}' and user='{data.get('user')}'")
        if cursor.fetchone() is None:
            cursor.execute(
                f"insert into sharingphoto.thumbsup_comments value ('{data.get('user')}', '{data.get('id')}', '{is_add}')")
        else:
            cursor.execute(
                f"update sharingphoto.thumbsup_comments set great='{is_add}' where commentsId='{data.get('id')}' and user='{data.get('user')}'")
        cursor.execute(
            f"update sharingphoto.comments set thumbsupNum=thumbsupNum+1 where id='{data.get('id')}'")
    else:
        is_add = "F"
        cursor.execute(
            f"update sharingphoto.thumbsup_comments set great='{is_add}' where commentsId='{data.get('id')}' and user='{data.get('user')}'")
        cursor.execute(
            f"update sharingphoto.comments set thumbsupNum=thumbsupNum-1 where id='{data.get('id')}'")

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
