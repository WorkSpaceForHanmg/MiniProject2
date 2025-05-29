import React, { useState, useMemo } from 'react';
import styles from '../styles/DetailPage.module.css';

// 임시 dummyDiaries는 App에서 props로 전달하거나 여기서 임포트 가능
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
  {
    id: 3,
    date: '2025-05-24',
    project: '프로젝트 A',
    tags: ['JavaScript'],
    summary: '코드 리팩토링 진행...',
    content: '함수형 컴포넌트로 전환 및 코드 간결화.',
  },
];

export default function DetailPage({ onBack }) {
  const [projectFilter, setProjectFilter] = useState('');
  const [tagFilter, setTagFilter] = useState('');
  const [selectedDiaryId, setSelectedDiaryId] = useState(null);

  // 필터 조건에 맞는 일기 필터링
  const filteredDiaries = useMemo(() => {
    return dummyDiaries.filter(diary => {
      const matchesProject =
        projectFilter === '' || diary.project.includes(projectFilter);
      const matchesTag =
        tagFilter === '' || diary.tags.some(tag => tag.includes(tagFilter));
      return matchesProject && matchesTag;
    });
  }, [projectFilter, tagFilter]);

  // 날짜 내림차순 정렬
  const sortedDiaries = useMemo(() => {
    return [...filteredDiaries].sort((a, b) => (a.date < b.date ? 1 : -1));
  }, [filteredDiaries]);

  // 선택된 일기 객체
  const selectedDiary = sortedDiaries.find(d => d.id === selectedDiaryId) || sortedDiaries[0] || null;

  // 초기 선택 세팅 (처음 렌더링 시 첫 번째 일기 선택)
  React.useEffect(() => {
    if (sortedDiaries.length > 0 && !selectedDiaryId) {
      setSelectedDiaryId(sortedDiaries[0].id);
    }
  }, [sortedDiaries, selectedDiaryId]);

  return (
    <div className={styles.detailContainer}>
      <header className={styles.detailHeader}>
        <button className={styles.backBtn} onClick={onBack}>
          ← 뒤로가기
        </button>
        <h2>일기 상세 보기</h2>
      </header>

      <div className={styles.detailContent}>
        {/* 왼쪽 패널: 필터 + 일기 날짜 목록 */}
        <div className={styles.leftPanel}>
          <div className={styles.filters}>
            <input
              type="text"
              placeholder="프로젝트 검색"
              value={projectFilter}
              onChange={e => setProjectFilter(e.target.value)}
            />
            <input
              type="text"
              placeholder="태그 검색"
              value={tagFilter}
              onChange={e => setTagFilter(e.target.value)}
            />
          </div>

          <ul className={styles.diaryList}>
            {sortedDiaries.length === 0 && <li>조건에 맞는 일기가 없습니다.</li>}
            {sortedDiaries.map(diary => (
              <li
                key={diary.id}
                className={diary.id === selectedDiaryId ? styles.selectedDiaryItem : ''}
                onClick={() => setSelectedDiaryId(diary.id)}
              >
                {diary.date}
              </li>
            ))}
          </ul>
        </div>

        {/* 오른쪽 패널: 선택된 일기 상세 */}
        <div className={styles.rightPanel}>
          {selectedDiary ? (
            <>
              <h3>{selectedDiary.project}</h3>
              <p><strong>날짜:</strong> {selectedDiary.date}</p>
              <p><strong>태그:</strong> {selectedDiary.tags.join(', ')}</p>
              <h4>요약</h4>
              <p>{selectedDiary.summary}</p>
              <h4>내용</h4>
              <pre className={styles.contentBox}>{selectedDiary.content}</pre>
            </>
          ) : (
            <p>일기를 선택해주세요.</p>
          )}
        </div>
      </div>
    </div>
  );
}
