FROM openjdk:21
ARG JAR_FILE=build/libs/metering-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} metering.jar
ENTRYPOINT ["java","-jar","/metering.jar"]