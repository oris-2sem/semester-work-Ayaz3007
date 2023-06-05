FROM maven AS build

WORKDIR /app
COPY src /app/src
COPY pom.xml /app
USER root

ENV DB_URL jdbc:postgresql://localhost:5432/ludik_db
ENV DB_USERNAME postgres
ENV DB_PASSWORD RoflanParol

RUN --mount=type=cache,target=/.m2 mvn -f /app/pom.xml clean package

FROM openjdk:19-jdk-alpine
COPY /target/app.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "usr/local/lib/app.jar"]