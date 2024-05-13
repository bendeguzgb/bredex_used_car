FROM maven:3.9.6-eclipse-temurin-11-alpine AS build

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml clean package -Dmaven.test.skip=true

FROM adoptopenjdk/openjdk11:jdk-11.0.23_9-alpine-slim
COPY --from=build /usr/src/app/target/used_car-0.0.1-SNAPSHOT.jar /usr/app/used_car-0.0.1-SNAPSHOT.jar

EXPOSE 8080
CMD ["java","-jar","/usr/app/used_car-0.0.1-SNAPSHOT.jar"]