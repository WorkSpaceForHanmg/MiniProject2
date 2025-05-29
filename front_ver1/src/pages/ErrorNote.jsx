import React, { useState } from 'react';
import styles from '../styles/ErrorNote.module.css';

const dummyData = [
  {
    id: 1,
    title: '오류1_발생 → 해결',
    description: '이런 저런 오류가 발생하였다.',
    project: '프로젝트 1',
    date: '2025년 4월 20일',
    tags: ['Spring', 'JAVA'],
  },
  {
    id: 2,
    title: '오류2_발생 → 해결',
    description: '이런 저런 오류가 발생하였다.',
    project: '프로젝트 1',
    date: '2025년 4월 23일',
    tags: ['Spring', 'JAVA'],
  },
];

export default function ErrorNote({onBack}) {
  const [selectedTag, setSelectedTag] = useState('Spring');

  const filtered = dummyData.filter((d) => d.tags.includes(selectedTag));

  return (
    <div className={styles.container}>
      <button className = {styles.backBtn} onClick={onBack}>
        ←뒤로가기
      </button>

      <div className={styles.filterSection}>
        <label htmlFor="tag-select">태그</label>
        <div className={styles.tagInputGroup}>
          <input
            id="tag-select"
            value={selectedTag}
            onChange={(e) => setSelectedTag(e.target.value)}
            list="tag-options"
          />
          <datalist id="tag-options">
            <option value="Spring" />
            <option value="JAVA" />
            <option value="React" />
          </datalist>
          <button className={styles.searchButton}>▼</button>
        </div>
      </div>

      <h2 className={styles.errorTitle}>ERRORS</h2>

      <div className={styles.errorList}>
        {filtered.map((item) => (
          <div key={item.id} className={styles.errorCard}>
            <a href="#" className={styles.errorLink}>{item.title}</a>
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
    </div>
  );
}
