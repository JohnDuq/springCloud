FROM openjdk:11
VOLUME /tmp
EXPOSE 8090
ADD ./target/spring-gateway-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]