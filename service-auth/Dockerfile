FROM openjdk:11
ENV SPRING_PROFILES_ACTIVE=dock
VOLUME /tmp
EXPOSE 9100
ENV config.security.oauth.client.id=consumerApp
ENV config.security.oauth.client.secret=54321
ENV config.security.oauth.user.login.try=3
ENV config.security.oauth.jwt.key=123C18C4D8F896AEF7DC61383E538
ADD ./build/libs/service-auth-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]