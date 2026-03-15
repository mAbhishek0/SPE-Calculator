FROM eclipse-temurin:11-jre-alpine
WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Layer 1: dependencies — only rebuilds when pom.xml changes
COPY target/dependency/ ./dependency/

# Layer 2: your app JAR — tiny, rebuilds on every commit
COPY target/spe-calculator-1.0-SNAPSHOT.jar app.jar

USER appuser

ENTRYPOINT ["java", "-cp", "app.jar:dependency/*", "org.example.App"]