# Notes-Backend 🚀

This repository contains a **Spring Boot REST API** application designed for note management, built to demonstrate modern software development principles, containerization (Docker), and automated CI/CD practices.


---

## 🛠️ Tech Stack & Tools

* **Backend:** Java 25 / Spring Boot 3.x
* **Database:** PostgreSQL 15
* **Containerization:** Docker & Docker Compose
* **CI/CD Pipeline:** GitHub Actions
* **Container Registry:** Docker Hub

---

## 🏗️ Architecture & CI/CD Workflow

The automated Continuous Integration (CI) workflow follows these steps:

1.  **Code Push:** A developer pushes code changes to the `main` branch.
2.  **GitHub Actions Trigger:** The CI pipeline is automatically initiated on a remote runner.
3.  **Test & Build:** Maven runs unit tests and compiles the application package.
4.  **Docker Push:** Upon a successful build, a new Docker image is built and pushed directly to Docker Hub (`beykan/notes-backend:latest`).

---

## 🚀 Local Development Setup (How to Run)

You can spin up the entire infrastructure (Backend API & PostgreSQL database) locally with a single command using Docker Compose.

### Prerequisites
* Ensure that **Docker Desktop** is installed and running on your machine.

### Installation Steps

1.  Clone the repository:
    ```bash
    git clone [https://github.com/Beykn/Notes-Backend.git](https://github.com/Beykn/Notes-Backend.git)
    cd Notes-Backend
    ```

2.  Build and run the services using Docker Compose:
    ```bash
    docker compose up -d --build
    ```
    *Note: To avoid local port conflicts with any native database instances running on your host machine, PostgreSQL is exposed externally on port `5433`.*

3.  Verify that the containers are up and running:
    ```bash
    docker compose ps
    ```

4.  Stream live application logs to monitor the Spring Boot startup:
    ```bash
    docker compose logs -f notes-backend
    ```

Once the application successfully starts, the REST API will be accessible and ready to accept requests at **`http://localhost:8080`**.

---

## ⚙️ Environment Variables

The containerized environment relies on the following configuration parameters inside the Docker Compose architecture:

| Variable Name | Description | Default Value                              |
| :--- | :--- |:-------------------------------------------|
| `DB_URL` | Database connection string | `jdbc:postgresql://postgres-db:5432/notes` |
| `POSTGRES_USER` | PostgreSQL administrative user | `postgres`                                 |
| `POSTGRES_PASSWORD` | PostgreSQL user password | `******`                                   |
| `POSTGRES_DB` | Target database name | `notes`                                    |

---

## 💾 Data Persistence (Volumes)

To ensure that your notes are preserved when containers are stopped, restarted, or destroyed, a **named volume** (`postgres_data`) is mapped to the PostgreSQL data directory. This securely anchors database records on your host machine's storage.