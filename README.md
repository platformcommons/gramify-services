# Gramify Services

This is a multi-module Maven project containing various Spring Boot services for the Gramify platform.

## Project Structure

The project is organized into several microservices, each with its own responsibilities:

- **commons-app-config-service**: Manages application configurations.
- **commons-assessment-reporting-service**: Handles reporting for assessments.
- **commons-assessment-service**: Core service for managing assessments.
- **commons-blocks-profiles-service**: Manages user profiles and blocks.
- **commons-domain-service**: Domain-specific logic and data management.
- **commons-iam-service**: Identity and Access Management service.
- **commons-ie-service**: Import/Export service.
- **commons-post-service**: Manages posts and related content.
- **commons-report-service**: General reporting service.
- **commons-search-service**: Search functionality across the platform.

Each service typically follows a modular structure:
- `dto`: Data Transfer Objects.
- `client`: Client library for inter-service communication.
- `avro`: Avro schemas and generated classes (where applicable).
- `service` or `application`: The core Spring Boot application.

## Prerequisites

- Java 1.8
- Maven 3.8+

## Building the Project

To build the entire project and all its modules, run the following command from the root directory:

```bash
mvn clean install
```

To build a specific module:

```bash
mvn clean install -pl <module-name> -am
```
For example:
```bash
mvn clean install -pl commons-iam-service -am
```

## Running the Services

Each service can be run individually from its respective directory. Navigate to the `service` (or `application`) submodule and run:

```bash
mvn spring-boot:run
```

## Build Scripts

- `build.sh`: A shell script to clean and install the entire project.

## CI/CD

The project uses CI-friendly versions (`${revision}${changelist}`). You can pass these as properties:

```bash
mvn clean install -Drevision=1.0.0 -Dchangelist=-RELEASE
```
