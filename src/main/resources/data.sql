INSERT INTO project (name) VALUES ('My First Project');
INSERT INTO tag (name) VALUES ('Spring Boot'), ('JPA');

INSERT INTO diary (date, devfeel, diff, error, explaination, pid)
VALUES ('2025-05-30', '오늘은 DTO 정복!', 'Mapper 구현이 어려웠다', 'NPE 발생', '프론트와 백 연결을 위해 구조를 개선함.', 1);

INSERT INTO diary_tag (did, tid) VALUES (1, 1), (1, 2);
