# 🧩 SkillSync - Profile Service

**Profile-Service** is a core microservice within the SkillSync architecture, responsible for managing user profile data. It provides a RESTful API for full CRUD operations, built with Spring Boot and enhanced with Swagger documentation and unit testing.

## 🚀 Technologies Used 
- Java 17 + Spring Boot
- Spring Data JPA
- H2 / PostgreSQL
- Lombok
- Swagger (OpenAPI)
- JUnit 5 + Mockito
- Docker-ready

## 📦 Features
- Create, read, update, and delete user profiles
- DTO mapping and service-layer business logic
- Interactive API documentation via Swagger UI
- Unit tests for core functionality 

## 📁 API Endpoints

| Method | Endpoint           | Description                     |
|--------|--------------------|---------------------------------|
| GET    | `/profiles`        | Retrieve all user profiles      |
| POST   | `/profiles`        | Create a new user profile       |
| GET    | `/profiles/{id}`   | Get profile by ID               |
| PUT    | `/profiles/{id}`   | Update existing profile         |
| DELETE | `/profiles/{id}`   | Delete profile by ID            |

## 🧪 Testing
Run unit tests with:
```bash
./mvnw test
```

## 📜 Swagger UI
Available at: http://localhost:9090/swagger-ui.html

## 🐳 Docker (optional)
This service is container-ready. To build and run locally:

```bash
docker build -t profile-service .
docker run -p 8080:8080 profile-service
