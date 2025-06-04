// ErrorNote.jsx
import React, { useState, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from '../styles/ErrorNote.module.css';

export default function ErrorNote({ diaries }) {
  const navigate = useNavigate();
  const [selectedTag, setSelectedTag] = useState('');

  // 일기에서 errorSummary가 존재하는 항목만 추출
  const errorDiaries = useMemo(() => {
    return diaries
      .map(d => {
        try {
          const content = JSON.parse(d.content || '{}');
          return {
            id: d.id,
            title: content.errorSummary ? `${content.errorSummary} → 해결` : '',
            description: content.errorSolution || '',
            project: d.project,
            date: d.date,
            tags: content.errorTags || [],
          };
        } catch {
          return null;
        }
      })
      .filter(Boolean)
      .filter(d => d.title); // errorSummary가 있어야 노출
  }, [diaries]);

  // 전체 태그 추출
  const allTags = useMemo(() => {
    const tagSet = new Set();
    errorDiaries.forEach(d => d.tags.forEach(tag => tagSet.add(tag)));
    return Array.from(tagSet);
  }, [errorDiaries]);

  // 필터링된 리스트
  const filtered = selectedTag
    ? errorDiaries.filter(d => d.tags.includes(selectedTag))
    : errorDiaries;

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
          <option value="">전체</option>
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
