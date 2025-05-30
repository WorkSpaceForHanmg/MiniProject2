import React, { useState, useMemo, useEffect } from 'react';
import styles from '../styles/DetailPage.module.css';

export default function DetailPage({
  diaries,
  selectedDiaryId,
  onBack,
  onSelectDiary,
  onUpdateDiary,
  onDeleteDiary,
}) {
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

  const filteredDiaries = useMemo(() => {
    return diaries.filter(diary => {
      const matchesProject = projectFilter === '' || diary.project.includes(projectFilter);
      const matchesTag = tagFilter === '' || diary.tags.some(tag => tag.includes(tagFilter));
      return matchesProject && matchesTag;
    });
  }, [diaries, projectFilter, tagFilter]);

  const sortedDiaries = useMemo(() => {
    return [...filteredDiaries].sort((a, b) => (a.date < b.date ? 1 : -1));
  }, [filteredDiaries]);

  const selectedDiary = sortedDiaries.find(d => d.id === selectedDiaryId) || null;

  const parsedContent = useMemo(() => {
    if (!selectedDiary || !selectedDiary.content) return {};
    try {
      return JSON.parse(selectedDiary.content);
    } catch {
      return {};
    }
  }, [selectedDiary]);

  useEffect(() => {
    if (sortedDiaries.length > 0 && !selectedDiaryId) {
      onSelectDiary(sortedDiaries[0].id);
    }
  }, [sortedDiaries, selectedDiaryId, onSelectDiary]);

  useEffect(() => {
    if (selectedDiary) {
      setEditForm({
        summary: selectedDiary.summary || '',
        code: parsedContent.code || '',
        devReview: parsedContent.devReview || '',
        challenges: parsedContent.challenges || '',
        errors: parsedContent.errors || '',
      });
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
        code: editForm.code,
        devReview: editForm.devReview,
        challenges: editForm.challenges,
        errors: editForm.errors,
      }),
    };
    onUpdateDiary(selectedDiary.id, updatedDiary);
    setIsEditing(false);
  };

  const handleDelete = () => {
    if (window.confirm('정말 삭제하시겠습니까?')) {
      onDeleteDiary(selectedDiary.id);
    }
  };

  return (
    <div className={styles.detailContainer}>
      <header className={styles.detailHeader}>
        <button className={styles.backBtn} onClick={onBack}>
          ← 뒤로가기
        </button>
        <h2>일기 상세 보기</h2>
      </header>

      <div className={styles.detailContent}>
        {/* 왼쪽 */}
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
                onClick={() => {
                  setIsEditing(false);
                  onSelectDiary(diary.id);
                }}
              >
                {diary.date}
              </li>
            ))}
          </ul>
        </div>

        {/* 오른쪽 */}
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

                  <label><strong>코드 설명</strong></label>
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

                  <label><strong>에러 해결</strong></label>
                  <textarea
                    name="errors"
                    value={editForm.errors}
                    onChange={handleChange}
                    rows={4}
                    className={styles.textarea}
                  />

                  <div className={styles.buttons}>
                    <button className={styles.saveBtn} onClick={handleSave}>저장</button>
                    <button className={styles.cancelBtn} onClick={() => setIsEditing(false)}>취소</button>
                  </div>
                </>
              ) : (
                <>
                  <h4>요약</h4>
                  <p>{selectedDiary.summary}</p>

                  <h4>코드 설명</h4>
                  <pre className={styles.contentBox}>{parsedContent.code || '-'}</pre>

                  <h4>개발 소감</h4>
                  <pre className={styles.contentBox}>{parsedContent.devReview || '-'}</pre>

                  <h4>어려웠던 점</h4>
                  <pre className={styles.contentBox}>{parsedContent.challenges || '-'}</pre>

                  <h4>에러 해결</h4>
                  <pre className={styles.contentBox}>{parsedContent.errors || '-'}</pre>
                </>
              )}
            </>
          ) : (
            <p>일기를 선택해주세요.</p>
          )}
        </div>
      </div>
    </div>
  );
}
