#  Delivery System with Docker Compose and Swagger

## Prerequisites
Ensure you have the following installed:
- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [Java 17+](https://adoptium.net/)
- [Maven](https://maven.apache.org/install.html)

## Getting Started
### Clone the Repository
```sh
git clone https://github.com/michaelandom/michael_delivery.git
cd michael_delivery
```
### Run with Docker Compose
```sh
docker-compose up 
```
This will start the application along with any dependencies (like a database) specified in `docker-compose.yml`.

### Access the Application
- **Spring Boot App:** `http://localhost:8085`
- **Swagger UI:** `http://localhost:8085/swagger-ui/index.html#/`

## Swagger API Documentation
Swagger is integrated using [Springfox](https://springdoc.org/).

## Stopping the Application
To stop and remove containers:
```sh
docker-compose down
```


## License
This project is licensed under the MIT License.

