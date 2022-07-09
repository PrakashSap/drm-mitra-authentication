#Build Jar
FROM openjdk:11
ADD target/drm-mitra-authentication-0.0.1-SNAPSHOT.jar authApp.jar
ENTRYPOINT ["java", "-jar", "authApp.jar"]