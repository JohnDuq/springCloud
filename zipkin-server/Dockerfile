FROM openjdk:11
VOLUME /tmp
EXPOSE 9411
ADD ./zipkin-server-2.23.4-exec.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]