# Use OpenJDK image
FROM openjdk:21-jdk-slim

# Copy the JAR file
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Run the JAR
ENTRYPOINT ["java", "-jar", "/app.jar"]