FROM openjdk:11
ENV SPRING_PROFILES_ACTIVE=dock
VOLUME /tmp
EXPOSE 8002
ADD ./build/libs/service-client-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]