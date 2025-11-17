# Build stage
FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /app
COPY pom.xml .
COPY rag-app-svc-base/pom.xml rag-app-svc-base/
COPY rag-app-svc-web/pom.xml rag-app-svc-web/
# Download dependencies first (for better caching)
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage
FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /app/rag-app-svc-base/target/rag-app-svc.jar app.jar
EXPOSE 9081
ENTRYPOINT ["java", "-jar", "app.jar"]
