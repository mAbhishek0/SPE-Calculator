FROM eclipse-temurin:11-jre-alpine
WORKDIR /app
COPY target/*-jar-with-dependencies.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]