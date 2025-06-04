import React from 'react';
import styles from '../styles/MainPage.module.css';
import { useNavigate } from 'react-router-dom';
import PropTypes from 'prop-types';
import { useDiary } from '../context/DiaryContext';
import useDiaryFilters from '../hooks/useDiaryFilters';

const dummyProjects = ['개발 일기 웹앱', '프로젝트 A', '프로젝트 B'];
const dummyTags = ['React', 'Spring', 'JavaScript'];

export default function MainPage() {
  const { diaries } = useDiary();
  const navigate = useNavigate();

  const { filters, handleFilterChange, filteredDiaries } = useDiaryFilters(diaries || []);

  return (
    <div className={styles.container}>
      <header className={styles.header}>My Diary ✏️</header>

      <section className={styles.filtersRow}>
        <div className={styles.filterGroup}>
          <label>프로젝트</label>
          <select
            value={filters.project}
            onChange={(e) => handleFilterChange('project', e.target.value)}
          >
            <option value="">전체</option>
            {dummyProjects.map((p) => (
              <option key={p} value={p}>{p}</option>
            ))}
          </select>
        </div>

        <div className={styles.filterGroup}>
          <label>태그</label>
          <select
            value={filters.tag}
            onChange={(e) => handleFilterChange('tag', e.target.value)}
          >
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
            value={filters.date}
            onChange={(e) => handleFilterChange('date', e.target.value)}
          />
        </div>
      </section>

      <section className={styles.diaryList}>
        {filteredDiaries.length === 0 && <p>조건에 맞는 일기가 없습니다.</p>}
        {filteredDiaries.map((d) => (
          <div key={d.id} className={styles.diaryItem}>
            <div className={styles.projectTitle}>
              <strong>{d.project}</strong> <em>{d.title || '제목 없음'}</em>
            </div>

            <div className={styles.summary}>➝ {d.summary}</div>

            <div className={styles.tags}>
              {d.tags && d.tags.length > 0
                ? d.tags.map((tag) => (
                  <span key={tag} className={styles.tag}>
                    {tag}
                  </span>
                ))
                : <em className={styles.noTags}>태그 없음</em>
              }
            </div>

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
        <button
          className={styles.newDiaryBtn}
          onClick={() => navigate('/new')}
        >
          + 새 일기 작성
        </button>
      </div>
    </div>
  );
}

MainPage.propTypes = {
  diaries: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.number.isRequired,
      date: PropTypes.string.isRequired,
      project: PropTypes.string.isRequired,
      tags: PropTypes.arrayOf(PropTypes.string).isRequired,
      summary: PropTypes.string,
      content: PropTypes.string.isRequired,
    })
  ).isRequired,
};
