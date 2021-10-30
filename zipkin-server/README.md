Decargar jar zipkin

1. entrar https://zipkin.io/pages/quickstart.html
    - Java
        * latest release
2. ubicar en carpeta zipkin

Ajuste Docker file

1. Cambiar el nombre del archivo .java por la versión descargada

Configuración MySql Zipkin

1. Crear base de datos con nombre 'zipkin'
    - Charset/Collation= utf8 utf8_general_ci
2. Ejecutar zipkin-mysql.sql en la base de datos 'zipkin_db'
2. Crear usuario con 
    - campo nombre 'zipkin'
    - campo password 'zipkin'
    - tab schema privileges
        * add entry
            > selected schema 'zipkin_db'
        * object right
            > SELECT
            > INSERT
            > UPDATE
            > EXECUTE
            > SHOW VIEW

Ejcutar Docker

1. docker build -t zipkin-server-image:v1 .
2. docker run -p 9411:9411 --name zipkin-server-container --network springcloud -e RABBIT_ADDRESSES=rabbit-mq-container:5672 -e MYSQL_DB=zipkin_db -e STORAGE_TYPE=mysql -e MYSQL_USER=zipkin -e MYSQL_PASS=zipkin -e MYSQL_HOST=mysql-db-container zipkin-server-image:v1

Verificar servidor zipkin

1. Entrar a http://127.0.0.1:9411/zipkin/