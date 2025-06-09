import React, { useState, useMemo } from 'react';
import styles from '../styles/MainPage.module.css';
import { useNavigate } from 'react-router-dom';

export default function MainPage({ diaries }) {
  const [selectedProject, setSelectedProject] = useState('');
  const [selectedTag, setSelectedTag] = useState('');
  const [selectedDate, setSelectedDate] = useState('');
  const navigate = useNavigate();

  // 🧠 diaries로부터 프로젝트와 태그 목록을 동적으로 생성
  const projects = useMemo(() => {
    const set = new Set(diaries.map((d) => d.project));
    return Array.from(set);
  }, [diaries]);

  const tags = useMemo(() => {
    const set = new Set();
    diaries.forEach((d) => d.tags.forEach((tag) => set.add(tag)));
    return Array.from(set);
  }, [diaries]);

  const filteredDiaries = diaries.filter((d) => {
    return (
      (selectedProject === '' || d.project === selectedProject) &&
      (selectedTag === '' || d.tags.includes(selectedTag)) &&
      (selectedDate === '' || d.date === selectedDate)
    );
  });

  return (
    <div className={styles.container}>
      <header className={styles.header}>Devry✏️</header>

      <section className={styles.filtersRow}>
        <div className={styles.filterGroup}>
          <label>프로젝트</label>
          <select value={selectedProject} onChange={(e) => setSelectedProject(e.target.value)}>
            <option value="">전체</option>
            {projects.map((p) => (
              <option key={p} value={p}>{p}</option>
            ))}
          </select>
        </div>

        <div className={styles.filterGroup}>
          <label>태그</label>
          <select value={selectedTag} onChange={(e) => setSelectedTag(e.target.value)}>
            <option value="">전체</option>
            {tags.map((t) => (
              <option key={t} value={t}>{t}</option>
            ))}
          </select>
        </div>

        <div className={styles.dateRow}>
          <label>날짜</label>
          <input
            type="date"
            value={selectedDate}
            onChange={(e) => setSelectedDate(e.target.value)}
          />
        </div>
      </section>

      <section className={styles.diaryList}>
        {filteredDiaries.length === 0 && <p>조건에 맞는 일기가 없습니다.</p>}
        {filteredDiaries.map((d) => (
          <div key={d.id} className={styles.diaryItem}>
            <div><strong>{d.project}</strong> | 태그: {d.tags.join(', ')}</div>
            <div>➝ {d.summary}</div>
            <button
              className={styles.detailBtn}
              onClick={() => navigate(`/diary/${d.id}`)}
            >
              자세히 보기
            </button>
          </div>
        ))}
      </section>

      <button
        className={styles.noteBtnFixed}
        onClick={() => navigate('/error-note')}
      >
        오답노트 보러가기
      </button>

      <div className={styles.newDiaryWrapper}>
        <button className={styles.newDiaryBtn} onClick={() => navigate('/new')}>
          + 새 일기 작성
        </button>
      </div>
    </div>
  );
}
