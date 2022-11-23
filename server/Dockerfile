FROM maven:3.8-eclipse-temurin-17-alpine as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package -Dmaven.test.skip

FROM openjdk:17-jdk
COPY --from=build /app/target/server-1.jar /server.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "server.jar"]