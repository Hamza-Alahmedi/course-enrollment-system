# Use Maven with Java 17 for building
FROM maven:3.9-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml first to leverage Docker cache
COPY pom.xml .

# Download dependencies with forced updates
RUN mvn dependency:resolve dependency:resolve-plugins -B -U

# Copy source code
COPY src ./src

# Build the application (clean build, skip tests, update snapshots)
RUN mvn clean package -DskipTests -U

# Use smaller JRE image for running
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Copy the built jar from build stage
COPY --from=build /app/target/Course-Enrollment-System-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Render will set PORT environment variable)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

