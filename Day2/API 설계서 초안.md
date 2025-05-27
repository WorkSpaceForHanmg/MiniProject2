##  프로젝트 API

| 메서드 | 경로 | 설명 |
|--------|------|------|
| `GET` | `/api/projects` | 전체 프로젝트 조회 |
| `GET` | `/api/projects/{pid}` | 단일 프로젝트 조회 |
| `POST` | `/api/projects` | 프로젝트 생성 |
| `PUT` | `/api/projects/{pid}` | 프로젝트 수정 |
| `DELETE` | `/api/projects/{pid}` | 프로젝트 삭제 |

---

## 일기 API

| 메서드 | 경로 | 설명 |
|--------|------|------|
| `GET` | `/api/diaries` | 전체 일기 조회 |
| `GET` | `/api/diaries/{did}` | 단일 일기 조회 |
| `GET` | `/api/diaries/project/{pid}` | 프로젝트별 일기 조회 |
| `GET` | `/api/diaries/range?start=YYYY-MM-DD&end=YYYY-MM-DD` | 날짜 범위로 일기 조회 |
| `POST` | `/api/diaries` | 일기 생성 |
| `PUT` | `/api/diaries/{did}` | 일기 수정 |
| `DELETE` | `/api/diaries/{did}` | 일기 삭제 |
