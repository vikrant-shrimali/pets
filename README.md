# Pooch Palace Pets

A Spring Boot REST service that helps people find an adoptable dog through a conversational AI assistant. It retrieves dog details from a PostgreSQL/pgvector store, uses PostgresML to create embeddings, and uses Google Gemini to generate answers. Conversation history is stored in PostgreSQL.

## Requirements

- Java 25
- PostgreSQL with the PostgresML and pgvector capabilities available (the project is configured for a PostgresML instance on `localhost:5433`)
- A Google Gemini API key

## Configuration

The default local database settings are in `src/main/resources/application.properties`:

| Setting | Default |
| --- | --- |
| JDBC URL | `jdbc:postgresql://localhost:5433/postgresml` |
| Database user | `myappuser` |
| Database password | `mypassword` |
| Gemini model | `gemini-2.5-flash` |

The Gemini key is read from the `GEMINI_API_KEY` environment variable. Set it before starting the app:

```powershell
$env:GEMINI_API_KEY = "your-api-key"
```

For a different database or credentials, update the Spring datasource settings in `application.properties` (or override them with Spring Boot environment variables). The database user needs permission to create/use the required schema and extensions. `db/users.sql` contains example role and grant statements for the local setup.

## Run locally

1. Start and configure a PostgreSQL/PostgresML server reachable at the configured JDBC URL. The scripts and SQL under `db/` are examples for local database provisioning; review the database name and credentials against your own server before using them.
2. Set `GEMINI_API_KEY` as shown above.
3. Start the application from the repository root:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

   On macOS or Linux, use `./mvnw spring-boot:run`.

Spring SQL initialization creates the `dog` table and loads the sample dogs from `src/main/resources/schema.sql` and `data.sql`. On startup, the application adds dog descriptions to the vector store when it is empty.

## API

Ask the assistant a question with `GET /{user}/assistant?question=...`. The `{user}` path value identifies the conversation, so reuse it to continue that user's chat.

```http
GET http://localhost:8080/alex/assistant?question=Which%20dogs%20are%20calm%3F
```

The response body is the assistant's plain text answer. The assistant is configured to help with dog adoption at Pooch Palace locations and to respond politely when no dog information is available.

## Project structure

- `src/main/java/com/example/pets/controller/AdoptionController.java` — assistant endpoint and AI/vector-store setup
- `src/main/java/com/example/pets/model/` — dog records
- `src/main/java/com/example/pets/repository/DogRepository.java` — Spring Data JDBC repository
- `src/main/resources/schema.sql` and `data.sql` — dog table and sample records
- `db/` — example database setup scripts and grants

## Build

```powershell
.\mvnw.cmd package
```

The project also includes Spring Boot Actuator. The current configuration exposes all actuator endpoints and detailed health information; restrict these settings appropriately before deploying the service outside a trusted local environment.
