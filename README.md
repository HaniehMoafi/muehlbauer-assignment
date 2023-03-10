# Order Service

Order service is a JAVA project which can save, cancel orders and get their status
Reported by Muehlbauer.de company

## Clone

run the below command:

```bash
git clone https://github.com/HaniehMoafi/muehlbauer-assignment-order.git
```


## API documentation

[Swagger URL](http://localhost:8080/swagger-ui/index.html)

## Database

[H2 database](http://localhost:8080/h2-console/login.jsp?jsessionid=c35e72e8aabddad7d15909344a617e43)
- username : sa
- password : sa
- jdbc url : jdbc:h2:mem:order


## Instruction

build the api module (order-api) and push on an artifactory:

```bash
cd order-api
docker build .
```

for building the order service run this command:

```bash
cd order
docker build -t order:1.0.0 .
```

then to run the application:

```bash
cd order
docker-compose up
```
