# Etapa de build
FROM gradle:8.7.0-jdk17-alpine AS build
WORKDIR /app
COPY . .
RUN gradle build

# Etapa de execução
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]