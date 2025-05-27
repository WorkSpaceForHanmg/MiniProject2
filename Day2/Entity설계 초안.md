## Entity 설계

### Diary

| 필드명    | 타입        | 설명              |
|-----------|-------------|-------------------|
| `did`     | `Long`      | Diary ID (PK)     |
| `diff`    | `String`    | 어려웠던 점       |
| `Error`   | `String`    | 오류 내용         |
| `Devfeel` | `String`    | 느낀점            |
| `Explain` | `String`    | 코드 설명         |
| `date`    | `LocalDate` | 작성 날짜         |

---

### Tag

| 필드명  | 타입     | 설명          |
|--------|----------|---------------|
| `tid`  | `Long`   | Tag ID (PK)   |
| `name` | `String` | 태그 이름     |

---

### Error

| 필드명     | 타입     | 설명                  |
|------------|----------|-----------------------|
| `eid`      | `Long`   | Error ID (PK)         |
| `err_msg`  | `String` | 에러 메시지           |
| `err_sol`  | `String` | 에러 해결 방법        |

---
