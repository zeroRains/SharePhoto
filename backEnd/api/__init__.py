import pymysql

server_info = {
    "db_url": "pan.kexie.space",
    "port": 9090,
    "db_user": "root",
    "passwd": "bassword",
    "db_name": "sharingphoto"
}

database_object = pymysql.connect(host=server_info["db_url"], user=server_info["db_user"], password=server_info["passwd"],
                                  database=server_info["db_name"], charset="utf8")
