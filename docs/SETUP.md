# 🛠️ IUCAT Setup & Usage Guide

This guide covers local setup, application usage, Docker, testing, observability, load testing, and deployment notes for the IUCAT Library Management System.

## 📋 Prerequisites

Install the following before starting:

- **Java 17**
- **Git**
- **Docker** — optional, for containerized execution
- **k6** — optional, for load testing

Verify Java:

```bash
java -version
```

The project includes the Maven wrapper, so a separate Maven installation is not required.

---

## 🚀 Run Locally

Clone the repository:

```bash
git clone https://github.com/gauri2029/iucat.git
cd iucat
```

Start the application:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
mvnw.cmd spring-boot:run
```

Open:

```text
http://localhost:8080
```

### Demo account

```text
Username: abc
Password: password123
```

This account is seeded for evaluation purposes only and contains no real user data.

---

## 🧭 Explore the Application

A useful evaluation flow is:

1. Sign in with the demo account
2. Search the book catalog
3. Open a book details page
4. Rent an available book
5. View the rental in **My Rentals**
6. Extend the rental when eligible
7. Return the book
8. Rent all available copies of another title
9. Join its hold queue
10. Return a copy and verify the next hold becomes ready

The public deployment is available at:

### [https://iucat-library.onrender.com](https://iucat-library.onrender.com)

Render may require a short cold-start period when the application has been inactive.

---

## 🗄️ Database Profiles

The repository currently uses:

- **H2** for the deployed application profile
- **SQLite** for local development

These choices reflect the current implementation of the project.

Review the configuration files under:

```text
src/main/resources/
```

Do not commit credentials, secrets, or environment-specific tokens to the repository.

---

## 🐳 Run with Docker

Build the image:

```bash
docker build -t iucat-library .
```

Run the container:

```bash
docker run --rm -p 8080:8080 iucat-library
```

Open:

```text
http://localhost:8080
```

Stop the running container with `Ctrl+C`.

---

## 🧪 Run Tests

Run the Maven test suite:

```bash
./mvnw test
```

Run a clean verification build:

```bash
./mvnw clean verify
```

The main user journeys to verify are:

1. Login → Search → Rent → Extend → Return
2. Rent all copies → Place hold → Return → Hold becomes ready → Pickup
3. Search with filters → Verify AJAX updates
4. Verify health and metrics endpoints
5. Build and start the Docker image

---

## 📊 Run the Load Test

Install k6 on macOS:

```bash
brew install k6
```

Run:

```bash
k6 run load-test.js
```

The baseline test included with the repository was executed with:

- 10 concurrent users
- 2-minute duration
- 660 total requests

Observed results:

| Metric | Result |
|---|---:|
| Success rate | 100% |
| Average response time | 75 ms |
| P95 response time | 109 ms |
| Maximum response time | 278 ms |
| Throughput | 5.37 requests/sec |

These values represent a controlled baseline test and may vary by machine, network, deployment state, and Render cold starts.

---

## 🔍 Observability

### Health

```bash
curl http://localhost:8080/actuator/health
```

### Liveness

```bash
curl http://localhost:8080/actuator/health/liveness
```

### Readiness

```bash
curl http://localhost:8080/actuator/health/readiness
```

### Prometheus metrics

```bash
curl http://localhost:8080/actuator/prometheus
```

### Correlation IDs

Send a request with a custom correlation ID:

```bash
curl -H "X-Correlation-ID: test-123" \
  http://localhost:8080/search
```

Search the application logs for:

```text
correlationId=test-123
```

This helps trace a request across application logs.

---

## 🔗 Main Routes

### Authentication and discovery

```text
POST /login
GET  /search?query={q}
GET  /books/{id}
```

### Rentals

```text
GET  /my-rentals
POST /rent/{id}
POST /extend/{id}
POST /return/{id}
```

### Holds

```text
GET  /my-holds
POST /holds/place/{id}
POST /holds/pickup/{id}
```

### Monitoring

```text
GET /actuator/health
GET /actuator/health/liveness
GET /actuator/health/readiness
GET /actuator/prometheus
```

---

## 🔄 CI/CD

The workflow is defined at:

```text
.github/workflows/ci-cd.yml
```

The pipeline is designed to:

1. Build the Spring Boot application
2. Run the automated tests
3. Build Docker images
4. Publish images to Docker Hub and AWS ECR
5. Trigger Render deployment
6. Support AWS ECS deployment

View workflow runs:

### [GitHub Actions](https://github.com/gauri2029/iucat/actions)

---

## ☁️ Deployment Notes

### Render

Render is the active public demo environment:

### [https://iucat-library.onrender.com](https://iucat-library.onrender.com)

The service deploys from the repository through the configured deployment workflow.

Because the environment may scale down during inactivity, the first request can take longer than subsequent requests.

### AWS ECS Fargate

The AWS deployment architecture was implemented using:

```text
Internet → Application Load Balancer → ECS Service → Fargate Tasks
```

Supporting services included:

- AWS ECR for container images
- ECS Fargate for application tasks
- Application Load Balancer for traffic routing
- GitHub Actions for deployment automation

The AWS services are currently paused to avoid ongoing costs after the available credits were used. The architecture and deployment configuration remain part of the project.

To redeploy later, verify the current AWS account, networking, ECR image, ECS task definition, service configuration, secrets, and billing settings before enabling resources.

---

## 🧯 Troubleshooting

### The Render app takes time to open

The public service may be starting from an inactive state. Wait briefly and refresh.

### Port 8080 is already in use

Find the process:

```bash
lsof -i :8080
```

Stop it or run the application on another port:

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

### Java version mismatch

Confirm Java 17:

```bash
java -version
```

Set the correct `JAVA_HOME` before running the application.

### Docker build fails

Rebuild without cached layers:

```bash
docker build --no-cache -t iucat-library .
```

### Tests fail because of stale build output

Run:

```bash
./mvnw clean test
```

### Health endpoints are unavailable

Check that the Spring Actuator dependency and endpoint exposure settings are enabled in the active application profile.

---

## 🔐 Security Notes

- The published demo credentials are for evaluation only
- Do not reuse the demo password for any real account
- Do not commit API keys, cloud credentials, database secrets, or webhook URLs
- Keep deployment secrets in GitHub Actions secrets or the cloud provider's secret-management system
- The public demo contains no real user data
