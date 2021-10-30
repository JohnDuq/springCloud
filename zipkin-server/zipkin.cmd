@echo off
set RABBIT_ADDRESSES=localhost:5672
set MYSQL_DB=zipkin_db
set STORAGE_TYPE=mysql
set MYSQL_USER=zipkin
set MYSQL_PASS=zipkin
java -jar ./zipkin-server-2.23.4-exec.jar