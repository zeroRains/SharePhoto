import pymysql

server_info = {
    "db_url": "pan.kexie.space",
    "port": 9090,
    "db_user": "root",
    "passwd": "bassword",
    "db_name": "sharingphoto"
}

database_object = pymysql.connect(server_info["db_url"], server_info["db_user"], server_info["passwd"],
                                  server_info["db_name"])
