# Metering Application

This repository contains a full‑stack metering application consisting of a Spring Boot backend and a React frontend built with Vite. The application provides functionalities for customer authentication, managing metering points, recording consumption data, and displaying market price information with cost calculations.

## Table of Contents

- [Overview](#overview)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Running the Application](#running-the-application)
  - [Using Docker Compose](#using-docker-compose)
- [API Endpoints](#api-endpoints)
- [Frontend Routing & Authentication](#frontend-routing--authentication)

## Overview

The Metering Application allows customers to:
- Log in.
- View their metering points.
- Retrieve market price data and compute cost breakdowns.
- Visualize data through interactive charts.

The backend is built with Spring Boot and connects to PostgreSQL for data persistence and Redis for caching. The frontend is built with React and uses libraries such as React Bootstrap, Formik, Yup, and echarts-for-react for data visualization.


## Technologies Used

- **Backend:** Spring Boot, PostgreSQL, Redis, Liquibase
- **Frontend:** React, Vite, Bootstrap, echarts-for-react, axios, Formik, Yup
- **Containerization:** Docker, Docker Compose
- **Testing:** JUnit (backend), Vitest and React Testing Library (frontend)

## Prerequisites

- Docker and Docker Compose
- Node.js (if running the frontend locally)
- Java JDK (if running the backend locally)

## Running the Application

### Using Docker Compose

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/De-Bola/my-metering.git
   ```
2. **Navigate to the backend project root:**
   ```bash
   cd my-metering/my-metering-be/
   ```
3. **Build backend jar using gradle:**
   ```bash
   ./gradlew clean build
   ```
4. **Navigate away from backend project root:**
   ```bash
   cd ..
   ```
5. **Start the application using Docker Compose:**
   ```bash
   docker-compose up --build
   ```
6. **Shutdown the application using Docker compose:**
   ```bash
   docker-compose down -v
   ```

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

## Frontend Routing & Authentication

This document explains how the frontend of the Metering Application handles routing and user authentication. It details how protected routes work, what happens after a successful login, and how the dashboard functions.

### Protected Routes

- **Unauthenticated Access:**  
  Unauthenticated users are prevented from accessing protected routes. If a user attempts to navigate to a protected page without a valid JWT token, they are automatically redirected to the login page.

- **Implementation:**  
  The application uses a `ProtectedRoute` component that checks for a JWT in `localStorage`. If the token exists, the user is allowed to access the route; otherwise, they are redirected to `/login`.

### Dashboard

- **Post-Login Behavior:**  
  After a successful login, the user is redirected to the dashboard.

- **Metering Points:**  
  Upon entering the dashboard, the application automatically fetches the authenticated customer’s metering points via a backend API call. These metering points are then rendered in a dropdown list, allowing the user to select a specific metering point.

- **Date Range Selection:**  
  The dashboard includes datepickers that let users set a start date and an end date.
  - The default behavior is to set the end date to today's date and the start date to one year before the end date.

- **Data Fetching:**  
  When a metering point is selected and the date range is defined, the dashboard fetches:
  - **Cost Data:** A cost breakdown for the selected metering point over the specified date range.
  - **Market Price Data:** Aggregated market price information over the same date range.

- **Charts:**  
  The dashboard displays the fetched data in interactive charts (using, for example, ECharts) to visualize:
  - Monthly cost breakdowns.
  - Market price trends.

### Login Flow

- **User Authentication:**  
  The login page allows the user to enter their credentials. On form submission:
  - The credentials are sent to the backend authentication endpoint (`/api/auth/login`).
  - Upon a successful login, the server returns a JWT and customer details.

- **Storage:**  
  The JWT token and customer details are stored in `localStorage` so that they can be used for subsequent API calls and to maintain the authenticated session.

- **Redirection:**  
  After storing the token, the user is redirected to the dashboard, where they can view their metering points and cost data.

### Summary

- **Protected Routes:** Ensure only authenticated users can access specific pages.
- **Dashboard:** Fetches user-specific metering points and allows dynamic selection of metering point and date range to fetch and visualize cost and market price data.
- **Login Flow:** Authenticates the user, stores the JWT in localStorage, and redirects to the dashboard.

This routing and authentication strategy ensures that users have a seamless experience while keeping secure areas of the application protected.
