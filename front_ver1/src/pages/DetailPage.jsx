import React, { useState } from 'react';
import styles from '../styles/DetailPage.module.css';

const dummyDiaries = [
  {
    id: 1,
    date: '2025-05-26',
    project: '개발 일기 웹앱',
    tags: ['React', 'Spring'],
    summary: '오늘은 일기 폼을 완성함...',
    content: '폼 구성하고 상태 관리 마무리함.',
  },
  {
    id: 2,
    date: '2025-05-25',
    project: '개발 일기 웹앱',
    tags: ['React'],
    summary: '기초 구조 설계',
    content: '프로젝트 구조를 설정하고 폴더 나눔.',
  },
];

export default function DiaryDetail({ diary, onBack }) {
  const [selectedDate, setSelectedDate] = useState(diary.date);

  const sameProjectDiaries = dummyDiaries.filter(
    (d) => d.project === diary.project
  );

  const selectedEntry = sameProjectDiaries.find((d) => d.date === selectedDate);

  return (
    <div className={styles.container}>
      <button className={styles.backBtn} onClick={onBack}>
        ← 뒤로가기
      </button>

      <div className={styles.layout}>
        <aside className={styles.sidebar}>
          <h3>{diary.project}</h3>
          <ul>
            {sameProjectDiaries.map((d) => (
              <li
                key={d.id}
                className={selectedDate === d.date ? styles.activeDate : ''}
                onClick={() => setSelectedDate(d.date)}
              >
                {d.date}
              </li>
            ))}
          </ul>
        </aside>

        <section className={styles.content}>
          <h2>{selectedEntry.date}</h2>
          <p><strong>태그:</strong> {selectedEntry.tags.join(', ')}</p>
          <p><strong>요약:</strong> {selectedEntry.summary}</p>
          <p><strong>내용:</strong> {selectedEntry.content}</p>
        </section>
      </div>
    </div>
  );
}
