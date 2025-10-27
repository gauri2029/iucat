# 📚 IU CAT – Library Management System

![CI/CD Pipeline](https://github.com/YOUR_USERNAME/iucat/actions/workflows/ci-cd.yml/badge.svg)
![Docker Image](https://img.shields.io/docker/v/YOUR_USERNAME/iucat-library?label=docker)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen)

A full-stack **Spring Boot web application** for managing a university library system.  
It allows users to **search, rent, and return books** with secure authentication and real-time availability tracking.

---

## 🚀 Live Demo  
🔗 **https://iucat-library.onrender.com/**

## 📊 CI/CD Pipeline

This project uses GitHub Actions for continuous integration and deployment:

- ✅ **Automated Testing**: Tests run on every push
- ✅ **Docker Build**: Automatic Docker image creation
- ✅ **Deployment**: Auto-deploy to Render.com

### Pipeline Status

| Stage | Status |
|-------|--------|
| Build & Test | ![Build](https://github.com/gauri2029/iucat/actions/workflows/ci-cd.yml/badge.svg) |
| Docker Image | [View on Docker Hub](https://hub.docker.com/r/YOUR_USERNAME/iucat-library) |
| Deployment | [Render.com Dashboard](https://dashboard.render.com) |

### Deployment Process
The project uses a multi-stage CI/CD pipeline:

**Stage 1: Build & Test**
- Checkout code
- Compile application
- Run unit tests

**Stage 2: Docker Build**
- Build Docker image (multi-stage)
- Push to Docker Hub
- Enable layer caching

**Stage 3: Deploy**
- Trigger Render.com deployment
- Verify deployment status
