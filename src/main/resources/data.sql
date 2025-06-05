INSERT INTO project (name) VALUES ('My First Project');
INSERT INTO tag (name) VALUES ('Spring Boot'), ('JPA');

INSERT INTO diary (date, devfeel, diff, error, explaination, pid)
VALUES ('2025-05-30', '히히히', '정말 어렵당', '에러다 에러', '이러이렇게 잘 고쳤당', 1);

INSERT INTO diary_tag (did, tid) VALUES (1, 1), (1, 2);
