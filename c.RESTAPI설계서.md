# REST API 설계서

## 문서 정보

* **프로젝트명**: 개발 일기 프로젝트
* **작성자**: 3조 / 김인태
* **작성일**: 2025-06-08
* **버전**: v1.0
* **검토자**: 백엔드 개발자명
* **API 버전**: v1
* **Base URL**: [https://localhost:8080/api](https://localhost:8080/api)

---

## 1. API 설계 개요

### 1.1 설계 목적

> RESTful 원칙에 따라 클라이언트-서버 간 통신 규격을 정의하여 일관되고 확장 가능한 API를 제공

### 1.2 설계 원칙

* RESTful 아키텍처: HTTP 메서드와 상태 코드의 올바른 사용
* 일관성: 모든 API 엔드포인트에서 동일한 규칙 적용
* 버전 관리: URL 경로를 통한 버전 구분
* 보안: JWT 기반 인증
* 성능: 페이지네이션, 캐싱
* 문서화: 명확한 요청/응답 스펙 제공

### 1.3 기술 스택

* 프레임워크: Spring Boot 3.4.6
* 인증: JWT (JSON Web Token)
* 직렬화: JSON
* API 문서: OpenAPI 3.0 (Swagger)

---

## 2. API 공통 규칙

### 2.1 URL 설계 규칙

| 규칙     | 설명                | 좋은 예                | 나쁜 예               |
| ------ | ----------------- | ------------------- | ------------------ |
| 명사 사용  | 동사가 아닌 명사로 리소스 표현 | `/api/diaries`      | `/api/getDiaries`  |
| 복수형 사용 | 컬렉션은 복수형으로 표현     | `/api/projects`     | `/api/projectList` |
| 계층 구조  | 리소스 간 관계를 URL로 표현 | `/api/diaries/1`    | `/api/diaryById`   |
| 소문자 사용 | URL은 소문자와 하이픈 사용  | `/api/tags`         | `/api/Tags`        |
| 동작 표현  | HTTP 메서드로 동작 구분   | `POST /api/diaries` | `/api/createDiary` |

### 2.2 HTTP 메서드 사용 규칙

| 메서드    | 용도        | 멱등성 | 안전성 | 예시                      |
| ------ | --------- | --- | --- | ----------------------- |
| GET    | 리소스 조회    | ✅   | ✅   | `GET /api/diaries`      |
| POST   | 리소스 생성    | ❌   | ❌   | `POST /api/diaries`     |
| PUT    | 리소스 전체 수정 | ✅   | ❌   | `PUT /api/diaries/1`    |
| DELETE | 리소스 삭제    | ✅   | ❌   | `DELETE /api/diaries/1` |

### 2.3 HTTP 상태 코드 가이드

| 코드  | 상태                    | 설명          | 사용 예시         |
| --- | --------------------- | ----------- | ------------- |
| 200 | OK                    | 성공 (데이터 포함) | 일기 조회 성공      |
| 201 | Created               | 리소스 생성 성공   | 일기 작성 성공      |
| 204 | No Content            | 성공 (응답 없음)  | 일기 삭제 성공      |
| 400 | Bad Request           | 잘못된 요청      | 유효성 검증 실패     |
| 401 | Unauthorized          | 인증 필요       | 토큰 없음/만료      |
| 404 | Not Found             | 리소스 없음      | 존재하지 않는 일기 ID |
| 500 | Internal Server Error | 서버 오류       | 예기치 못한 오류     |

### 2.4 공통 요청 헤더

```
Content-Type: application/json
Accept: application/json
Authorization: Bearer {JWT_TOKEN}
X-Request-ID: {UUID}
Accept-Language: ko-KR
User-Agent: DiaryApp/1.0.0
```

### 2.5 공통 응답 형식

#### 성공 응답

```json
{
  "success": true,
  "data": {},
  "message": "요청이 성공적으로 처리되었습니다",
  "timestamp": "2025-06-08T10:00:00Z",
  "requestId": "uuid-value"
}
```

#### 에러 응답

```json
{
  "statusCode": 400,
  "message": "잘못된 요청입니다",
  "timestamp": "2025-06-08T10:00:00Z",
  "requestId": "uuid-value"
}
```

---

## 3. API 상세 명세

### 3.1 일기 API

#### 📘 GET /api/diaries

* 설명: 모든 일기 목록 조회
* 인증: 필요 (JWT)
* 응답 코드: 200 OK
* 요청 예시:
  ```sql
  GET /api/diaries
  Authorization: Bearer <token>
  ```
* 응답 예시:
  ```json
  [
    {
      "id": 1,
      "title": "오늘의 개발",
      "content": "React 상태 관리 공부함",
      "createdAt": "2025-06-01T12:34:56Z",
      "tags": ["React", "공부"],
      "projectId": 2
    },
    ...
  ]
  ```
  
#### 📘 GET /api/diaries/{id}

* 설명: 특정 ID의 일기 조회
* 파라미터: `id` (경로 변수)
* 응답 코드: 200 OK / 404 Not Found
* 요청 예시:
  ```sql
  GET /api/diaries/1
  Authorization: Bearer <token>
  ```
* 응답 예시:
  ```json
  {
    "id": 1,
    "title": "오늘의 개발",
    "content": "React 상태 관리 공부함",
    "createdAt": "2025-06-01T12:34:56Z",
    "tags": ["React", "공부"],
    "projectId": 2
  }
  ```
  

#### 📝 POST /api/diaries

* 설명: 새 일기 생성
* 바디: `title`, `content`, `tags`, `projectId`
* 응답 코드: 201 Created / 400 Bad Request
* 요청 바디:
  ```json
  {
    "title": "오늘의 개발",
    "content": "Spring Boot JWT 공부",
    "tags": ["Spring", "JWT"],
    "projectId": 2
  }
  ```
* 응답 예시:
  ```json
  {
    "id": 5,
    "title": "오늘의 개발",
    "content": "Spring Boot JWT 공부",
    "createdAt": "2025-06-08T09:30:00Z",
    "tags": ["Spring", "JWT"],
    "projectId": 2
  }
  ```
  

#### ✏️ PUT /api/diaries/{id}

* 설명: 기존 일기 수정
* 파라미터: `id` (경로 변수)
* 바디: 수정할 일기 내용
* 응답 코드: 200 OK / 404 Not Found
* 요청 바디:
  ```json
  {
    "title": "수정된 일기 제목",
    "content": "수정된 일기 내용",
    "tags": ["수정", "예시"],
    "projectId": 3
  }
  ```
* 응답 예시:
  ```json
  {
    "id": 5,
    "title": "수정된 일기 제목",
    "content": "수정된 일기 내용",
    "createdAt": "2025-06-08T09:30:00Z",
    "tags": ["수정", "예시"],
    "projectId": 3
  }
  ```


#### ❌ DELETE /api/diaries/{id}

* 설명: 일기 삭제
* 파라미터: `id`
* 응답 코드: 204 No Content / 404 Not Found
* 응답 예시:
  ```json
  {
    "success": true
  }
  ```

### 3.2 프로젝트 API

#### 📘 GET /api/projects

* 설명: 프로젝트 목록 조회
* 응답 코드: 200 OK
* 응답 예시:
  ```json
  [
    {
      "id": 1,
      "name": "개발 일지 앱"
    },
    {
      "id": 2,
      "name": "머신러닝 프로젝트"
    }
  ]
  ```

### 3.3 태그 API

#### 📘 GET /api/tags

* 설명: 태그 목록 조회
* 응답 코드: 200 OK
* 응답 예시:
  ```json
    ["React", "Spring", "공부", "버그", "회고"]
  ```  

---

## 4. 에러 코드 및 처리

### 4.1 표준 에러 코드 정의

| 코드                            | HTTP 상태 | 설명         | 해결 방법           |
| ----------------------------- | ------- | ---------- | --------------- |
| **VALIDATION\_ERROR**         | 400     | 입력값 검증 실패  | 요청 데이터 확인 후 재시도 |
| **INVALID\_CREDENTIALS**      | 401     | 인증 정보 오류   | 로그인 정보 확인       |
| **TOKEN\_EXPIRED**            | 401     | 토큰 만료      | 토큰 갱신 또는 재로그인   |
| **ACCESS\_DENIED**            | 403     | 권한 없음      | 권한 확인 또는 관리자 문의 |
| **RESOURCE\_NOT\_FOUND**      | 404     | 리소스 없음     | 요청 URL 및 ID 확인  |
| **DUPLICATE\_RESOURCE**       | 409     | 중복 생성 시도   | 기존 리소스 확인       |
| **BUSINESS\_RULE\_VIOLATION** | 422     | 비즈니스 규칙 위반 | 규칙 확인 후 조건 충족   |
| **RATE\_LIMIT\_EXCEEDED**     | 429     | 요청 한도 초과   | 잠시 후 재시도        |
| **INTERNAL\_SERVER\_ERROR**   | 500     | 서버 내부 오류   | 관리자 문의          |

### 4.2 비즈니스 로직 에러 코드

| 코드                          | 설명               | 해결 방법          |
| --------------------------- | ---------------- | -------------- |
| **DIARY\_NOT\_FOUND**       | 일기 ID가 존재하지 않음   | ID 확인 후 재요청    |
| **PROJECT\_NOT\_FOUND**     | 프로젝트 ID가 존재하지 않음 | 유효한 프로젝트 ID 확인 |
| **TAG\_NOT\_FOUND**         | 태그 ID가 존재하지 않음   | 태그 ID 확인       |
| **DUPLICATE\_DIARY**        | 동일 제목의 일기 존재     | 다른 제목으로 재작성    |
| **INVALID\_DIARY\_CONTENT** | 일기 내용 오류         | 내용 검토 후 수정     |
| **UNAUTHORIZED\_ACCESS**    | 자신의 일기가 아님       | 접근 권한 확인       |


---

## 5. API 문서화

### 5.1 OpenAPI 3.0 스펙 예시
```yaml
openapi: 3.0.3
info:
  title: 개발 일기 API
  description: 개발 일기 관리 및 조회를 위한 REST API
  version: 1.0.0
  contact:
    name: API 지원팀
    email: api-support@devlog.com
  license:
    name: MIT
    url: https://opensource.org/licenses/MIT

servers:
  - url: https://localhost:8080/api
    description: 로컬 개발 서버

components:
  securitySchemes:
    BearerAuth:
      type: http
      scheme: bearer
      bearerFormat: JWT

  schemas:
    ErrorResponse:
      type: object
      properties:
        statusCode:
          type: string
          example: "400"
        message:
          type: string
          example: "입력값이 올바르지 않습니다"
        timestamp:
          type: string
          format: date-time

    Diary:
      type: object
      properties:
        id:
          type: integer
          example: 1
        title:
          type: string
          example: "React 상태 관리 학습"
        content:
          type: string
          example: "오늘은 Zustand를 공부했고, 전역 상태 관리에 대해 배웠다."
        createdAt:
          type: string
          format: date-time
        tags:
          type: array
          items:
            type: string
          example: ["React", "Zustand"]

paths:
  /diaries:
    get:
      summary: 일기 목록 조회
      tags: [Diary]
      responses:
        '200':
          description: 성공
          content:
            application/json:
              schema:
                type: object
                properties:
                  success:
                    type: boolean
                    example: true
                  data:
                    type: array
                    items:
                      $ref: '#/components/schemas/Diary'
                  message:
                    type: string
                    example: "목록 조회가 완료되었습니다"

```

### 5.2 API 문서 생성 도구
```java
// Spring Boot + Swagger 설정
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("개발 일기 API")
                .version("v1.0.0")
                .description("개발 일기 관리 기능을 제공하는 REST API"))
            .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
            .components(new Components()
                .addSecuritySchemes("BearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
    }
}


// Controller 어노테이션 예시
@RestController
@RequestMapping("/api/diaries")
@Tag(name = "Diary", description = "일기 관리 API")
public class DiaryController {

    @GetMapping
    @Operation(summary = "일기 목록 조회", description = "작성된 모든 일기를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공",
            content = @Content(schema = @Schema(implementation = Diary.class))),
        @ApiResponse(responseCode = "401", description = "인증 실패",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ApiResponse<List<DiaryDTO>>> getAllDiaries() {
        // 구현 생략
    }
}
```

## 6. 체크리스트 및 품질 관리

### 6.1 API 설계 체크리스트
```
□ RESTful 원칙을 준수하는가?
□ 일기, 프로젝트, 태그 등 리소스의 URL이 명확한가? (/diaries, /projects 등)
□ HTTP 메서드(GET, POST, PUT, DELETE)를 적절히 사용하는가?
□ 응답에 대한 HTTP 상태 코드가 일관적으로 적용되는가?
□ 응답 포맷이 통일되어 있는가? (성공/실패 여부, 메시지, 데이터 구조 등)
□ 예외 상황에 대한 에러 응답 형식이 정의되어 있는가?
□ 목록 조회 시 페이지네이션이 적용되었는가?
□ API에 JWT 기반 인증/인가가 적용되었는가?
```

### 6.2 보안 체크리스트
```
□ 모든 요청에 대해 입력값 검증을 수행하는가? (예: title, content 길이 제한)
□ JWT 토큰을 안전하게 저장 및 전달하고 있는가? (LocalStorage 사용 시 XSS 방지 고려)
□ 민감한 API는 인증/인가를 반드시 확인하는가? (예: 일기 수정/삭제는 작성자만 가능)
□ 에러 메시지에 내부 로직, 스택트레이스 등 민감 정보가 포함되지 않는가?
□ Swagger 등의 문서에 보안 설정이 적용되었는가? (BearerAuth, 테스트용 토큰 보호)
```

### 6.3 성능 체크리스트
```
□ 일기, 프로젝트, 태그 등의 연관 관계에 대해 N+1 쿼리 문제가 발생하지 않는가?
□ frequently used 검색/조회 대상에 대해 인덱스가 설정되었는가?
□ 일기 목록 조회 시 필요한 정보만 선별적으로 제공하는가? (불필요한 전체 데이터 전송 방지)
□ 클라이언트에서 필요 없는 반복 호출을 방지하고 있는가?
```

## 7. 마무리

### 7.1 주요 포인트 요약
1. **RESTful 설계**: HTTP 메서드와 상태 코드의 올바른 사용
2. **일관성 유지**: 모든 API에서 동일한 규칙과 형식 적용
3. **보안 강화**: 인증, 인가, 입력 검증을 통한 보안 확보
4. **문서화**: 명확하고 완전한 API 문서 제공

### 7.2 추천 도구 및 라이브러리
- **문서화**: Swagger/OpenAPI, Postman
- **보안**: Spring Security, JWT

### 7.3 향후 고도화 방안
- **GraphQL 지원**: 클라이언트별 맞춤 데이터 제공
- **WebSocket**: 실시간 알림 및 채팅 기능
- **이벤트 기반 아키텍처**: 마이크로서비스 간 느슨한 결합
- **API 게이트웨이**: 중앙화된 API 관리
- **성능**: 캐싱, 압축을 통한 성능 향상 Redis, Caffeine Cache
- **테스트**: REST Assured, WireMock, TestContainers
- **모니터링**: 로깅, 메트릭을 통한 운영 상황 파악 Micrometer, Prometheus, Grafana

---
            
