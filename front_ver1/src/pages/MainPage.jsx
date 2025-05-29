import React, { useState } from 'react';
import styles from '../styles/MainPage.module.css';
import { Button } from '@mui/material';

const dummyProjects = ['개발 일기 웹앱', '프로젝트 A', '프로젝트 B'];
const dummyTags = ['React', 'Spring', 'JavaScript'];

export default function MainPage({ diaries, onViewDetail, onCreateNew, onGoToErrorNote }) {
  const [selectedProject, setSelectedProject] = useState('');
  const [selectedTag, setSelectedTag] = useState('');
  const [selectedDate, setSelectedDate] = useState('');

  const filteredDiaries = diaries.filter((d) => {
    return (
      (selectedProject === '' || d.project === selectedProject) &&
      (selectedTag === '' || d.tags.includes(selectedTag)) &&
      (selectedDate === '' || d.date === selectedDate)
    );
  });

  return (
      <div className={styles.container}>
        <header className={styles.header}>My Diary ✏️</header>

        <section className={styles.filtersRow}>
          <div className={styles.filterGroup}>
            <label>프로젝트</label>
            <select value={selectedProject} onChange={(e) => setSelectedProject(e.target.value)}>
              <option value="">전체</option>
              {dummyProjects.map((p) => (
                <option key={p} value={p}>{p}</option>
              ))}
            </select>
          </div>

          <div className={styles.filterGroup}>
            <label>태그</label>
            <select value={selectedTag} onChange={(e) => setSelectedTag(e.target.value)}>
              <option value="">전체</option>
              {dummyTags.map((t) => (
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


        {/* <section className={styles.dateRow}>
          <label>날짜</label>
          <input
            type="date"
            value={selectedDate}
            onChange={(e) => setSelectedDate(e.target.value)}
          />
        </section> */}

        <section className={styles.diaryList}>
          {filteredDiaries.length === 0 && <p>조건에 맞는 일기가 없습니다.</p>}
          {filteredDiaries.map((d) => (
            <div key={d.id} className={styles.diaryItem}>
              <div><strong>{d.project}</strong> | 태그: {d.tags.join(', ')}</div>
              <div>➝ {d.summary}</div>
              <button
                className={styles.detailBtn}
                onClick={() => onViewDetail(d.id)}
              >
                자세히 보기
              </button>
            </div>
          ))}
        </section>

        <button
          className={styles.noteBtnFixed}
          onClick={onGoToErrorNote}
        >
          오답노트 보러가기
        </button>
        
        <div className={styles.newDiaryWrapper}>
          <button className={styles.newDiaryBtn} onClick={onCreateNew}>
            + 새 일기 작성
          </button>
        </div>
      </div>

  );
}
