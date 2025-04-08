#FROM openjdk:21
#ARG JAR_FILE=build/libs/metering-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} metering.jar
#ENTRYPOINT ["java","-jar","/metering.jar"]

# -------- Stage 1: Build the JAR --------
#FROM gradle:8.5-jdk21 AS builder
#
#WORKDIR /app
#
## Copy only Gradle wrapper and build files first (to cache dependencies)
#COPY my-metering-be/build.gradle.kts my-metering-be/settings.gradle.kts ./
#COPY my-metering-be/gradlew ./gradlew
#COPY my-metering-be/gradle ./gradle
#
## Preload dependencies
#RUN ./gradlew build -x test --no-daemon || return 0
#
## Copy the full source
#COPY my-metering-be/ .
#
## Build the project
#RUN ./gradlew clean build --no-daemon
#
## -------- Stage 2: Runtime Image --------
#FROM openjdk:21-jdk-slim
#
#WORKDIR /app
#
## Copy JAR from the builder stage
#COPY --from=builder /app/build/libs/*.jar app.jar
#
## Expose the default Spring Boot port
#EXPOSE 8080
#
## Run the application
#ENTRYPOINT ["java", "-jar", "app.jar"]


# -------- Stage 1: Build the Application --------
FROM openjdk:21-slim AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle wrapper and build files to the container
COPY gradlew build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

# Grant execute permissions to the Gradle wrapper
RUN chmod +x gradlew

# Copy the main application source code
COPY src ./src

# Download dependencies and build the application
RUN ./gradlew clean build --no-daemon

# -------- Stage 2: Create the Runtime Image --------
FROM openjdk:21-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
