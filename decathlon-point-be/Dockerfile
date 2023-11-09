# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file to the container
COPY target/decathlon-point-be-0.0.1-SNAPSHOT.jar  app.jar

# Expose the port that your application listens on
EXPOSE 8080

# Set the entry point for the container
ENTRYPOINT ["java", "-jar", "app.jar"]