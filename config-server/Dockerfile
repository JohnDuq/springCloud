FROM openjdk:11
VOLUME /tmp
EXPOSE 8888
ADD ./build/libs/config-server-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]