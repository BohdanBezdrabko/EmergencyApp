# Use an official Java runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /EmergencyApp

# Copy the application JAR file to the container
COPY target/EmergencyApp-0.0.1-SNAPSHOT.jar app.jar
# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
