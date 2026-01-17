# 📚 IU CAT – Library Management System

![CI/CD](https://github.com/gauri2029/iucat/actions/workflows/ci-cd.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen)

Production-ready Spring Boot library management system with book rentals, holds/queue system, automated notifications, and multi-cloud deployment.

**Live Demo:** [https://iucat-library.onrender.com/](https://iucat-library.onrender.com/)

---

## 🏗️ Architecture
```mermaid
graph LR
    A[GitHub] --> B[GitHub Actions]
    B --> C[Docker Hub]
    B --> D[AWS ECR]
    C --> E[Render]
    D --> F[AWS App Runner]
    E --> G[Spring Boot App]
    F --> G
    G --> H[Health Checks]
    G --> I[Structured Logs]
```

---

## 🚀 Quick Start
```bash
# Clone and run
git clone https://github.com/gauri2029/iucat.git
cd iucat
./mvnw spring-boot:run

# Access at http://localhost:8080
```

**Test Credentials:** `abc` / `password123`

---

## ✨ Key Features

- ✅ **Book Rentals** - 14-day loans with 2x extensions
- ✅ **Holds/Queue System** - Automatic queue management
- ✅ **Advanced Search** - AJAX filters, 100+ books
- ✅ **Health Checks** - `/actuator/health` endpoints
- ✅ **Structured Logging** - JSON logs with correlation IDs
- ✅ **Multi-Cloud** - Render + AWS App Runner
- ✅ **CI/CD** - Automated deployment pipeline

---

## 🛠 Tech Stack

**Backend:** Java 17, Spring Boot 3.5.6, Spring Data JPA  
**Frontend:** Thymeleaf, HTML5, CSS3, JavaScript  
**Database:** H2 (prod), SQLite (dev)  
**DevOps:** Docker, GitHub Actions, AWS ECR, App Runner, Render  
**Observability:** Spring Actuator, Logback JSON, MDC Correlation IDs

---

## ☁️ Deployments

### Render.com (Active)
- **URL:** https://iucat-library.onrender.com
- **Deploy:** Auto on push to `main`

### AWS App Runner (Setup)
```bash
# Create ECR
aws ecr create-repository --repository-name iucat-library --region us-east-1

# Create IAM role
aws iam create-role --role-name AppRunnerECRAccessRole \
    --assume-role-policy-document file://apprunner-trust-policy.json
aws iam attach-role-policy --role-name AppRunnerECRAccessRole \
    --policy-arn arn:aws:iam::aws:policy/service-role/AWSAppRunnerServicePolicyForECRAccess

# GitHub Secrets Required: AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY, AWS_REGION, AWS_ACCOUNT_ID
```

---

## 🔍 Observability

### Health Checks
```bash
curl https://iucat-library.onrender.com/actuator/health
curl https://iucat-library.onrender.com/actuator/health/liveness
curl https://iucat-library.onrender.com/actuator/health/readiness
```

### Correlation IDs
```bash
curl -H "X-Correlation-ID: test-123" https://iucat-library.onrender.com/search
# Check logs for correlationId=test-123
```

---

## 📊 Load Test Results

**Test:** 10 concurrent users, 2 minutes, 660 requests

| Metric | Result | Status |
|--------|--------|--------|
| Success Rate | 100% | ✅ |
| Avg Response | 75ms | ✅ |
| P95 Response | 109ms | ✅ |
| Max Response | 278ms | ✅ |
| Requests/sec | 5.37 | ✅ |

**Run Test:**
```bash
brew install k6
k6 run load-test.js
```

---

## 🔄 CI/CD Pipeline

**Workflow:** Build → Test → Docker Build → Deploy

1. **Build & Test** - Maven compile + JUnit tests
2. **Docker (Render)** - Push to Docker Hub
3. **Docker (AWS)** - Push to ECR
4. **Deploy Render** - Webhook trigger
5. **Deploy AWS** - App Runner service update

**View:** [GitHub Actions](https://github.com/gauri2029/iucat/actions)

---

## 🗄 Database Schema
```sql
users (id, username, password, role)
books (id, isbn, title, author, available_copies, total_copies, publication_year, language, format, subject)
rentals (id, user_id, book_id, rental_date, due_date, return_date, status, extension_count, extension_limit)
holds (id, user_id, book_id, hold_date, expiration_date, status, queue_position)
```

---

## 🔗 API Endpoints

### Core
- `POST /login` - Authenticate
- `GET /search?query={q}` - Search books
- `GET /books/{id}` - Book details

### Rentals
- `GET /my-rentals` - List rentals
- `POST /rent/{id}` - Rent book
- `POST /extend/{id}` - Extend due date
- `POST /return/{id}` - Return book

### Holds
- `GET /my-holds` - List holds
- `POST /holds/place/{id}` - Place hold
- `POST /holds/pickup/{id}` - Pickup ready hold

### Monitoring
- `GET /actuator/health` - Health status
- `GET /actuator/health/liveness` - Liveness probe
- `GET /actuator/health/readiness` - Readiness probe

---

## 🧪 Testing
```bash
# Unit tests
./mvnw test

# Load test
k6 run load-test.js

# Docker
docker build -t iucat-library .
docker run -p 8080:8080 iucat-library
```

**Test Flows:**
1. Login → Search → Rent → Extend (2x) → Return
2. Rent all copies → Place hold → Return → Hold becomes ready → Pickup
3. Search with filters → Verify AJAX updates

---

## 📦 Project Structure
```
├── .github/workflows/ci-cd.yml    # CI/CD pipeline
├── src/main/
│   ├── java/.../config/
│   │   └── CorrelationIdFilter.java
│   ├── resources/
│   │   ├── application-aws.properties
│   │   └── logback-spring.xml
├── Dockerfile
├── load-test.js
└── pom.xml
```

---

## 📝 Configuration

**Profiles:**
- `default` - Local dev (SQLite)
- `aws` - AWS App Runner (H2, JSON logs)
- `render` - Render.com (H2, JSON logs)

**Environment Variables:**
- `SPRING_PROFILES_ACTIVE` - Profile name
- `SERVER_PORT` - Port (default: 8080)

---

## 🔗 Links

- **Repo:** [github.com/gauri2029/iucat](https://github.com/gauri2029/iucat)
- **Live:** [iucat-library.onrender.com](https://iucat-library.onrender.com)
- **Pipeline:** [GitHub Actions](https://github.com/gauri2029/iucat/actions)

---