import pymysql

server_info = {
    "url": "",
    "port": 9090,
    "dbuser": "",
    "passwd": "",
    "dbname": ""
}

database_object = pymysql.connect(server_info["url"], server_info["dbuser"], server_info["passwd"],
                                  server_info["dbname"])
