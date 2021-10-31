FROM openjdk:11
VOLUME /tmp
EXPOSE 9411
ENV RABBIT_ADDRESSES=rabbit-mq-container:5672
ENV MYSQL_HOST=mysql-db-container
ENV MYSQL_TCP_PORT=3306
ENV MYSQL_DB=zipkin_db
ENV MYSQL_USER=zipkin
ENV MYSQL_PASS=zipkinpass
ENV STORAGE_TYPE=mysql
ADD ./zipkin-server-2.23.4-exec.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]