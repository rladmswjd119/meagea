FROM openjdk:17
LABEL authors="gim-eunjeong"
ARG JAR_FILE=build/libs/docker-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]