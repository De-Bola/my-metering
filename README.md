# Metering Application

This repository contains a full-stack metering application built with **Spring Boot** (backend) and **React + Vite** (frontend). It provides features like customer authentication, metering point tracking, energy consumption recording, and cost breakdown visualizations using market price data.

---

## 📚 Table of Contents

- [Overview](#-overview)
- [Tech Stack](#-tech-stack)
- [Prerequisites](#-prerequisites)
- [Running the Application](#running-the-application)
- [API Overview](#api-endpoints)
- [Frontend Overview](#-frontend-overview)
- [Project Structure](#-project-structure)

---

## 🧭 Overview

Customers using this platform can:

- Log in and manage their accounts
- View their metering points
- Record energy consumption data
- Get real-time market prices and cost analytics
- Visualize historical usage and billing data

---

## 🛠️ Tech Stack

### Backend
- Java + Spring Boot
- PostgreSQL
- Liquibase (DB migrations)
- Redis (caching)
- JWT (authentication)

### Frontend
- React (with Vite)
- React Bootstrap
- Formik + Yup (form handling)
- echarts-for-react (visualizations)

### DevOps
- Docker
- Docker Compose

---

## ✅ Prerequisites

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- Java 21 (if running the backend locally)
- Node.js (if running the frontend locally)

---

## Running the Application

### Using Docker Compose

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/De-Bola/my-metering.git
   ```
2. **Navigate to the backend project root:**
   ```bash
   cd my-metering/
   ```
3. **Start the application using Docker Compose:**
   ```bash
   docker-compose up --build
   ```
4. **Shutdown the application using Docker compose:**
   ```bash
   docker-compose down -v
   ```
---
## API Endpoints
To see the API doc use the following [link](http://localhost:8080/swagger-ui/index.html) when the app is running.

| Method   | Endpoint                                       | Description                              |
|----------|------------------------------------------------|------------------------------------------|
| **POST** | `/api/auth/register`                           | Register a new customer                  |
| **POST** | `/api/auth/login`                              | Login and get JWT token                  |
| **GET**  | `/api/customers/me`                            | Get currently logged-in customer details |
| **GET**  | `/api/market-prices`                           | Get market-prices by proxy               |
| **GET**  | `/api/metering-points`                         | Get logged-in customer's metering points |
| **GET**  | `/api/metering-points/{meteringPointId}/costs` | Get currently logged-in customer details |

### Notes:
- **{meteringPointId}** represents a metering point’s unique ID for the currently logged-in customer.
- **Query parameters** (e.g., `?startDateTime=2024-05-31T20:59:59.999Z`) `**/market-prices` and `**/costs` takes mandatory parameters `startDateTime` & `endDateTime` for filtering based on dateTime.
---

## 🎨 Frontend Overview

The frontend is a React SPA created with Vite. Key features:

- JWT-based authentication and route protection
- Dashboard displaying metering and billing data
- Responsive design using React Bootstrap
- Dynamic charts via `echarts-for-react`
- Form validation using Formik + Yup

---

## 📁 Project Structure
````
my-metering/
├── my-metering-be/       # Spring Boot backend  
├── my-metering-fe/       # React + Vite frontend  
├── docker-compose.yml    # Container orchestration  
├── README.md             # Project documentation
````
---