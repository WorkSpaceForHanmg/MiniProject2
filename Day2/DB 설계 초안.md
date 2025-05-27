##  DB 기본 설계

---

###  Diary Table

| 필드명           | 자료형        | 제약조건                      | 설명                  |
|------------------|---------------|-------------------------------|-----------------------|
| `Id`             | `BIGINT`      | `PK`, `AUTO_INCREMENT`        | 일기 고유 ID (Primary Key) |
| `Date`           | `DATETIME`    | `NOT NULL`                    | 작성 날짜             |
| `diary_name`     | `VARCHAR(100)`| `NULL` 가능                   | 일기 제목             |
| `thought`        | `TEXT`        | `NULL` 가능                   | 느낀 점               |
| `Diff`           | `TEXT`        | `NULL` 가능                   | 어려웠던 점           |
| `Error_message`  | `TEXT`        | `NULL` 가능                   | 입력한 에러 메시지    |
| `solution`       | `TEXT`        | `NULL` 가능                   | 해결 방법             |

---

###  Error_record Table

| 필드명           | 자료형     | 제약조건                             | 설명                 |
|------------------|------------|--------------------------------------|----------------------|
| `Id`             | `BIGINT`   | `PK`, `AUTO_INCREMENT`               | 에러 코드 ID (PK)    |
| `Diary_id`       | `BIGINT`   | `FK → Diary(Id)`, `NOT NULL`         | 연결된 다이어리 ID   |
| `Error_message`  | `TEXT`     | `NOT NULL`                           | 입력한 에러 메시지   |
| `solution`       | `TEXT`     | `NULL` 가능                          | 해결 방법            |

---

###  TAG Table

| 필드명  | 자료형        | 제약조건               | 설명           |
|---------|---------------|------------------------|----------------|
| `Id`    | `BIGINT`      | `PK`, `AUTO_INCREMENT` | 태그 고유 ID   |
| `name`  | `VARCHAR(50)` | `UNIQUE`, `NOT NULL`   | 태그 이름      |

---

