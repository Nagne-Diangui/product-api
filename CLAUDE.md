# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
# Build
./mvnw clean package

# Run application
./mvnw spring-boot:run

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=ProductApiApplicationTests

# Build Docker image (after packaging)
docker build -t product-api .
```

## Architecture

This project is a thin **presentation layer** — it only contains REST controllers. All business logic and data access lives in an external dependency: `product-core` v0.3.0 (a separate Maven artifact).

**Package:** `sn.isi.l3gl.api`

The main application class scans both `sn.isi.l3gl.api` (this project) and `sn.isi.l3gl.core` (from the core library), allowing Spring to pick up beans from the core module.

**Current endpoints** (`ProductController`):
- `POST /api/products` — create product
- `GET /api/products` — list all products
- `PUT /api/products/{id}` — update product quantity
- `GET /api/products/low-stock/count` — count low-stock products

Controllers use `@RequiredArgsConstructor` (Lombok) for injecting services from `product-core`.

## Tech Stack

- **Spring Boot 4.0.3**, Java 17
- **MySQL** on port 3306 (`product_db` database), accessed via `host.docker.internal` when running in Docker
- **Lombok** for boilerplate reduction
- **Spring Actuator** for health/metrics endpoints
- App runs on port **8086**

## External Dependencies

The `product-core` library (resolved from a local Nexus instance at `http://localhost:8081`) provides:
- `ProductService` — business logic
- Product model/entity
- Repository/persistence layer

If Nexus is unavailable, the build will fail on dependency resolution. Ensure the local Nexus is running and `product-core:0.3.0` is published there.

## Database

Configured via `src/main/resources/application.properties`. Hibernate DDL is set to `update` — schema changes are auto-applied on startup. SQL logging is enabled (`show-sql=true`).
