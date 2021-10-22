import pymysql
from dbutils.pooled_db import PooledDB

server_info = {
    "db_url": "pan.kexie.space",
    "port": 9090,
    "db_user": "root",
    "passwd": "bassword",
    "db_name": "sharingphoto"
}

database_pool = PooledDB(
    creator=pymysql,
    maxconnections=10,
    maxcached=10,
    mincached=4,
    host=server_info["db_url"],
    user=server_info["db_user"],
    password=server_info["passwd"],
    database=server_info["db_name"],
    charset="utf8"
)
