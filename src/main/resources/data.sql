INSERT INTO project (name) VALUES ('My First Project');
INSERT INTO tag (name) VALUES ('Spring Boot'), ('JPA');

INSERT INTO diary (date, devfeel, diff, error, explaination, pid)
VALUES ('2025-05-30', '히히 똥!', '집인데 집에가고싶다', '난 또 언제자냐', '자고 일어나서 과제하고 발표준비 해야징', 1);

INSERT INTO diary_tag (did, tid) VALUES (1, 1), (1, 2);
