from flask import Flask

from api.commentsAPI import comment_opt
from api.imagesAPI import image_opt
from api.shuoshuoAPI import shuoshuo_opt
from api.usersAPI import user_opt
from api import database_pool

app = Flask(__name__)

app.register_blueprint(comment_opt, url_prefix="/comments")
app.register_blueprint(image_opt, url_prefix="/images")
app.register_blueprint(shuoshuo_opt, url_prefix="/shuoshuo")
app.register_blueprint(user_opt, url_prefix="/users")


@app.route("/")
def hello_world():
    return "hello world!!"


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=9192, debug=False)
    database_pool.close()
