<p align="center">
  <img src="https://img.shields.io/badge/Java-17+-orange?style=flat-square&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/SpringBoot-3.x-green?style=flat-square&logo=springboot"/>
  <img src="https://img.shields.io/badge/MySQL-Relational-blue?style=flat-square&logo=mysql"/>
  <img src="https://img.shields.io/badge/Redis-Cache-red?style=flat-square&logo=redis"/>
  <img src="https://img.shields.io/badge/Docker-Container-2496ED?style=flat-square&logo=docker"/>
  <img src="https://img.shields.io/badge/GCP-Cloud-4285F4?style=flat-square&logo=googlecloud"/>
  </p>

<h1 align="center">☕ CoffeeReturn (커피 리턴)</h1>
<h3 align="center">지구를 위한 커피 한 잔,<br>원두 찌꺼기의 순환을 위한 친환경 지도 기반 플랫폼</h3>

<br/>

<p align="center">
  <img src="https://user-images.githubusercontent.com/your-image-path/demo.gif" width="70%" alt="CoffeeReturn Demo">
</p>

---

## 📌 프로젝트 개요

> **CoffeeReturn**은 커피 찌꺼기의 무분별한 폐기를 줄이고,  
> **카페와 사용자를 연결하는 친환경 수거 네트워크 플랫폼**입니다.

> 이 레포지토리는 백엔드 코드를 관리합니다.

- 🗺️ **지도 기반 수거소 검색**
- 🧑‍🍳 **카페의 찌꺼기 등록 및 관리**
- 🤖 **AI가 추천하는 찌꺼기 활용법**
- 📊 **환경 기여도 리포트 자동 생성**

---

## 🎯 프로젝트 목표

- ♻️ **원두 찌꺼기의 재활용 활성화**
- 👥 **사용자 ↔ 카페 간 매칭 자동화**
- 🧠 **Gemini API 기반 AI 솔루션 제공**
- 🌍 **친환경 실천 문화 조성**
</p>

---

## 🧩 주요 기능

### 🗺️ 1. 홈화면 (지도 기반 수거소 검색)
- 현재 위치 기반 수거소 지도 표시
- 마커 클릭 → 상세 정보 팝업
- 카페/공공/기업 필터 기능 지원

### 🔐 2. 로그인 / 회원가입
- 카카오 / 구글 소셜 로그인
- 사용자 유형 선택 (일반 사용자 / 카페 운영자)

### 🧑‍🍳 3. 카페 관리 페이지
- 원두 정보 등록
- 찌꺼기 등록 및 수거 요청 수락/거절
- 내 카페 정보 관리

### 👤 4. 사용자 마이페이지
- 수거 신청 내역 조회
- 상태 확인 (대기 / 수락 / 완료)
- 기여 리포트 열람

### 🤖 5. AI 솔루션 *(추후 기능)*
- 원두 기반 맞춤 활용법 추천 (비료, 스크럽제 등)
- 활동 기반 환경 기여 분석 (Gemini API)

### 📊 6. 환경 기여도 리포트
- 탄소 절감량, 분리배출 지수
- 사용자/카페 비교 리포트

### 🛍️ 7. 업사이클링 마켓 / 커뮤니티 *(추후 기능)*
- 커피 찌꺼기 제품 나눔 및 거래
- 게시판 기반 커뮤니티
- (후순위 기능: 1:1 채팅)

---

## ⚙️ 사용 기술 스택

### 💻 Back-End

| 기술 스택                   |
| :-------------------------- |
| `Java 17+`                  |
| `Spring Boot 3.x`           |
| `Spring Security`           |
| `JWT`                       |
| `OAuth2 Client`             |
| `Spring Data JPA`           |
| `Hibernate`                 |
| `QueryDSL`                  |
| `MySQL`                     |
| `Redis`                     |
| `AWS S3 SDK`                |
| `Gemini API`                |
| `SpringDoc (Swagger UI)`    |
| `Spring Validation API`     |
| `Lombok`                    |
| `SLF4j / Logback`           |
| `GitHub Actions`            |

---

### 🌐 Front-End

| 기술 스택         |
| :---------------- |
| `TypeScript`      |
| `React`           |
| `Tailwind CSS`    |
| `Google maps API` |
| `Zod`             |
| `Firebase Hosting`|

---

### 📦 로컬 환경 설정 (Getting Started)

프로젝트를 로컬 개발 환경에서 실행하기 위한 방법입니다.

**사전 준비:**

* Java 17+
* Docker Desktop 또는 Docker Engine
* Gradle

**단계:**

1.  **저장소 복제:**
    ```bash
    git clone <본인 GitHub BE 레포지토리 URL>
    cd SDGP_team2_BE
    ```

2.  **환경 설정:**
    * `src/main/resources/application.yaml` 파일과 `.env` 파일을 로컬 개발 환경에 맞게 설정해야 합니다.

3.  **필요 미들웨어 실행:**
    프로젝트 루트의 `docker-compose.yaml` 파일을 사용하여 애플리케이션 실행에 필요한 컨테이너들을 실행합니다. (이 `docker-compose.yaml` 파일은 CI/CD 목적으로도 사용되고 있으며, 로컬 실행을 위해 일부 설정을 확인하거나 변경해야 할 수 있습니다.)
    ```bash
    docker-compose up -d
    ```
    *(Docker Compose 실행 후 MySQL 및 Redis 컨테이너가 정상적으로 동작하는지 확인하세요.)*

4.  **애플리케이션 빌드 및 실행:**
    * Gradle Wrapper를 사용하여 프로젝트를 빌드합니다.
        ```bash
        ./gradlew clean build
        ```
    * IDE에서 실행하거나, 빌드 결과물인 JAR 파일을 실행합니다. 빌드된 JAR 파일은 `build/libs` 디렉토리에 생성됩니다.
        ```bash
        java -jar build/libs/<프로젝트 이름-버전>.jar
        ```
    *(실제 빌드된 JAR 파일명은 `build/libs` 디렉토리에서 확인하세요.)*

---

### 📄 API 문서 (Swagger UI)

애플리케이션 실행 후 다음 경로에서 API 문서를 확인할 수 있습니다.

* **Swagger UI:** `http://localhost:8080/swagger-ui.html`
* **OpenAPI 3 JSON:** `http://localhost:8080/v3/api-docs`

SpringDoc 라이브러리를 통해 컨트롤러 코드에 정의된 `@Operation`, `@Tag` 등의 어노테이션을 기반으로 자동 생성됩니다.

---

### 🚀 배포 환경

본 백엔드 애플리케이션은 **GCP (Google Cloud Platform)** 환경에 배포되어 서비스되고 있습니다.

---

## 🚀 개발 로드맵

- ✅ 소셜 로그인 / 회원가입
- ✅ 지도 기반 수거소 검색 API
- ✅ 카페 관리 및 수거 요청 관련 API
- ✅ 사용자 마이페이지 관련 API
- ✅ 환경 기여도 리포트 API
- ✅ 특정 카페 찌꺼기 목록 조회 API
- 🔄 AI 솔루션 연동 및 로직 구현 *(진행 예정)*
- 🔄 업사이클링 마켓 / 커뮤니티 관련 API *(후순위)*

---

## 📬 문의 & 기여

> 🙌 기여(Contribution), 제안(Feature Request), 버그 리포트 모두 환영합니다!

---

<p align="center"><i>매일 마시는 커피 한 잔이 지구를 지키는 행동이 됩니다 🌍</i></p>
