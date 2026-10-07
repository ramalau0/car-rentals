# car-rentals

Spring Boot REST API for a vehicle rental system (Java 21, Spring Boot 4.1.1, H2 server mode).

## Run on a new machine

### 1. Install

- Java 21 (check with `java -version`)
- Maven (check with `mvn -v`, it must show Java 21)
- Git

### 2. Clone the project

```bash
git clone https://github.com/ramalau0/car-rentals.git
cd car-rentals
```

### 3. Download dependencies

```bash
mvn dependency:resolve
```

This also downloads the H2 jar that the database server needs.

### 4. Find the H2 version

Mac / Linux / Git Bash:

```bash
ls ~/.m2/repository/com/h2database/h2/
```

Windows PowerShell:

```powershell
dir $env:USERPROFILE\.m2\repository\com\h2database\h2
```

Note the version folder name (for example `2.4.240`). Use it in place of `<version>` below.

### 5. Start the database (Terminal 1, leave it running)

Run from the project root.

Mac / Linux / Git Bash:

```bash
java -cp ~/.m2/repository/com/h2database/h2/<version>/h2-<version>.jar org.h2.tools.Server -tcp -tcpPort 9092 -baseDir ./h2data -ifNotExists
```

Windows PowerShell (do not use `~`, PowerShell does not expand it for Java; keep the quotes):

```powershell
java -cp "$env:USERPROFILE\.m2\repository\com\h2database\h2\<version>\h2-<version>.jar" org.h2.tools.Server -tcp -tcpPort 9092 -baseDir ./h2data -ifNotExists
```

### 6. Start the app (Terminal 2, from the project root)

```bash
mvn spring-boot:run
```

Wait for `Started CarRentalApplication`. The test users and vehicles are created automatically on first start.

### 7. Open it

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
- Always start the database (step 5) before the app (step 6).
