import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDiary } from '../context/DiaryContext';
import styles from '../styles/ErrorNote.module.css';

export default function ErrorNote() {
  const navigate = useNavigate();
  const { diaries, tags } = useDiary();
  const [selectedTag, setSelectedTag] = useState('all');

  // 선택한 태그가 'all'이면 모든 diaries 보여주고, 아니면 필터링
  const filteredDiaries =
    selectedTag === 'all'
      ? diaries
      : diaries.filter((d) => d.tags && d.tags.includes(selectedTag));

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
          <option value="all">전체보기</option>
          {tags.map((tag) => (
            <option key={tag} value={tag}>
              {tag}
            </option>
          ))}
        </select>
      </div>

      <h2 className={styles.errorTitle}>ERRORS</h2>

      {filteredDiaries.length === 0 ? (
        <p className={styles.emptyText}>조건에 맞는 오류가 없습니다.</p>
      ) : (
        <div className={styles.errorList}>
          {filteredDiaries.map((item) => (
            <div key={item.id} className={styles.errorCard}>
              <button
                className={styles.errorLink}
                onClick={() => navigate(`/diary/${item.id}`)}
                // 가능하면 인라인 스타일 대신 css로 관리하는 게 좋음
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
                aria-label={`일기 ${item.title || item.id} 상세보기`}
              >
                {item.title || `일기 ${item.id}`}
              </button>
              <p className={styles.errorDesc}>
                {item.description || item.summary || '설명이 없습니다.'}
              </p>
              <div className={styles.errorMeta}>
                <span>{item.project || '프로젝트 정보 없음'}</span>
                <span>{item.date || '날짜 정보 없음'}</span>
              </div>
              <div className={styles.tagList}>
                {item.tags && item.tags.length > 0 ? (
                  item.tags.map((tag) => (
                    <span key={tag} className={styles.tag}>
                      {tag}
                    </span>
                  ))
                ) : (
                  <span className={styles.noTag}>태그 없음</span>
                )}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
