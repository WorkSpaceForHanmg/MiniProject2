# Spring Boot 백엔드 API 명세서

## 🔧 기본 정보
- **Base URL**: `http://localhost:8085`
- **인증 방식**: JWT Bearer Token
- **Content-Type**: `application/json`

---

## 🔐 인증 API

### 1. 로그인
```http
POST /api/auth/login
```

**Request Body:**
```json
{
  "username": "testuser",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "tokenType": "Bearer",
  "user": {
    "uid": 1,
    "username": "testuser",
    "email": "test@example.com",
    "name": "테스트 사용자",
    "role": "USER"
  }
}
```

---

## 👤 사용자 관리 API

### 1. 현재 사용자 정보 조회
```http
GET /api/users/me
Authorization: Bearer {token}
```

**Response:**
```json
{
  "uid": 1,
  "username": "testuser",
  "email": "test@example.com",
  "name": "테스트 사용자",
  "role": "USER"
}
```

### 2. 사용자 등록 (회원가입)
```http
POST /api/users/register
```

**Request Body:**
```json
{
  "username": "newuser",
  "email": "newuser@example.com",
  "password": "password123",
  "name": "새로운 사용자"
}
```

### 3. 사용자 정보 수정
```http
PUT /api/users/me
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "username": "updateduser",
  "email": "updated@example.com",
  "password": "newpassword123",
  "name": "수정된 사용자"
}
```

### 4. 특정 사용자 조회 (관리자용)
```http
GET /api/users/{uid}
Authorization: Bearer {token}
```

### 5. 사용자 삭제
```http
DELETE /api/users/{uid}
Authorization: Bearer {token}
```

---

## 🏷️ 태그 관리 API

### 1. 태그 목록 조회
```http
GET /api/tags
Authorization: Bearer {token}
```

**Response:**
```json
[
  {
    "tid": 1,
    "name": "개발"
  },
  {
    "tid": 2,
    "name": "학습"
  }
]
```

### 2. 태그 생성
```http
POST /api/tags
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "name": "개발"
}
```

**Response:**
```json
{
  "tid": 1,
  "name": "개발"
}
```

### 3. 태그 수정
```http
PUT /api/tags/{tid}
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "name": "수정된 태그명"
}
```

### 4. 태그 삭제
```http
DELETE /api/tags/{tid}
Authorization: Bearer {token}
```

---

## 📁 프로젝트 관리 API

### 1. 프로젝트 목록 조회
```http
GET /api/projects
Authorization: Bearer {token}
```

**Response:**
```json
[
  {
    "pid": 1,
    "name": "Spring Boot 백엔드 개발"
  }
]
```

### 2. 프로젝트 생성
```http
POST /api/projects
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "name": "Spring Boot 백엔드 개발",
  "description": "JWT 인증과 RESTful API를 포함한 완전한 백엔드 시스템 구축"
}
```

**Response:**
```json
{
  "pid": 1,
  "name": "Spring Boot 백엔드 개발"
}
```

### 3. 프로젝트 수정
```http
PUT /api/projects/{pid}
Authorization: Bearer {token}
```

### 4. 프로젝트 삭제
```http
DELETE /api/projects/{pid}
Authorization: Bearer {token}
```

---

## 📔 일기 관리 API

### 1. 일기 목록 조회
```http
GET /api/diaries
Authorization: Bearer {token}
```

**Response:**
```json
[
  {
    "did": 1,
    "date": "2024-12-08",
    "devfeel": "매우좋음",
    "diff": null,
    "error": null,
    "explaination": "태그 연결 기능이 잘 작동하면 좋겠습니다.",
    "projectId": 1,
    "projectName": "Spring Boot 백엔드 개발",
    "tags": ["개발", "학습"]
  }
]
```

### 2. 일기 생성
```http
POST /api/diaries
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "title": "태그 연결 테스트",
  "content": "태그가 제대로 연결되는지 확인하는 일기입니다.",
  "date": "2024-12-08",
  "devfeel": "매우좋음",
  "explaination": "태그 연결 기능이 잘 작동하면 좋겠습니다.",
  "projectId": 1,
  "tagIds": [1, 2]
}
```

**Response:**
```json
{
  "did": 1,
  "date": "2024-12-08",
  "devfeel": "매우좋음",
  "diff": null,
  "error": null,
  "explaination": "태그 연결 기능이 잘 작동하면 좋겠습니다.",
  "projectId": 1,
  "projectName": "Spring Boot 백엔드 개발",
  "tags": ["개발", "학습"]
}
```

### 3. 일기 수정
```http
PUT /api/diaries/{did}
Authorization: Bearer {token}
```

### 4. 일기 삭제
```http
DELETE /api/diaries/{did}
Authorization: Bearer {token}
```

### 5. 특정 일기 조회
```http
GET /api/diaries/{did}
Authorization: Bearer {token}
```

---

## 📊 데이터 모델

### User (사용자)
```json
{
  "uid": "Long - 사용자 ID",
  "username": "String - 사용자명 (고유)",
  "email": "String - 이메일 (고유)",
  "name": "String - 실명",
  "role": "String - 권한 (USER/ADMIN)"
}
```

### Tag (태그)
```json
{
  "tid": "Long - 태그 ID",
  "name": "String - 태그명"
}
```

### Project (프로젝트)
```json
{
  "pid": "Long - 프로젝트 ID",
  "name": "String - 프로젝트명"
}
```

### Diary (일기)
```json
{
  "did": "Long - 일기 ID",
  "title": "String - 제목",
  "content": "String - 내용",
  "date": "String - 날짜 (YYYY-MM-DD)",
  "devfeel": "String - 개발 기분",
  "diff": "String - 어려웠던 점",
  "error": "String - 에러 내용",
  "explaination": "String - 설명",
  "projectId": "Long - 연결된 프로젝트 ID",
  "projectName": "String - 프로젝트명",
  "tags": "Array<String> - 연결된 태그명 목록"
}
```

---

## 🔒 인증 및 권한

### 인증 헤더
모든 보호된 API는 다음 헤더가 필요합니다:
```http
Authorization: Bearer {JWT_TOKEN}
```

### 데이터 접근 권한
- 각 사용자는 **자신의 데이터만** 접근 가능
- 태그, 프로젝트, 일기는 사용자별로 격리됨
- 관리자 권한이 필요한 일부 기능 존재

---

## 🚨 에러 응답

### 인증 오류 (401)
```json
{
  "statusCode": 401,
  "message": "인증이 필요합니다.",
  "timestamp": "2024-12-08 15:30:00"
}
```

### 권한 오류 (403)
```json
{
  "statusCode": 403,
  "message": "접근 권한이 없습니다.",
  "timestamp": "2024-12-08 15:30:00"
}
```

### 데이터 없음 (404)
```json
{
  "statusCode": 404,
  "message": "요청한 리소스를 찾을 수 없습니다.",
  "timestamp": "2024-12-08 15:30:00"
}
```

### 서버 오류 (500)
```json
{
  "statusCode": 500,
  "message": "서버 내부 오류가 발생했습니다.",
  "timestamp": "2024-12-08 15:30:00"
}
```

---

## 📝 테스트 계정

### 기본 테스트 계정
- **사용자명**: `testuser`
- **비밀번호**: `password123`
- **이메일**: `test@example.com`

### 관리자 계정
- **사용자명**: `admin`
- **비밀번호**: `admin123`
- **이메일**: `admin@example.com`

---

## 🎯 API 사용 예시

### 1. 로그인 → 일기 작성 플로우
```bash
# 1. 로그인
curl -X POST http://localhost:8085/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'

# 2. 태그 생성
curl -X POST http://localhost:8085/api/tags \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{"name":"개발"}'

# 3. 프로젝트 생성
curl -X POST http://localhost:8085/api/projects \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{"name":"Spring Boot 프로젝트","description":"설명"}'

# 4. 일기 작성
curl -X POST http://localhost:8085/api/diaries \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{"title":"오늘의 개발일기","content":"내용","date":"2024-12-08","devfeel":"좋음","projectId":1,"tagIds":[1]}'
```

---

