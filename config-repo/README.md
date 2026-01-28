# Gramify Config Repository

This repository contains Spring Cloud Config configurations for Gramify services.

## Repository Structure

```
config-repo/
├── application.yml                    # Global configuration (applies to all services)
├── application-dev.yml               # Global dev environment configuration
├── application-prod.yml              # Global prod environment configuration
├── commons-post-service.yml          # Service-specific configuration
├── commons-post-service-dev.yml      # Service-specific dev configuration
├── commons-post-service-prod.yml     # Service-specific prod configuration
├── commons-iam-service.yml           # IAM service configuration
├── commons-domain-service.yml        # Domain service configuration
└── ...                               # Other service configurations
```

## Configuration Priority

Spring Cloud Config applies configurations in the following order (highest to lowest priority):

1. `{service-name}-{profile}.yml` (e.g., `commons-post-service-dev.yml`)
2. `{service-name}.yml` (e.g., `commons-post-service.yml`)
3. `application-{profile}.yml` (e.g., `application-dev.yml`)
4. `application.yml`

## Environment Variables

All sensitive information is externalized using environment variables:

- Database credentials: `COMMONS_DB_USERNAME`, `COMMONS_DB_PASSWORD`
- Service URLs: `COMMONS_SOLR_URL`, `COMMONS_ELASTIC_SEARCH_URL`
- AWS S3: `COMMONS_S3_ACCESS_KEY`, `COMMONS_S3_SECRET_KEY`
- API Keys: Masked in configuration files

## Usage

Services automatically fetch their configuration from the Config Server on startup based on:
- `spring.application.name` (service name)
- `spring.profiles.active` (environment profile)