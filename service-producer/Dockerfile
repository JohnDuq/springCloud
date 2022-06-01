FROM openjdk:11
ENV SPRING_PROFILES_ACTIVE=dock
VOLUME /tmp
ADD ./build/libs/service-producer-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]