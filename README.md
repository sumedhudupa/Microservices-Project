# Microservices Job Portal Application

A distributed microservices-based job portal application built with Spring Boot, enabling companies to post jobs and receive reviews.

## 🏗️ Architecture Overview

This application follows a microservices architecture pattern with the following components:

### Core Services
- **Company Service** (`companyms`) - Manages company information and profiles
- **Job Service** (`jobms`) - Handles job postings and job-related operations
- **Review Service** (`reviewms`) - Manages company reviews and ratings

### Infrastructure Services
- **Config Server** (`Config-server`) - Centralized configuration management for all microservices
- **Service Registry** (`service-reg`) - Service discovery and registration (Eureka Server)
- **API Gateway** (`Gateway`) - Single entry point for all client requests, handles routing and load balancing

## 🚀 Technology Stack

- **Backend Framework**: Spring Boot
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Configuration Management**: Spring Cloud Config
- **Database**: PostgreSQL
- **Containerization**: Docker & Docker Compose
- **Orchestration**: Kubernetes
- **Build Tool**: Maven

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker & Docker Compose
- Kubernetes cluster (for K8s deployment)
- PostgreSQL (if running without Docker)

## 🔧 Getting Started

### Running with Docker Compose

1. **Clone the repository**
   ```bash
   git clone https://github.com/sumedhudupa/Microservices-Project.git
   cd Microservices-Project
   ```

2. **Build all services**
   ```bash
   # Build each service
   cd companyms/companyms && mvn clean package
   cd ../../jobms/jobms && mvn clean package
   cd ../../reviewms/reviewms && mvn clean package
   cd ../../Config-server && mvn clean package
   cd ../gateway/gateway && mvn clean package
   cd ../../service-reg/service-reg && mvn clean package
   ```

3. **Start all services with Docker Compose**
   ```bash
   cd companyms/companyms
   docker-compose up -d
   ```

4. **Verify services are running**
   - Service Registry: http://localhost:8761
   - API Gateway: http://localhost:8080
   - Config Server: Registered with Eureka (no fixed port configured) 

### Running Locally (Development)

1. **Start Config Server first**
   ```bash
   cd Config-server
   mvnw spring-boot:run
   ```

2. **Start Service Registry**
   ```bash
   cd service-reg/service-reg
   mvnw spring-boot:run
   ```

3. **Start Core Services**
   ```bash
   # Terminal 1 - Company Service
   cd companyms/companyms
   mvnw spring-boot:run

   # Terminal 2 - Job Service
   cd jobms/jobms
   mvnw spring-boot:run

   # Terminal 3 - Review Service
   cd reviewms/reviewms
   mvnw spring-boot:run
   ```

4. **Start API Gateway**
   ```bash
   cd gateway/gateway
   mvnw spring-boot:run
   ```

## ☸️ Kubernetes Deployment

Deploy to Kubernetes using the provided manifests:

```bash
# Apply bootstrap configurations
kubectl apply -f k8s/bootstrap/

# Deploy services
kubectl apply -f k8s/services/

# Verify deployments
kubectl get pods
kubectl get services
```

## 📡 API Endpoints

All requests should go through the API Gateway at `http://localhost:8080`

### Company Service
- `GET /companies` - List all companies
- `GET /companies/{id}` - Get company by ID
- `POST /companies` - Create a new company
- `PUT /companies/{id}` - Update company
- `DELETE /companies/{id}` - Delete company

### Job Service
- `GET /jobs` - List all jobs
- `GET /jobs/{id}` - Get job by ID
- `POST /jobs` - Create a new job
- `PUT /jobs/{id}` - Update job
- `DELETE /jobs/{id}` - Delete job

### Review Service
- `GET /reviews` - List all reviews
- `GET /reviews/{id}` - Get review by ID
- `POST /reviews` - Create a new review
- `PUT /reviews/{id}` - Update review
- `DELETE /reviews/{id}` - Delete review

## 🗄️ Database

PostgreSQL is used as the database for all services. Database initialization scripts are located in:
- [companyms/companyms/db-init/postgres-init.sql](companyms/companyms/db-init/postgres-init.sql)

Each service has its own database schema for data isolation.

## 🔍 Service Discovery

All microservices register themselves with the Eureka Service Registry. The registry dashboard is available at:
- http://localhost:8761

## ⚙️ Configuration Management

Externalized configuration is managed by Spring Cloud Config Server, which serves configuration from a centralized location. Configuration files should be placed in the Config Server's repository.

## 🐳 Docker Support

Each service includes:
- [Dockerfile](companyms/companyms/Dockerfile) for containerization
- [Docker-compose.yml](companyms/companyms/Docker-compose.yml) for orchestration

### Pre-built Docker Images

Pre-built images are available on Docker Hub under the `sumedhudupa` account:

```bash
# Pull images from Docker Hub
docker pull sumedhudupa/companyms:latest
docker pull sumedhudupa/jobms:latest
docker pull sumedhudupa/reviewms:latest
docker pull sumedhudupa/config-server:latest
docker pull sumedhudupa/gateway:latest
docker pull sumedhudupa/service-reg:latest
```

### Building Docker Images Locally

Build individual Docker images from source:
```bash
cd <service-directory>
docker build -t <your-username>/<service-name>:latest .
```

Push to Docker Hub (requires authentication):
```bash
docker push <your-username>/<service-name>:latest
```

## 🛠️ Development

### Building the Project
```bash
# Build all services
mvnw clean install

# Build specific service
cd <service-directory>
mvnw clean package
```

### Running Tests
```bash
mvnw test
```

## 📝 Project Structure

```
├── companyms/          # Company management service
├── jobms/              # Job management service
├── reviewms/           # Review management service
├── Config-server/      # Centralized configuration
├── gateway/            # API Gateway
├── service-reg/        # Service registry (Eureka)
└── k8s/               # Kubernetes manifests
    ├── bootstrap/     # Bootstrap configs
    └── services/      # Service deployments
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'feat: Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📧 Contact

For questions or support, please open an issue in the repository.

## 🔗 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Cloud Documentation](https://spring.io/projects/spring-cloud)
- [Docker Documentation](https://docs.docker.com/)
- [Kubernetes Documentation](https://kubernetes.io/docs/)