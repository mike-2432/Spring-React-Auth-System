FROM openjdk:19-jdk-slim
ARG JAR_FILE=target/server-1.jar
WORKDIR /app
COPY ${JAR_FILE} server.jar
ENTRYPOINT ["java", "-jar", "server.jar"]