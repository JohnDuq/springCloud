FROM openjdk:11
ENV SPRING_PROFILES_ACTIVE=dock
VOLUME /tmp
# EXPOSE [PUERTO ALEATORIO, SE EXPONEN TODOS LOS PUERTOS]
ADD ./build/libs/service-user-role-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]