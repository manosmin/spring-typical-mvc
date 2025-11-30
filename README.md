# MVC Bookstore Application

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.8-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-NeonDB-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)
![Auth0](https://img.shields.io/badge/Auth0-EB5424?style=for-the-badge&logo=auth0&logoColor=white)

A typical MVC application for bookstore management with OAuth 2.0 authentication.

## Tech Stack

- **Spring Boot 3.5.8** - Spring Data JPA, Spring Security, OAuth 2.0
- **PostgreSQL** - NeonDB cloud database
- **Auth0** - OAuth2 authentication provider
- **Thymeleaf** - Server side templating engine
- **Flyway** - Database migrations
- **MapStruct** - DTO mapping
- **Lombok** - Boilerplate reduction
- **Maven** - Build tool
- **Java 21** - LTS version

## Features

- OAuth2 authentication (Auth0/Google)
- CRUD operations for books
- Book rental system with status tracking
- User specific collections
- Caching for performance
- Database migrations with Flyway

## Screenshots

### Login (Auth0)
![Rental Details](images/brave_R9kR5eC5Vj.png)

### Home
![My Books](images/brave_jf6kOoqATE.png)

### Library
![All Books](images/brave_07s9H1WyhA.png)

### Create Book
![All Books](images/brave_y82xYh7Eb3.png)

### Rental History
![Rentals](images/brave_pGU8USrK3E.png)

## Database Schema

![Database Schema](images/database_er_diagram.svg)

## Folder Structure

```
src/
├── main/
│   ├── java/com/example/bookstore/
│   │   ├── controllers/    # Controllers
│   │   ├── services/       # Business logic
│   │   ├── repositories/   # Data access layer
│   │   ├── entities/       # JPA entities
│   │   ├── models/         # DTOs
│   │   ├── mappers/        # MapStruct mappers
│   │   ├── enums/          # Enumerations
│   │   ├── config/         # Security configuration
│   │   └── middleware/     # Exception handler
│   └── resources/
│       ├── db/migration/   # Flyway scripts
│       ├── templates/      # Views
└──       └── static/css/   # CSS
```
