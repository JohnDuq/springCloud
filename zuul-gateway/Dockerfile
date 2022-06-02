FROM openjdk:11
VOLUME /tmp
EXPOSE 8090
ENV SPRING_PROFILES_ACTIVE=dock
ENV SPRING_CONFIG_ENDPOINT=config-server-container
ADD ./build/libs/zuul-gateway-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]