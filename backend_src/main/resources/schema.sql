-- 1. 프로젝트 테이블
CREATE TABLE project (
  pid BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL
);

-- 2. 태그 테이블
CREATE TABLE tag (
  tid BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL
);

-- 3. 다이어리 테이블
CREATE TABLE diary (
  did BIGINT PRIMARY KEY AUTO_INCREMENT,
  date DATE NOT NULL,
  devfeel VARCHAR(500),
  diff VARCHAR(500),
  error VARCHAR(500),
  explaination TEXT,
  pid BIGINT,
  CONSTRAINT fk_diary_project FOREIGN KEY (pid) REFERENCES project(pid)
);

-- 4. 다이어리-태그 연결 테이블 (중간 테이블)
CREATE TABLE diary_tag (
  dtid BIGINT PRIMARY KEY AUTO_INCREMENT,
  did BIGINT NOT NULL,
  tid BIGINT NOT NULL,
  CONSTRAINT fk_diarytag_diary FOREIGN KEY (did) REFERENCES diary(did),
  CONSTRAINT fk_diarytag_tag FOREIGN KEY (tid) REFERENCES tag(tid)
);
