# Compass-Uol-Projeto-2
For project organization we use the Kanban method on the Trello project management website.

Trello: https://trello.com/b/XYihFwsd/ong-desafio-dois


## Description
The Pet Adoption API is a RESTful API using Spring Boot, which aims to simulate the management of the pet adoption process. It allows you to use the four basic HTTP operations: create, retrieve, update, and delete information about pets available for adoption.

The requirements of this project are to model the domain, the implementation of the HTTP verb, in addition, the implementation of the database, good practices and clean code and finally, integration tests.

#

## Prerequisites
- Java 17 or higher installed
- IDE like IntelliJ or Eclipse

#

## Usage
Use tools like Insomnia, Postman, or any other HTTP client application to make HTTP requests to the API. Json with endpoints imports is in root of project
### Pet Api
- Access the API documentation at [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html) to view and test the available endpoints.

### Adoption Api
- Access the API documentation at [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html) to view and test the available endpoints.

#

## Directories and Packages
- Controllers -> classes and interfaces for controller http requisitions;
- Services -> classes and interfaces to validate operations and manage business logic and rules;
- Repository -> interfaces to manage data persistence in database;
- Exceptions -> classes responsible for managing and handling specific excess of the api;
- DTO -> classes (record) responsible for managing data transfer between api objects;
- Enums -> enum using to create entity and response objects;
- Clients -> classes and interfaces responsible for configuring, connecting, calling and mapping external api requests.

#

## Endpoints
### Pet Api
- url: [http://localhost:8081](http://localhost:8081).

#### Get
- /api/v1/pet -> all pets registered;
- /api/v1/pet/{id} -> returns pet by the passed id;
- /api/v1/pet/search?name=toto -> searches for all saved pets that have the value of toto in their name like "Toto" or "Totoro".

#### Post
- /api/v1/pet -> save a new pet.

#### Put 
- /api/v1/pet/{id} -> update a pet by id.

#### Delete
- /api/v1/pet/{id} -> delete a pet by id.

#### Patch
- /api/v1/pet/alterAdoptedStatus/{id} -> inverts the adopted state of a pet by id.


### Adoption Api
- url: [http://localhost:8082](http://localhost:8082).

#### Get
- /api/v1/adoption -> all the documents registered;
- /api/v1/adoption/{id} -> return a document by the id.

#### Post
- /api/v1/adoption -> save a new adoption document.

#### Delete
- /api/v1/adoption/{id} -> delete a adoption document by id.

#

## Running the project
- Open IntelliJ;
- Open the directory 'serverdiscovery/src/main/java';
- Run the java code 'ServerDiscoveryApplication';


After it is running, you can do the same for the other 3 folders named PetApi, AdoptionApi, gateway by following the same path and running the java code named AdoptionApiApplication, AdoptionDocApplication, and GatewayApplication, respectively for each directory.

After that, the entire application is connected and functional.

You can access the endpoints via insomnia, postman, http or another similar program.

#

## Technologies Used
- Java;
- Spring Boot;
- Spring Data JPA;
- Spring Cloud;
- Spring Web;
- Swagger;
- Actuator;
- Mockito;
- JUnit;
- H2 Database;
- Validation;
- Gateway;
- Lombok;
- Spring Test;

#

## Database
For a better user experience, we opted for the H2 relational database, which is an in-memory database, meaning no separate installation is required.
### Pet Api
- Access the database console at [http://localhost:8081/h2-console](http://localhost:8081/h2-console).
- Use the following connection information:
  - JDBC URL: jdbc:h2:file:./petApi;
  - User Name: sa;
  - Password: (leave blank).

### Adoption Api
- Access the database console at [http://localhost:8082/h2-console](http://localhost:8082/h2-console).
- Use the following connection information:
  - JDBC URL: jdbc:h2:file:./adoptionApi;
  - User Name: sa;
  - Password: (leave blank).

#

## Dependencies
- jackson-datatype-jsr310: responsible for converting the date and time of the 'java.time' package to JSON;
- jackson-databind: responsible for facilitating the conversion between JAVA and JSON objects;
- mockito-core: dependency to create mocks in unit tests;
- junit-jupiter-api: write unit tests using the JUnit 5 framework;
- springdoc-openapi-starter-webmvc-ui: automates API generation and documentation using the OpenAPI standard;
- spring-boot-starter-actuator: Spring Boot dependency that monitor and manage the application at runtime;
- spring-boot-starter-data-jpa: Spring Boot dependency that simplifies access to relational databases using JPA;
- h2: integrated relational database, that is, in memory, and lightweight, developed in Java;
- spring-boot-starter-validation: Spring Boot dependency for data validation in applications, such as data entry validations;
- spring-boot-starter-web: Spring Boot dependency for building web applications and RESTful services;
- spring-boot-devtools: offers features to speed up Spring Boot development, such as automatic browser refresh;
- lombok: reduces repetitive code by generating standard methods through annotations;
- spring-boot-starter-test: makes it easy to write and run tests in Java applications.

#

## Server Discovery
- Eureka server discovery acess by url [http://localhost:8761](http://localhost:8761).
- Discovers, monitors and balances the microservices registered in this specific discovery server.

#

## Tests
### Pet Api
- 100% coverage by entity methods, service controller and integration.

### Adoption Api
- 100% coverage service.

#

## Gateway
- Gateway acess by url port 8080.
- Creates a mask for the microservices connected to it, replacing the fixed address of all services, making them all use port 8080 + the specific url.
- After it was turned on, the pet-api and adoption-api microservices were being accessed through the port [http://localhost:8080/api/v1/pet/](http://localhost:8080/api/v1/pet/)*  e [http://localhost:8080/api/v1/adoption/](http://localhost:8080/api/v1/adoption/)*

#

### Group Members 
- Ana Luíza  -> github: https://github.com/analuztx
- Ian Paschoal -> github: https://github.com/IanZeta64
- Renato Hioji -> github: https://github.com/RenatoHioji
- Vinícius Pontes -> github: https://github.com/pontes1009
