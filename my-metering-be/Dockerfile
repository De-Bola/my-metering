#FROM openjdk:21
#ARG JAR_FILE=build/libs/metering-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} metering.jar
#ENTRYPOINT ["java","-jar","/metering.jar"]

# -------- Stage 1: Build the JAR --------
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

# Copy only Gradle wrapper and build files first (to cache dependencies)
COPY my-metering-be/build.gradle.kts my-metering-be/settings.gradle.kts ./
COPY my-metering-be/gradlew ./gradlew
COPY my-metering-be/gradle ./gradle

# Preload dependencies
RUN ./gradlew build -x test --no-daemon || return 0

# Copy the full source
COPY my-metering-be/ .

# Build the project
RUN ./gradlew clean build --no-daemon

# -------- Stage 2: Runtime Image --------
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy JAR from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
