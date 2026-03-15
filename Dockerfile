FROM eclipse-temurin:11-jre-alpine

WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY target/spe-calculator-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

USER appuser

ENTRYPOINT ["java", "-jar", "app.jar"]