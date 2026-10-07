# car-rentals

Spring Boot REST API for a vehicle rental system (Java 21, Spring Boot 4.1.1, H2 server mode).

## Run on a new machine

### 1. Install

- Java 21 (check with `java -version`)
- Git

Maven is not needed, the project includes the Maven Wrapper (`mvnw`).

### 2. Clone the project

```bash
git clone https://github.com/ramalau0/car-rentals.git
cd car-rentals
```

### 3. Download dependencies

```bash
./mvnw dependency:resolve      # Windows: mvnw.cmd dependency:resolve
```

This also downloads the H2 jar that the database server needs.

### 4. Start the database (Terminal 1, leave it running)

Find the H2 version:

```bash
ls ~/.m2/repository/com/h2database/h2/
```

Start the server from the project root, replacing `<version>` with that folder name:

```bash
java -cp ~/.m2/repository/com/h2database/h2/<version>/h2-<version>.jar org.h2.tools.Server -tcp -tcpPort 9092 -baseDir ./h2data -ifNotExists
```

### 5. Start the app (Terminal 2)

```bash
./mvnw spring-boot:run         # Windows: mvnw.cmd spring-boot:run
```

Wait for `Started CarRentalApplication`. The test users and vehicles are created automatically on first start.

### 6. Open it

| What | URL |
|---|---|
| Swagger UI (try the API) | http://localhost:8080/swagger-ui/index.html |
| H2 console | http://localhost:8080/h2-console |

H2 console login: JDBC URL `jdbc:h2:tcp://localhost:9092/rentaldb`, user `sa`, password `rentalpass`.

## Test users

Password for all: `password123`

| Email | Role |
|---|---|
| admin@rental.com | ADMIN |
| user@rental.com | USER |
| user2@rental.com | USER |

## Stop and reset

- Stop: press Ctrl+C in each terminal. Data stays in `h2data/`.
- Reset the database: stop both, delete the `h2data` folder and start again.
- Always start the database (step 4) before the app (step 5).
