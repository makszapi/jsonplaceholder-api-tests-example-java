# JSONPlaceholder API Tests (Java + RestAssured)

API tests example for [JSONPlaceholder](https://jsonplaceholder.typicode.com/) built with Java, RestAssured, JUnit 6, and Gradle.

### Quick Start

```bash
# Linux/macOS
./gradlew functionalTest

# Windows (PowerShell)
.\gradlew.bat functionalTest
```

Open report after run:

- `test-results/junit/functionalTest/html/index.html`

This runs all tests annotated with the `Functional`.

To run all tests (including annotated with `Contract` and `E2E` tags), use the `test` task:

```bash
# Linux/macOS
./gradlew test

# Windows (PowerShell)
.\gradlew.bat test
```

### Tech Stack

Java 25, Gradle 9.3.1 (Wrapper), JUnit 6, RestAssured, AssertJ, Allure

### Project Structure

```text
src/
  main/java/dev/maksymzapisov/jsonplaceholder/
    clients/      # API clients (users, posts)
    config/       # Environment/config provider
    data/         # Test data provider
    model/        # DTO models
    specs/        # Request/response specs
    utils/        # Reusable response extractors
  test/java/dev/maksymzapisov/jsonplaceholder/
    users/        # Users endpoint tests
    posts/        # Posts/Comments endpoint tests
    e2e/          # End-to-end tests
    BaseApiTest   # Common test setup
```

### Test Tags

Tests are grouped by JUnit tags:

- `Functional` - functional endpoint behavior and search/get scenarios
- `Contract` - JSON schema validation checks
- `E2E` - full e2e workflow

### Prerequisites

- Java 25 installed (use any provider or version manager your preference: [Eclipse Temurin (Adoptium)](https://adoptium.net/temurin/releases/), [SDKMAN](https://sdkman.io/),
[Oracle JDK](https://www.oracle.com/java/technologies/downloads/))
- Use Gradle Wrapper (`./gradlew`), no local Gradle installation required

### Configuration

Default configuration is in `src/test/resources/api.properties`:

```properties
api.base.uri = https://jsonplaceholder.typicode.com/
api.base.path =
api.port = 443
log.all = false
```

Notes:

- Configuration is loaded via OWNER with merge policy (`system properties` + `classpath` file)
- `log.all = true` enables full request/response logging for all tests; when `false`, logging is triggered only on validation failure
- You can override values from command line, for example:

```bash
./gradlew test -Dapi.base.uri=https://jsonplaceholder.typicode.com/
```

### Run Tests

Linux/macOS:

```bash
./gradlew test # runs all tests (functional, contract, e2e)
./gradlew contractTest # runs only contract tests
./gradlew functionalTest # runs only functional tests
./gradlew e2eTest # runs only e2e tests
```

Windows (PowerShell):

```powershell
.\gradlew.bat test # runs all tests (functional, contract, e2e)
.\gradlew.bat contractTest # runs only contract tests
.\gradlew.bat functionalTest # runs only functional tests
.\gradlew.bat e2eTest # runs only e2e tests
```

### Test Reports

JUnit reports are generated under `test-results/junit` per task:

- XML: `test-results/junit/<task-name>/xml`
- HTML: `test-results/junit/<task-name>/html`

Allure results directory:

- `test-results/allure-results`

To generate and view the Allure report, refer to the [Allure JUnit documentation](https://allurereport.org/docs/junit5/)

## CI/CD (CircleCI)

The project includes a CircleCI pipeline (`.circleci/config.yml`) that runs on every push.

**What the pipeline does:**

1. Checks out the code
2. Restores cached Gradle dependencies (keyed on `build.gradle.kts` checksum)
3. Runs all tests via `./gradlew test`
4. Stores JUnit XML/HTML reports and Allure results as artifacts

**Environment:**

- Docker image: `cimg/openjdk:25.0.1`

### Coverage Summary

- **Users**: get all, get by valid/invalid id, search by valid/invalid query combinations
- **Posts**: get all, get by valid/invalid id, search by valid/invalid query combinations
- **Comments**: get comments for post, search comments with valid/invalid query combinations
- **E2E**: search user by username -> search user's posts by userId -> 
fetch all comments for each post -> for each comment, validate email format

Negative scenarios are primarily covered in atomic endpoint tests (non-E2E).

### Design Notes

- Request/response specs are centralized for consistency
- API calls are encapsulated in client classes
- DTO models are strongly typed and reused across tests
- Response extraction is centralized to reduce boilerplate in tests
- Tests are tagged for flexible execution

