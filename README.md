<p align="right">
  🇰🇷 <a href="./README.ko.md">View in Korean</a>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17%2B-orange?style=flat-square&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/SpringBoot-3.x-green?style=flat-square&logo=springboot"/>
  <img src="https://img.shields.io/badge/MySQL-Relational-blue?style=flat-square&logo=mysql"/>
  <img src="https://img.shields.io/badge/Redis-Cache-red?style=flat-square&logo=redis"/>
  <img src="https://img.shields.io/badge/Docker-Container-2496ED?style=flat-square&logo=docker"/>
  <img src="https://img.shields.io/badge/GCP-Cloud-4285F4?style=flat-square&logo=googlecloud"/>
  <img src="https://img.shields.io/badge/Gemini-API-4285F4?style=flat-square&logo=google&logoColor=white"/>
</p>

<h1 align="center">☕ ECO-BEAN</h1>
<h3 align="center">A cup of coffee for the Earth,<br> An eco-friendly, map-based platform for coffee-ground recycling</h3>

<br/>

<p align="center">
  <img src="https://user-images.githubusercontent.com/your-image-path/demo.gif" width="70%" alt="CoffeeReturn Demo"/>
</p>

---

## 📌 Overview

> **ECO-BEAN** is an eco-friendly collection-network platform that connects **cafés and users** to reduce careless disposal of coffee grounds.  
> This repository hosts the **back-end** source code.

- 🗺️ **Search collection points on a map**  
- 🧑‍🍳 **Register and manage coffee grounds (for cafés)**  
- 🤖 **AI-powered reuse suggestions**  
- 📊 **Auto-generated environmental impact reports**

---

## 🎯 Goals

- ♻️ **Promote recycling of coffee grounds**  
- 👥 **Automate matching between users and cafés**  
- 🧠 **Provide AI-powered solutions via Gemini API**  
- 🌍 **Foster eco-conscious behaviour**

---

## 🧩 Key Features

### 🗺️ 1. Home (Map-based Collection Search)
* Map view centred on the user’s current location  
* Clickable markers to open detail pop-ups  
* Filters for Café / Public / Enterprise collection points  

### 🔐 2. Login / Signup
* Social login via **Kakao** or **Google**  
* Choose account type: **General User** or **Café Owner**

### 🧑‍🍳 3. Café Dashboard
* Register bean information  
* Add grounds and accept / reject collection requests  
* Manage café profile and opening hours  

### 👤 4. User MyPage
* View collection request history  
* Track status (Pending / Accepted / Completed)  
* Access personal contribution reports  

### 🤖 5. AI Solution
* Personalised reuse suggestions (fertiliser, scrub, etc.)  
* Analyse environmental contribution using **Gemini API**

### 📊 6. Environmental Report
* Visualise carbon reduction and recycling score   
* Compare impact between users and cafés  

### 🛍️ 7. Upcycling Market / Community *(Coming Soon)*
* Share or trade upcycled coffee-ground products  
* Community board & discussions  
* *(Later feature: 1 : 1 Chat)*  

---

## ⚙️ Tech Stack

### 💻 Back-End

| Technology | Role |
|------------|------|
| **Java 17+** | Core language |
| **Spring Boot 3.x** | API server / core logic |
| **Spring Security** | Authentication & Authorisation |
| **JWT** | Stateless auth token |
| **OAuth2 Client** | Social login integration |
| **Spring Data JPA** | ORM layer |
| **Hibernate** | JPA implementation |
| **QueryDSL** | Type-safe query construction |
| **MySQL** | Relational database |
| **Redis** | Cache & real-time data |
| **AWS S3 SDK** | Image storage |
| **Gemini API** | AI suggestions & reports |
| **SpringDoc (Swagger UI)** | API documentation |
| **Spring Validation API** | Input validation |
| **Lombok** | Boilerplate reduction |
| **SLF4J / Logback** | Logging |
| **GitHub Actions** | CI / CD |

### 🌐 Front-End (Reference)

| Technology |
|------------|
| **TypeScript** |
| **React** |
| **Tailwind CSS** |
| **Google Maps API** |
| **Zod** |
| **Firebase Hosting** |

---

## 📦 Getting Started (Local Setup)

### Prerequisites
* **Java 17+**  
* **Docker** (Desktop or Engine)  
* **Gradle** (6.8 or later)  

### Steps

1. **Clone the repository**
   ```bash
   git clone <YOUR_BACKEND_REPO_URL>
   cd SDGP_team2_BE
2. Configure your environment
   Update `src/main/resources/application.yaml` and `.env` to match your local setup.

3. Start required middleware
   The root-level `docker-compose.yaml` spins up all necessary containers (MySQL, Redis, …).

   ```bash
   docker-compose up -d
   ```
    *This `docker-compose.yaml` file is also used in CI/CD pipelines—feel free to tweak it for local development.*

4. Build & run the application

   ```bash
   ./gradlew clean build
   java -jar build/libs/<PROJECT_NAME-VERSION>.jar
   ```

   *The built JAR file name can be found under `build/libs`.*

---

## 📄 API Documentation (Swagger UI)

Once the application is running:

| Resource | URL |
|----------|-----|
| **Swagger UI** | `http://localhost:8080/swagger-ui.html` |
| **OpenAPI JSON** | `http://localhost:8080/v3/api-docs` |

API docs are auto-generated via SpringDoc annotations (`@Operation`, `@Tag`, …).

---

## ☁️ Deployment
The back-end service is deployed on **Google Cloud Platform (GCP)** using Docker containers managed via Cloud Run.

---

## 🚀 Roadmap

| Status | Item |
|:------:|------|
| ✅ | Social login / signup |
| ✅ | Map-based collection search API |
| ✅ | Café management & collection request APIs |
| ✅ | User MyPage APIs |
| ✅ | Environmental report APIs |
| ✅ | Get coffee-ground list by café |
| ✅ | AI solution integration |
| 🔄 | Upcycling market / community APIs *(later)* |

---

## 📬 Contact & Contribution
🙌 **Contributions, feature requests, and bug reports are welcome!**  
Feel free to open issues or submit pull requests.

<p align="center"><i>A daily cup of coffee can help protect our planet 🌍</i></p>
