FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY fatec_eng3_db.sqlite /app/fatec_eng3_db.sqlite

COPY src /app/src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar /app/app.jar
COPY --from=build /app/fatec_eng3_db.sqlite /app/fatec_eng3_db.sqlite

EXPOSE 8080

CMD ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]