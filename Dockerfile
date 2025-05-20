FROM maven:3.9.7-amazoncorretto-17 AS build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app

Run mvn clean install

FROM amazoncorretto:17

COPY --from=build /app/target/encurtadorurl-0.0.1-SNAPSHOT.jar /app/encurtadorurl-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/encurtadorurl-0.0.1-SNAPSHOT.jar"]