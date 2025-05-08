<p align="center">
  <img src="https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/SpringBoot-3.x-green?style=for-the-badge&logo=springboot"/>
  <img src="https://img.shields.io/badge/SpringSecurity-Authentication-brightgreen?style=for-the-badge&logo=springsecurity"/>
  <img src="https://img.shields.io/badge/MySQL-Relational-blue?style=for-the-badge&logo=mysql"/>
  <img src="https://img.shields.io/badge/Redis-Cache-red?style=for-the-badge&logo=redis"/>
  <img src="https://img.shields.io/badge/Gemini-API-lightgray?style=for-the-badge&logo=google"/>
  <img src="https://img.shields.io/badge/SpringDoc-OpenAPI-8CC <color>?style=for-the-badge&logo=openapiinitiative&logoColor=white"/>
</p>

<h1 align="center">☕ CoffeeReturn - Backend</h1>
<h3 align="center">A cup of coffee for the Earth,<br>Backend system for an eco-friendly map-based platform for the circulation of coffee grounds</h3>

<br/>

<p align="center">
  <img src="https://user-images.githubusercontent.com/your-image-path/demo.gif" width="70%" alt="CoffeeReturn Backend Overview or Demo">
</p>

---

## 📌 Project Overview

> **CoffeeReturn** is a platform designed to reduce the indiscriminate disposal of coffee grounds and
> **an eco-friendly collection network platform connecting cafes and users.**
> This repository manages the code for the project's **backend system**. It is responsible for core business logic such as handling user requests, data management, and external service integration.

---

## 🎯 Project Goals

- ♻️ **Promote the recycling of coffee grounds**
- 👥 **Automate matching between users and cafes**
- 🧠 **Provide AI solutions based on Gemini API**
- 🌍 **Foster an eco-friendly practice culture**

---

## 🧩 Key Features

The backend implements and supports the core APIs and business logic for the following key features provided by the frontend.

### 🗺️ 1. Home Screen (Map-based Collection Point Search)
- Display collection points on map based on current location
- Marker click → Detail information popup
- Support cafe/public/corporate filter functions

### 🔐 2. Login / Sign-up
- Spring Security and JWT-based user authentication/authorization system
- Kakao / Google social login integration logic

### 🧑‍🍳 3. Cafe Management Page
- Provide API for cafe information registration, lookup, modification, deletion
- Implement API for cafe operators to register grounds, manage collection requests (acceptance/rejection, etc.)
- Provide API for managing my cafe information

### 👤 4. User My Page
- Provide API for viewing collection application history
- Implement API for status check (Pending / Accepted / Completed)
- Provide API for viewing contribution report

### 🤖 5. AI Solution *(Future Feature)*
- Implement Gemini API integration logic (Planned)
- Implement bean-based personalized usage recommendation logic (Planned)
- Implement activity-based environmental contribution analysis logic (Planned)

### 📊 6. Environmental Contribution Report
- Implement API for calculating and viewing environmental contribution reports by user/cafe role
- Provide data for carbon reduction amount, sorted waste index visualization
- Implement user/cafe comparison reports

### 🛍️ 7. Upcycling Market / Community *(Future Feature)*
- Implement API for product/post data management (Planned)
- Implement 1:1 chat function (Planned)

---
### 📦 Local Setup (Getting Started)

Steps to get the project running in your local development environment.

**Prerequisites:**

* Java 17+
* Docker Desktop or Docker Engine
* Gradle

**Steps:**

1.  **Clone the repository:**
    ```bash
    git clone <Your GitHub BE Repository URL>
    cd SDGP_team2_BE
    ```

2.  **Configuration:**
    * Configure the `src/main/resources/application.yaml` file and a `.env` file for your local development environment.

3.  **Run Required Middleware:**
    Use the `docker-compose.yaml` file in the project root to run the necessary containers for the application's middleware. (This `docker-compose.yaml` file is also used for CI/CD purposes and may require some configuration changes for local execution.)
    ```bash
    docker-compose up -d
    ```
    *(After running Docker Compose, ensure the MySQL and Redis containers are running correctly.)*

4.  **Build and Run the Application:**
    * Build the project using the Gradle Wrapper.
        ```bash
        ./gradlew clean build
        ```
    * Run from your IDE or execute the resulting JAR file. The built JAR file is created in the `build/libs` directory.
        ```bash
        java -jar build/libs/<project-name-version>.jar
        ```
    *(Check the `build/libs` directory for the actual built JAR filename.)*

---

### 📄 API Documentation (Swagger UI)

After running the application, you can check the API documentation at the following paths.

* **Swagger UI:** `http://localhost:8080/swagger-ui.html`
* **OpenAPI 3 JSON:** `http://localhost:8080/v3/api-docs`

The documentation is automatically generated based on annotations like `@Operation`, `@Tag`, etc., defined in the controller code via the SpringDoc library.

---

### 🚀 Deployment Environment

This backend application is deployed and served within the **GCP (Google Cloud Platform)** environment.

---

## 🚀 Development Roadmap

- ✅ Social Login / Sign-up
- ✅ Map-based Collection Point Search API
- ✅ Cafe Management and Collection Request related APIs
- ✅ User My Page related APIs
- ✅ Environmental Contribution Report API
- ✅ Specific Cafe Grounds List Lookup API
- 🔄 AI Solution Integration and Logic Implementation *(Planned)*
- 🔄 Upcycling Market / Community related APIs *(Lower priority)*

---

## 📬 Inquiry & Contribution

> 🙌 Contributions, Feature Requests, and Bug Reports are all welcome!

---

<p align="center"><i>A daily cup of coffee becomes an action to protect the Earth 🌍</i></p>
