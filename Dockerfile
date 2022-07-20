#Build Jar
FROM openjdk:11
ADD target/drm-mitra-authentication-0.0.1-SNAPSHOT.jar authapp.jar
ENTRYPOINT ["java", "-jar", "authapp.jar"]
EXPOSE 8080