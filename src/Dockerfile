# Use a Maven image to build the application
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory inside the builder container
WORKDIR /app

# Copy the pom.xml and the source code
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Use a slim image with just the JDK to run the application
FROM openjdk:17-jdk-slim

# Set the working directory inside the final container
WORKDIR /app

# Copy the packaged jar file from the builder container
COPY --from=build /app/target/ratelimiter-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application runs on
EXPOSE 8080

# Set the entry point for the container to run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]