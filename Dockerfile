FROM amazoncorretto:17-alpine3.19-jdk as builder

# Copy local code to the container image.
WORKDIR /app
COPY target/backed-0.0.1-SNAPSHOT.jar .
# Run the web service on container startup.
CMD ["java","-jar","/app/backed-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]