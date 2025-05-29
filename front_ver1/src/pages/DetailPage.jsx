import React, { useState, useEffect, useMemo } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import styles from '../styles/DetailPage.module.css';

export default function DetailPage({ diaries, onUpdateDiary, onDeleteDiary }) {
  const { id } = useParams();
  const navigate = useNavigate();
  const selectedDiaryId = Number(id);

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

  const selectedDiary = diaries.find(d => d.id === selectedDiaryId) || null;

  // content 문자열 파싱 함수
  const parseContent = (content) => {
    const sections = {
      code: '',
      devReview: '',
      challenges: '',
      errors: '',
    };

    // 정규식으로 분리
    const regex = /\[코드 설명\]\n([\s\S]*?)\n\[개발 소감\]\n([\s\S]*?)\n\[어려웠던 점\]\n([\s\S]*?)\n\[에러 해결\]\n([\s\S]*)/;
    const match = content.match(regex);

    if (match) {
      sections.code = match[1].trim();
      sections.devReview = match[2].trim();
      sections.challenges = match[3].trim();
      sections.errors = match[4].trim();
    }
    return sections;
  };

  useEffect(() => {
    if (selectedDiary) {
      const sections = parseContent(selectedDiary.content || '');
      setEditForm({
        summary: selectedDiary.summary || '',
        code: sections.code,
        devReview: sections.devReview,
        challenges: sections.challenges,
        errors: sections.errors,
      });
    }
  }, [selectedDiary]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEditForm(prev => ({ ...prev, [name]: value }));
  };

  const handleSave = () => {
    const content = `
[코드 설명]
${editForm.code}

[개발 소감]
${editForm.devReview}

[어려웠던 점]
${editForm.challenges}

[에러 해결]
${editForm.errors}
    `.trim();

    onUpdateDiary(selectedDiary.id, {
      summary: editForm.summary,
      content,
    });
    setIsEditing(false);
  };

  const handleDelete = () => {
    if (window.confirm('정말 삭제하시겠습니까?')) {
      onDeleteDiary(selectedDiary.id);
      navigate('/');
    }
  };

  // 왼쪽 필터링된 일기 목록
  const filteredDiaries = useMemo(() => {
    return diaries.filter(diary => {
      const matchesProject = projectFilter === '' || diary.project.includes(projectFilter);
      const matchesTag = tagFilter === '' || diary.tags.some(tag => tag.includes(tagFilter));
      return matchesProject && matchesTag;
    }).sort((a, b) => (a.date < b.date ? 1 : -1));
  }, [diaries, projectFilter, tagFilter]);

  // 읽기모드에서 파싱한 내용
  const parsedContent = selectedDiary ? parseContent(selectedDiary.content || '') : {
    code: '',
    devReview: '',
    challenges: '',
    errors: '',
  };

  return (
    <div className={styles.detailContainer}>
      <header className={styles.detailHeader}>
        <button className={styles.backBtn} onClick={() => navigate('/')}>← 뒤로가기</button>
        <h2>일기 상세 보기</h2>
      </header>

      <div className={styles.detailContent}>
        {/* 왼쪽 패널 */}
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
            {filteredDiaries.map(diary => (
              <li
                key={diary.id}
                className={diary.id === selectedDiaryId ? styles.selectedDiaryItem : ''}
                onClick={() => {
                  setIsEditing(false);
                  navigate(`/detail/${diary.id}`);
                }}
              >
                {diary.date}
              </li>
            ))}
          </ul>
        </div>

        {/* 오른쪽 패널 */}
        <div className={styles.rightPanel}>
          {selectedDiary ? (
            <>
              <h3>{selectedDiary.project}</h3>
              <p><strong>날짜:</strong> {selectedDiary.date}</p>
              <p><strong>태그:</strong> {selectedDiary.tags.join(', ')}</p>

              <div className={styles.buttons}>
                {!isEditing && (
                  <>
                    <button className={styles.editBtn} onClick={() => setIsEditing(true)}>수정</button>
                    <button className={styles.deleteBtn} onClick={handleDelete}>삭제</button>
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
            <p>존재하지 않는 일기입니다. <button onClick={() => navigate('/')}>홈으로</button></p>
          )}
        </div>
      </div>
    </div>
  );
}
