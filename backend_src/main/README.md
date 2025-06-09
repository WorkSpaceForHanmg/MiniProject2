```plaintext
개발일기 백엔드 API 서버 🖥️
개발일기 웹앱의 백엔드 서버입니다.
Spring Boot와 JPA 기반으로 일기, 프로젝트, 태그 데이터를 관리하는 RESTful API를 제공합니다.

🚀 주요 기능
일기 관리: 개발 일기 생성, 조회, 수정, 삭제

프로젝트 관리: 프로젝트 정보 CRUD

태그 관리: 태그 생성 및 조회

일기-태그 연동: 일기별 태그 매핑 관리

DTO와 Entity 매핑: 데이터 전송 객체와 DB 엔티티 간 변환

서비스 계층: 비즈니스 로직 처리

레포지토리 계층: 데이터베이스 접근 및 쿼리 수행

🛠️ 기술 스택
Backend: Java 17+

프레임워크: Spring Boot 3.x

데이터 접근: Spring Data JPA (Hibernate)

DB: MariaDB (또는 다른 관계형 DB)

빌드 도구: Maven 또는 Gradle

기타: Lombok, MapStruct (매퍼 사용 시)

📁 프로젝트 구조
bash
복사
src/main/java/com/example/diarybackend/
├── controller/           # REST API 컨트롤러 (DiaryController, ProjectController, TagController)
├── dto/                  # 데이터 전송 객체 (DiaryDTO, ProjectDTO, TagDTO 등)
├── entity/               # JPA 엔티티 (Diary, Project, Tag, DiaryTag)
├── mapper/               # DTO ↔ Entity 변환 매퍼 (DiaryMapper, ProjectMapper)
├── repository/           # 데이터 접근 계층 (DiaryRepository, ProjectRepository 등)
├── service/              # 비즈니스 로직 처리 (DiaryService, ProjectService 등)
└── service/impl/         # 서비스 구현체 (ProjectServiceImpl 등)
🏃‍♂️ 설치 및 실행
1. 저장소 클론
   bash
   복사
   git clone <repository-url>
   cd diary-backend
2. 데이터베이스 설정
   MariaDB 또는 원하는 DB를 설치 및 실행 후,
   application.yml 또는 application.properties 에서 접속 정보 설정

3. 의존성 설치 및 빌드
   Maven 또는 Gradle 빌드

bash
복사
./mvnw clean install
# 또는
./gradlew build
4. 서버 실행
   bash
   복사
   ./mvnw spring-boot:run
# 또는
./gradlew bootRun
📜 주요 API 엔드포인트
메서드	URI	설명
GET	/api/diaries	모든 일기 조회
POST	/api/diaries	새 일기 작성
GET	/api/diaries/{id}	특정 일기 조회
PUT	/api/diaries/{id}	일기 수정
DELETE	/api/diaries/{id}	일기 삭제
GET	/api/projects	프로젝트 목록 조회
POST	/api/projects	새 프로젝트 생성
GET	/api/tags	태그 목록 조회

📊 데이터 모델
Diary (일기)
id (Long): 일기 ID

date (LocalDate): 작성 날짜

devfeel (String): 개발 소감 (제목)

diff (String): 어려웠던 점

error (String): 에러 내용

explaination (String): 상세 설명

project (Project): 연관 프로젝트

tags (List<Tag>): 연관 태그 목록 (DiaryTag로 매핑)

Project (프로젝트)
pid (Long): 프로젝트 ID

name (String): 프로젝트명

Tag (태그)
tid (Long): 태그 ID

name (String): 태그명

DiaryTag (일기-태그 연결)
id (Long): 매핑 ID

diary (Diary): 일기

tag (Tag): 태그

🛠️ 주요 컴포넌트
DiaryController: 일기 관련 REST API 처리

ProjectController: 프로젝트 API 처리

TagController: 태그 API 처리

DiaryService & DiaryServiceImpl: 일기 비즈니스 로직

ProjectService & ProjectServiceImpl: 프로젝트 비즈니스 로직

Mapper 클래스: DTO와 Entity 변환 처리

Repository 인터페이스: JPA 기반 데이터 접근

🔧 사용 예시 (curl)
모든 일기 조회

bash
복사
curl -X GET http://localhost:8080/api/diaries
새 일기 작성

bash
복사
curl -X POST http://localhost:8080/api/diaries \
-H "Content-Type: application/json" \
-d '{"date":"2025-06-09","devfeel":"개발중","diff":"어려웠음","err
