FROM openjdk:17-jdk-alpine

EXPOSE 8080

WORKDIR usr/bin/bff-restaurant

ARG LOCAL_JAR_FILE=maven/*.jar

ADD ${LOCAL_JAR_FILE} app.jar

CMD ["java","-jar","app.jar"]