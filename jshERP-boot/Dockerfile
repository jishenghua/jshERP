FROM maven:3.6.3-openjdk-8-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:8-jdk

COPY --from=build /app/target/*.jar app.jar
EXPOSE 9999
ENTRYPOINT ["java","-jar","app.jar"]