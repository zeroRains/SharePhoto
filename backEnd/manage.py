from flask import Flask

from api import commentsAPI
from api import imagesAPI
from api import shuoshuoAPI
from api import usersAPI

app = Flask(__name__)

app.register_blueprint(commentsAPI, url_prefix="/comments")
app.register_blueprint(imagesAPI, url_prefix="/images")
app.register_blueprint(shuoshuoAPI, url_prefix="/shuoshuo")
app.register_blueprint(usersAPI, url_prefix="/users")


@app.route("/")
def hello_world():
    return "hello world!!"


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=9090, debug=True)
