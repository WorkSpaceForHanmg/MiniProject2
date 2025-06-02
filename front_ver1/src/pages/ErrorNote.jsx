import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from '../styles/ErrorNote.module.css';

const dummyData = [
  {
    id: 1,
    title: '오류1_발생 → 해결',
    description: '스프링에서 의존성 주입이 안 되는 오류',
    project: '프로젝트 1',
    date: '2025년 4월 20일',
    tags: ['Spring', 'JAVA'],
  },
  {
    id: 2,
    title: '오류2_발생 → 해결',
    description: 'useState 초기값 설정 오류',
    project: '프로젝트 2',
    date: '2025년 4월 23일',
    tags: ['React', 'JavaScript'],
  },
  {
    id: 3,
    title: '오류3_발생 → 해결',
    description: 'SQL 구문 오류 (문법 에러)',
    project: '프로젝트 3',
    date: '2025년 5월 1일',
    tags: ['SQL'],
  },
];

const allTags = ['Spring', 'JAVA', 'React', 'JavaScript', 'SQL'];

export default function ErrorNote() {
  const navigate = useNavigate();
  const [selectedTag, setSelectedTag] = useState('');

  const filtered = selectedTag
    ? dummyData.filter((d) => d.tags.includes(selectedTag))
    : [];

  return (
    <div className={styles.container}>
      <button className={styles.backBtn} onClick={() => navigate(-1)}>
        ← 뒤로가기
      </button>

      <div className={styles.filterSection}>
        <label htmlFor="tag-select">태그</label>
        <select
          id="tag-select"
          value={selectedTag}
          onChange={(e) => setSelectedTag(e.target.value)}
          className={styles.selectBox}
        >
          <option value="">태그 선택</option>
          {allTags.map((tag) => (
            <option key={tag} value={tag}>{tag}</option>
          ))}
        </select>
      </div>

      <h2 className={styles.errorTitle}>ERRORS</h2>

      {filtered.length === 0 ? (
        <p className={styles.emptyText}>조건에 맞는 오류가 없습니다.</p>
      ) : (
        <div className={styles.errorList}>
          {filtered.map((item) => (
            <div key={item.id} className={styles.errorCard}>
              <button
                className={styles.errorLink}
                onClick={() => navigate(`/diary/${item.id}`)}
                style={{
                  background: 'none',
                  border: 'none',
                  padding: 0,
                  color: 'blue',
                  textDecoration: 'underline',
                  cursor: 'pointer',
                  fontSize: 'inherit',
                  fontFamily: 'inherit',
                }}
              >
                {item.title}
              </button>
              <p className={styles.errorDesc}>{item.description}</p>
              <div className={styles.errorMeta}>
                <span>{item.project}</span>
                <span>{item.date}</span>
              </div>
              <div className={styles.tagList}>
                {item.tags.map((tag) => (
                  <span key={tag} className={styles.tag}>{tag}</span>
                ))}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
