# Stage 1: Build the application using Maven
FROM maven:3.8.4-openjdk-11 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Package the application (this skips tests in Docker since Jenkins will run them)
RUN mvn clean package -DskipTests

# Stage 2: Run the application using a lightweight JRE
FROM openjdk:11-jre-slim
WORKDIR /app
# Copy the compiled executable JAR from the build stage
COPY --from=build /app/target/spe-calculator-1.0-SNAPSHOT-jar-with-dependencies.jar ./calculator.jar

# Command to run the application
CMD ["java", "-jar", "calculator.jar"]