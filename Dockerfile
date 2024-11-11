FROM openjdk:17 as build
WORKDIR /workspace/app

# Copy maven files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Make maven wrapper executable
RUN chmod +x mvnw
# Build the application
RUN ./mvnw install -DskipTests

# Create final image
FROM openjdk:17-slim
VOLUME /tmp
COPY --from=build /workspace/app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]