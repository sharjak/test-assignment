## Customer Management API

## Project Overview

This project is built with:
- **Java 17**
- **Gradle 8.11.1**
- **Spring Boot 3.4.2**

It follows **Hexagonal Architecture**, which separates business logic from external concerns, making the system more modular and maintainable.

## Setup

1. Build the project:
   ```sh
   ./gradlew clean build
   ```
2. Run Liquibase migrations from the Liquibase module:
   ```sh
   ./gradlew :liquibase:update
   ```
3. Run the application:
   ```sh
   ./gradlew bootRun
   ```
4. Access Swagger UI:
   ```
   http://localhost:8080/swagger-ui.html
   ```
5. Access H2 Console:
   ```
   http://localhost:8080/h2-console
   ```
6. Generate test coverage report:
   ```sh
   ./gradlew jacocoRootReport
   ```

