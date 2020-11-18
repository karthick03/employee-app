# Employee-App
An Employee CRUD application using **Spring Boot and jOOQ** :)

## Pre-requisites
Kindly make sure the below softwares are installed.
- **Docker**
- **Postman**

## Run Standalone Postgres instance using Docker
Run the below commands in terminal or cmd prompt.
- Pull Postgres image
	- `docker pull postgres`
- Run Postgres image and expose Port `5432`.
	- `docker run --name postgres-instance -e POSTGRES_PASSWORD=password -e POSTGRES_USER=user -d -p 5432:5432 postgres`.
- Go inside the container with `/bin/bash`.
	- `docker exec -it postgres-instance /bin/bash`.
- Go to psql shell and follow the below steps.
	- `psql -U user`.
	- `create database employee;`.
	- `\c employee`.
- Execute DDL commands under the below path.
	- `/EmployeeJooq/src/main/resources/employee_schema.sql`.

### How to generate class file with jOOQ maven plugin
- This requires postgres instance running in the mentioned `server:port`.
- Generate the sources by `mvn generate-sources -P local`.
- Generate the sources along with jar using `mvn clean install -P local`.
- **Note:** Profile `local` is mentioned, because it will pick all the DB properties from the local properties under `/EmployeeJooq/src/main/resources/application-local.properties`.

### How to run the application
- Local Environment
	- Make sure to run standalone postgres instance (Refer section - `Run Standalone Postgres instance using Docker`).
	- Run the class under `/EmployeeJooq/src/test/java/com/app/EmployeeJooq/EmployeeJooqApplicationTest.java`.
	- This test class by default will use `spring.profiles.active=local`.
- Docker Environment
	- `.jar` generation can be done with `mvn clean install -P local`.
	- Go to the project location with terminal or cmd and run it using `docker-compose up --build`.
	- The above command will bring up a `postgres` instance (Make sure not to have any instance running in the same port mentioned) and starts the `EmployeeJooq` instance.

### Endpoints
- Kindly import the postman collection using `/EmployeeJooq/src/main/resources/EmployeeCRUD.postman_collection.json` and test the API's available.
