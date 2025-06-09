# 📝 Devry - 개발 일기 및 오답노트 웹앱

**Devry**는 개발자의 성장을 돕기 위한 일기 및 오답노트 관리 시스템입니다.
개발 중 겪은 문제, 코드 설명, 에러 해결 등을 정리하여 기록하고, 프로젝트와 태그별로 체계적으로 관리할 수 있습니다.

> Devry = Dev(elopment) + (Dia)ry

---

## 📦 프로젝트 구성

* **Frontend**: React 기반의 개발일기 웹앱
* **Backend**: Spring Boot 기반의 RESTful API 서버

---

## 🚀 주요 기능

### 공통

* 일기 작성 및 상세 보기
* 프로젝트 및 태그 기반 분류
* 에러 및 해결 방법 기록
* 일기 검색 및 필터링

### 프론트엔드 (React)

* 전체 일기 목록 표시
* 프로젝트/태그/날짜 필터
* 일기 작성, 수정, 삭제 UI
* 오답노트 전용 페이지
* 반응형 디자인 (모바일 지원)

### 백엔드 (Spring Boot)

* 일기, 프로젝트, 태그 CRUD API 제공
* DTO ↔ Entity 매핑
* 일기-태그 매핑 관리 (다대다)
* JPA 기반 데이터 처리 계층 분리

---

## 🛠️ 기술 스택

### 프론트엔드

* React 18, React Router v6
* React Hooks (`useState`, `useEffect`, `useMemo`)
* Axios
* CSS Modules
* CRA (Create React App)

### 백엔드

* Java 17+
* Spring Boot 3.x
* Spring Data JPA (Hibernate)
* MariaDB (또는 다른 관계형 DB)
* Lombok, MapStruct (선택)

---

## 📁 디렉토리 구조

### 프론트엔드

```
src/
├── api/                  # API 호출 모듈
├── components/           # 주요 페이지 컴포넌트
├── styles/               # CSS Modules
└── main.jsx              # 앱 진입점
```

### 백엔드

```
src/main/java/com/example/diarybackend/
├── controller/           # REST API 컨트롤러
├── dto/                  # 데이터 전송 객체
├── entity/               # JPA 엔티티
├── mapper/               # DTO ↔ Entity 변환기
├── repository/           # JPA Repository
├── service/              # 서비스 인터페이스
└── service/impl/         # 서비스 구현체
```

---

## 🔗 API 연동

* `GET /api/diaries` - 모든 일기 조회
* `POST /api/diaries` - 새 일기 생성
* `GET /api/diaries/{id}` - 특정 일기 조회
* `PUT /api/diaries/{id}` - 일기 수정
* `DELETE /api/diaries/{id}` - 일기 삭제
* `GET /api/projects` - 프로젝트 목록 조회
* `POST /api/projects` - 프로젝트 생성
* `GET /api/tags` - 태그 목록 조회

---

## 🧩 데이터 구조

### 프론트엔드 (예시)

```js
{
  id: number,
  date: "YYYY-MM-DD",
  project: "프로젝트명",
  tags: ["React", "JavaScript"],
  summary: "간단한 요약",
  content: JSON.stringify({
    codeExplanation: "코드 설명",
    devReview: "개발 소감",
    challenges: "어려웠던 점",
    errorSummary: "에러 요약",
    errorTags: ["태그1", "태그2"],
    errorSolution: "해결 방법"
  })
}
```

### 백엔드 (엔티티)

* **Diary**

  * id (Long), date (LocalDate), devfeel, diff, error, explanation, project, tags
* **Project**

  * pid (Long), name
* **Tag**

  * tid (Long), name
* **DiaryTag**

  * id (Long), diary, tag

---

## ⚙️ 설치 및 실행

### 프론트엔드

```bash
git clone <repository-url>
cd dev-diary-app
npm install
echo "REACT_APP_API_URL=http://localhost:8080/api" > .env
npm start
```

### 백엔드

```bash
git clone <repository-url>
cd diary-backend
./mvnw clean install  # 또는 ./gradlew build
./mvnw spring-boot:run  # 또는 ./gradlew bootRun
```

MariaDB 실행 후 `application.yml` 또는 `application.properties`에서 DB 연결 정보 설정 필요.

---

## 💻 페이지 구성

| 경로            | 기능                  |
| ------------- | ------------------- |
| `/`           | 메인 페이지 (일기 목록, 필터링) |
| `/diary/:id`  | 일기 상세 보기 및 수정       |
| `/new`        | 새 일기 작성             |
| `/error-note` | 에러/오답노트 전용 목록 보기    |

---

## 🔧 주요 컴포넌트

* `App.jsx`: 라우팅 및 API 통신
* `MainPage.jsx`: 필터링된 일기 목록
* `DetailPage.jsx`: 상세 보기 및 수정
* `NewDiaryForm.jsx`: 일기 작성
* `ErrorNote.jsx`: 에러 일기 목록

---

## 📤 배포

### 프론트엔드

* Vercel

  ```bash
  vercel --prod
  ```
* Netlify

  ```bash
  npm run build
  ```

### 백엔드

* 배포 시 DB 접근 정보 설정 필요
* Docker 및 클라우드 플랫폼 연동 가능 (선택 사항)

---

## ✅ 브라우저 지원

* Chrome, Firefox, Safari, Edge (최신 2버전 이상)

---

## 📝 라이센스

MIT License

---

## 🤝 기여하기

1. Fork
2. 브랜치 생성 및 기능 개발
3. 커밋 및 푸시
4. Pull Request 생성

---
