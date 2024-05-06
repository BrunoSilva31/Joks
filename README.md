<h1 align="center"> Customer Reservation Management Project </h1>


[![Java](https://img.shields.io/badge/Java-11-blue)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.5.0-green)](https://spring.io/projects/spring-boot) [![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-2.5.0-green)](https://spring.io/projects/spring-data-jpa) [![Hibernate](https://img.shields.io/badge/Hibernate-5.4.32-red)](https://hibernate.org/) [![H2 Database](https://img.shields.io/badge/H2%20Database-latest-blue)](https://www.h2database.com/html/main.html) 

This project is a Spring Boot application for managing customer reservations at an establishment. It allows users to perform CRUD (Create, Read, Update, Delete) operations on customer reservations, and provides validations for time and establishment capacity. ## Features  - Registration of new customers with reservation time - Update and deletion of existing reservations - Listing of all reservations - Validation of available time slots for scheduling - Control of establishment capacity

<h1 align="center">How to run </h1>

1. Clone this repository on your local machine:
	  - git clone [https://github.com/BrunoSilva31/WorkJoks.git](https://github.com/your-username/repository-name.git)

2.  Navigate to the project directory:
	 - cd WorkJoks

3.  Run the Spring Boot application:
	- ./mvnw spring-boot:run

4. The application will be available at [http://localhost:8080](http://localhost:8080).

<h1 align="center"> API endpoints </h1>

- `GET /clientes`: Returns all customers and their reservations.
- `GET /clientes/{id}`: Returns details of a specific customer.
- `POST /clientes`: Creates a new customer with reservation.
- `PUT /clientes/{id}`: Updates details of an existing customer.
- `DELETE /clientes/{id}`: Deletes a customer and their reservation.
- `DELETE /clientes`: Deletes all past reservations.

## Example POST Request

To create a new customer with a reservation, send a POST request with the following JSON in the body:

```json
{
  "name": "Customer Name",
  "email": "customer@example.com",
  "cpf": "123.456.789-10",
  "phone": "(11) 99999-9999",
  "formattedDate": "dd/MM/yyyy HH:mm",
  "ambiente": "mezanino"
}
