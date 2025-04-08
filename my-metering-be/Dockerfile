# -------- Stage 1: Build the Application --------
FROM openjdk:21-slim AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle wrapper and build files to the container
COPY gradlew build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
COPY src ./src

# Grant execute permissions to the Gradle wrapper
RUN chmod +x gradlew

# Copy the main application source code
#COPY my-metering-be .

# Download dependencies and build the application
RUN ./gradlew clean build -x test

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
