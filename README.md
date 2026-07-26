# Job Search App - Clean Architecture

Spring Boot application that searches jobs from multiple providers and exposes them through one clean domain model.

The project is built around **Clean Architecture**, **Hexagonal Architecture**, and **SOLID**. External APIs stay at the edges. The application core works with ports, use cases, and domain records.

![Hexagonal Architecture](docs/images/hexagonal-architecture-full.svg)

## What It Does

- Receives a job search request through HTTP.
- Converts that request into `JobSearchCriteria`.
- Queries every configured `JobProvider`.
- Maps provider-specific data into the domain `Job`.
- Returns one unified response.

Current providers:

- Jobicy
- LinkedIn
- Internal jobs placeholder

## Stack

| Area | Tool |
| --- | --- |
| Language | Java 21 |
| Framework | Spring Boot 4.1.0 |
| Web | Spring Web MVC |
| HTTP client | Spring `RestClient` |
| Build | Maven |
| HTML parsing | jsoup |
| Tests | JUnit / Spring Boot Test |

## Run

Default URL:

```text
http://localhost:8080
```

## API

### Search Jobs

```http
POST /jobs/search
Content-Type: application/json
```

Request:

```json
{
  "text": "java",
  "location": "argentina",
  "remote": true
}
```

Response:

```json
[
  {
    "externalId": "12345",
    "source": "jobicy",
    "title": "Java Backend Developer",
    "company": "Example Company",
    "location": "Argentina",
    "creationDate": "2026-07-13T00:00:00Z"
  }
]
```

## Architecture

![Execution Flow](docs/images/execution-flow-sequence-full.svg)

Dependency direction:

```text
adapter/in/web
    -> application/port/in
        -> application/service
            -> application/port/out
                -> adapter/out/*

domain stays independent
```

The rule:

> The core does not depend on external APIs, frameworks, HTML, JSON payloads, or provider DTOs.

That means:

- `domain` does not know Spring.
- `domain` does not know Jobicy.
- `domain` does not know LinkedIn.
- `JobSearchService` depends on `JobProvider`, not on HTTP clients.
- Each provider can change without leaking details into the use case.


## Application Service

[`JobSearchService.java`](src/main/java/com/hmeclazcke/jobsearchapp/application/service/JobSearchService.java)

```java
@Service
@RequiredArgsConstructor
public class JobSearchService implements SearchJobsUseCase {

    private final List<JobProvider> jobProviders;

    @Override
    public List<Job> search(JobSearchCriteria criteria) {
        return jobProviders.stream()
                .flatMap(jobProvider -> jobProvider.search(criteria).stream())
                .toList();
    }
}
```

Spring injects every `JobProvider` bean into the list. The service does not know how many providers exist.

## Plugin-style Providers

![Provider Extension](docs/images/provider-extension.svg)

Each job source behaves like a small static plugin. It brings its own client, DTO, and mapper, then Spring wires it into the app through `JobProvider`.

The shared shape is:

```text
JobProvider = JobClient<T> + JobMapper<T>
```

Common contracts:

- [`JobClient<T>`](src/main/java/com/hmeclazcke/jobsearchapp/adapter/out/common/JobClient.java): fetches provider DTOs.
- [`JobMapper<T>`](src/main/java/com/hmeclazcke/jobsearchapp/adapter/out/common/JobMapper.java): maps provider DTOs to `Job`.
- [`GenericJobProvider<T>`](src/main/java/com/hmeclazcke/jobsearchapp/adapter/out/common/GenericJobProvider.java): combines client and mapper.

This keeps each provider small:

```text
external API -> DTO -> mapper -> Job
```


## Add a Provider

1. Create a provider DTO.
2. Create a `JobClient<T>`.
3. Create a `JobMapper<T>`.
4. Register a `JobProvider` bean in [`AppConfig`](src/main/java/com/hmeclazcke/jobsearchapp/config/AppConfig.java).

Example:

```java
@Bean
public JobProvider exampleJobsProvider(
        HttpExampleJobsApiClient client,
        ExampleJobMapper mapper) {
    return new GenericJobProvider<>(client, mapper);
}
```

No change is needed in `JobSearchService`.

## SOLID Notes

| Principle | How the project applies it |
| --- | --- |
| SRP | Clients call APIs, mappers map data, service orchestrates providers. |
| OCP | New providers can be added without changing the use case. |
| LSP | Any `JobProvider` can be used by `JobSearchService`. |
| ISP | Interfaces are small: `JobProvider`, `JobClient`, `JobMapper`. |
| DIP | The service depends on `JobProvider`, not concrete HTTP clients. |


## Configuration

[`application.properties`](src/main/resources/application.properties)

```properties
spring.application.name=job-search-app

jobicy.api.base-url=https://jobicy.com
linkedin.api.base-url=https://www.linkedin.com
```
