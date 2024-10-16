# POS Backend API

## API Documentation

The full API documentation is available via Postman. It includes details on all endpoints, request/response formats, and status codes. ⬇️

https://documenter.getpostman.com/view/36189309/2sAXxTcAeT


## Overview
This project is a backend API for a Point of Sale (POS) system, developed with a well-structured layered architecture. The API adheres to RESTful principles, allowing interaction between the frontend and backend. The backend is built using **Spring Web MVC**, **Spring Data JPA**, and **Hibernate**, with **MySQL** as the database and comprehensive logging for monitoring and debugging.

## Tech Stack
- **Spring Web MVC**- Handles HTTP requests and responses, implementing the MVC design pattern.
- **Spring Data JPA**- Simplifies data access and persistence with a standard API.
- **Hibernate**- ORM tool that maps Java objects to database tables and manages relationships between them.
- **MySQL**- The relational database used for storing POS data.
- **Logging**- Implemented with appropriate logging levels (`INFO`, `DEBUG`, `ERROR`) to monitor and track system behavior.

## Setup & Installation

1. **Clone the repository**
```
  https://github.com/KusalDemo/Spring-POS-Backend.git
  ```
2. **Configure the Database**
- Set up your MySQL database.

3. **Update Hibernate Configuration**
- Update and configure the Hibernate settings for MySQL.
- Ensure the correct JDBC URL, username, and password are set for your database.

4. **Update Logger Configuration**
- Update the logger configuration for the application in logback.xml.

5. **Build & Deploy**
- Build the project using Maven.
- Run the Spring application.

6. **Access the Application**
- Once the backend is running, access the frontend application via your web browser.
- Frontend > https://github.com/KusalDemo/POS-Frontend.git

## Logging Configuration

The program makes use of an efficient logging system with several logging levels:

- `INFO` - Application flow in general.
- `DEBUG` - Comprehensive information for debugging.
- `ERROR` - serious mistakes that could possibly still allow the application to function.
- `WARN` - Risky situation.

## License

This project is licensed under the MIT License. For more details, see the [LICENSE](License)
