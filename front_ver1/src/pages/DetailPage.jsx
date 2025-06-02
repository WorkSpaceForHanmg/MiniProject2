// DetailPage.jsx
import React, { useState, useMemo, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import styles from '../styles/DetailPage.module.css';

export default function DetailPage({ diaries, onUpdateDiary, onDeleteDiary }) {
  const navigate = useNavigate();
  const { diaryId } = useParams();
  const selectedDiaryId = Number(diaryId);

  const [projectFilter, setProjectFilter] = useState('');
  const [tagFilter, setTagFilter] = useState('');
  const [isEditing, setIsEditing] = useState(false);
  const [editForm, setEditForm] = useState({
    summary: '',
    code: '',
    devReview: '',
    challenges: '',
    errors: '',
  });

  // 프로젝트, 태그 필터링
  const filteredDiaries = useMemo(() => {
    return diaries.filter(diary => {
      const matchesProject = projectFilter === '' || diary.project.includes(projectFilter);
      const matchesTag = tagFilter === '' || diary.tags.some(tag => tag.includes(tagFilter));
      return matchesProject && matchesTag;
    });
  }, [diaries, projectFilter, tagFilter]);

  // 날짜 내림차순 정렬
  const sortedDiaries = useMemo(() => {
    return [...filteredDiaries].sort((a, b) => (a.date < b.date ? 1 : -1));
  }, [filteredDiaries]);

  // 선택된 일기 찾기
  const selectedDiary = sortedDiaries.find(d => d.id === selectedDiaryId) || null;

  // JSON content 파싱
  const parsedContent = useMemo(() => {
    if (!selectedDiary || !selectedDiary.content) return {};
    try {
      return JSON.parse(selectedDiary.content);
    } catch {
      return {};
    }
  }, [selectedDiary]);

  // diaryId가 없거나 유효하지 않으면 첫 번째 일기로 강제 이동
  useEffect(() => {
    if ((!diaryId || !selectedDiary) && sortedDiaries.length > 0) {
      navigate(`/diary/${sortedDiaries[0].id}`, { replace: true });
    }
  }, [diaryId, selectedDiary, sortedDiaries, navigate]);

  // 선택된 일기 변경 시 에디트 폼 초기화
  useEffect(() => {
    if (selectedDiary) {
      setEditForm({
        summary: selectedDiary.summary || '',
        code: parsedContent.codeExplanation || '',
        devReview: parsedContent.devReview || '',
        challenges: parsedContent.challenges || '',
        errors: parsedContent.errorSummary || '',
      });
      setIsEditing(false);
    }
  }, [selectedDiary, parsedContent]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEditForm(prev => ({ ...prev, [name]: value }));
  };

  const handleSave = () => {
    const updatedDiary = {
      summary: editForm.summary,
      content: JSON.stringify({
        codeExplanation: editForm.code,
        devReview: editForm.devReview,
        challenges: editForm.challenges,
        errorSummary: editForm.errors,
        errorTags: parsedContent.errorTags || [],
        errorSolution: parsedContent.errorSolution || '',
      }),
    };
    onUpdateDiary(selectedDiary.id, updatedDiary);
    setIsEditing(false);
  };

  const handleDelete = () => {
    if (window.confirm('정말 삭제하시겠습니까?')) {
      onDeleteDiary(selectedDiary.id);
      navigate('/', { replace: true });
    }
  };

  const handleSelectDiary = (id) => {
    setIsEditing(false);
    navigate(`/diary/${id}`);
  };

  return (
    <div className={styles.detailContainer}>
      <header className={styles.detailHeader}>
        <button className={styles.backBtn} onClick={() => navigate(-1)}>
          ← 뒤로가기
        </button>
        <h2>일기 상세 보기</h2>
      </header>

      <div className={styles.detailContent}>
        <div className={styles.leftPanel}>
          <div className={styles.filters}>
            <input
              type="text"
              placeholder="프로젝트 검색"
              value={projectFilter}
              onChange={(e) => setProjectFilter(e.target.value)}
            />
            <input
              type="text"
              placeholder="태그 검색"
              value={tagFilter}
              onChange={(e) => setTagFilter(e.target.value)}
            />
          </div>

          <ul className={styles.diaryList}>
            {sortedDiaries.map(diary => (
              <li
                key={diary.id}
                className={diary.id === selectedDiaryId ? styles.selectedDiaryItem : ''}
                onClick={() => handleSelectDiary(diary.id)}
              >
                {diary.date}
              </li>
            ))}
          </ul>
        </div>

        <div className={styles.rightPanel}>
          {selectedDiary ? (
            <>
              <h3>{selectedDiary.project}</h3>
              <p><strong>날짜:</strong> {selectedDiary.date}</p>
              <p><strong>태그:</strong> {selectedDiary.tags.join(', ')}</p>

              <div className={styles.buttons}>
                {!isEditing && (
                  <>
                    <button onClick={() => setIsEditing(true)}>수정</button>
                    <button onClick={handleDelete}>삭제</button>
                  </>
                )}
              </div>

              {isEditing ? (
                <>
                  <label><strong>요약</strong></label>
                  <textarea
                    name="summary"
                    value={editForm.summary}
                    onChange={handleChange}
                    rows={2}
                    className={styles.textarea}
                  />

                  <label><strong>코드 및 설명</strong></label>
                  <textarea
                    name="code"
                    value={editForm.code}
                    onChange={handleChange}
                    rows={6}
                    className={styles.textarea}
                  />

                  <label><strong>개발 소감</strong></label>
                  <textarea
                    name="devReview"
                    value={editForm.devReview}
                    onChange={handleChange}
                    rows={4}
                    className={styles.textarea}
                  />

                  <label><strong>어려웠던 점</strong></label>
                  <textarea
                    name="challenges"
                    value={editForm.challenges}
                    onChange={handleChange}
                    rows={4}
                    className={styles.textarea}
                  />

                  <label><strong>에러 및 해결</strong></label>
                  <textarea
                    name="errors"
                    value={editForm.errors}
                    onChange={handleChange}
                    rows={6}
                    className={styles.textarea}
                  />

                  <div className={styles.editButtons}>
                    <button onClick={handleSave}>저장</button>
                    <button onClick={() => setIsEditing(false)}>취소</button>
                  </div>
                </>
              ) : (
                <>
                  <section>
                    <h4>요약</h4>
                    <p>{selectedDiary.summary}</p>
                  </section>

                  <section>
                    <h4>코드 및 설명</h4>
                    <pre>{editForm.code}</pre>
                  </section>

                  <section>
                    <h4>개발 소감</h4>
                    <p>{editForm.devReview}</p>
                  </section>

                  <section>
                    <h4>어려웠던 점</h4>
                    <p>{editForm.challenges}</p>
                  </section>

                  <section>
                    <h4>에러 및 해결</h4>
                    <pre>{editForm.errors}</pre>
                  </section>
                </>
              )}
            </>
          ) : (
            <p>선택된 일기가 없습니다.</p>
          )}
        </div>
      </div>
    </div>
  );
}
