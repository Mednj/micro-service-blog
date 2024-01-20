# Use a base image with Java and a minimal OS
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/*.jar /app/app.jar

# Expose the port that the application will run on
EXPOSE 9091

# Specify the command to run on container start
CMD ["java", "-jar", "app.jar"]
