# Compass-Uol-Projeto-2
For project organization we use the Kanban method on the Trello project management website.

Trello: https://trello.com/b/XYihFwsd/ong-desafio-dois

## Description
The Pet Adoption API is a RESTful API using Spring Boot, which aims to simulate the management of the pet adoption process. It allows you to use the four basic HTTP operations: create, retrieve, update, and delete information about pets available for adoption.

The requirements of this project are to model the domain, the implementation of the HTTP verb, in addition, the implementation of the database, good practices and clean code and finally, integration tests.


## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- Spring Web
- H2

## Dependencies
- jackson-datatype-jsr310: responsible for converting the date and time of the 'java.time' package to JSON
- jackson-databind: responsible for facilitating the conversion between JAVA and JSON objects
- mockito-core
- junit-jupiter-api
- springdoc-openapi-starter-webmvc-ui
- spring-boot-starter-actuator
- spring-boot-starter-data-jpa
- h2
- spring-boot-starter-validation
- spring-boot-starter-web
- spring-boot-devtools
- lombok
- spring-boot-starter-test

## Prerequisites
- Java 17 or higher installed
- H2 database
- Maven

## Usage
- Access the API documentation at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) to view and test the available endpoints.
- Use tools like cURL, Postman, or any other HTTP client application to make HTTP requests to the API.

## Database
For a better user experience, we opted for the H2 relational database, which is an in-memory database, meaning no separate installation is required.
- Access the database console at [http://localhost:8081/h2-console](http://localhost:8081/h2-console).
- Use the following connection information:
  - JDBC URL: jdbc:h2:file:./petApi
  - User Name: sa
  - Password: (leave blank)


## Group Members 
- Ana Pupo  -> github: https://github.com/analuztx
- Ian Paschoal -> github: https://github.com/IanZeta64
- Renato Hioji -> github: https://github.com/RenatoHioji
- Vinícius Pontes -> github: https://github.com/pontes1009
