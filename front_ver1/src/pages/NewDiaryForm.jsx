import React, { useState } from 'react';
import styles from '../styles/NewDiaryForm.module.css';
import { useNavigate } from 'react-router-dom';

const dummyProjects = ['개발 일기 웹앱', '프로젝트 A', '프로젝트 B'];
const dummyTags = ['React', 'Spring', 'JavaScript', 'SQL', 'JAVA'];

export default function NewDiaryForm({ onSave }) {
  const [date, setDate] = useState('');
  const [project, setProject] = useState('');
  const [tags, setTags] = useState('');
  const [code, setCode] = useState('');
  const [devReview, setDevReview] = useState('');
  const [challenges, setChallenges] = useState('');
  const [errorSummary, setErrorSummary] = useState('');
  const [errorTags, setErrorTags] = useState('');
  const [errorSolution, setErrorSolution] = useState('');

  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();

    const newDiary = {
      id: Date.now(),  // id는 App에서 덮어씌우지만 임시로 넣음
      date,
      project,
      tags: tags.split(',').map(tag => tag.trim()).filter(Boolean),
      summary: devReview.trim() || '내용 없음',
      content: JSON.stringify({
        codeExplanation: code.trim(),
        devReview: devReview.trim(),
        challenges: challenges.trim(),
        errorSummary: errorSummary.trim(),
        errorTags: errorTags.split(',').map(tag => tag.trim()).filter(Boolean),
        errorSolution: errorSolution.trim(),
      }),
    };

    onSave(newDiary);
    navigate('/');
  };

  return (
    <div className={styles.newDiaryContainer}>
      <header className={styles.newDiaryHeader}>
        <button className={styles.backBtn} onClick={() => navigate(-1)}>
          ←뒤로가기
        </button>
        <h2>새 일기 작성</h2>
      </header>

      <form className={styles.newDiaryForm} onSubmit={handleSubmit}>
        <label>
          날짜:
          <input type="date" value={date} onChange={(e) => setDate(e.target.value)} required />
        </label>

        <label>
          프로젝트명:
          <input
            type="text"
            list="project-list"
            value={project}
            onChange={(e) => setProject(e.target.value)}
            required
            placeholder="프로젝트 선택 또는 입력"
          />
          <datalist id="project-list">
            {dummyProjects.map((p) => (
              <option key={p} value={p} />
            ))}
          </datalist>
        </label>

        <label>
          태그 (콤마로 구분):
          <input
            type="text"
            list="tag-list"
            value={tags}
            onChange={(e) => setTags(e.target.value)}
            placeholder="예: React, JavaScript"
          />
          <datalist id="tag-list">
            {dummyTags.map((t) => (
              <option key={t} value={t} />
            ))}
          </datalist>
        </label>

        <label>
          코드 및 코드 설명:
          <textarea rows={6} value={code} onChange={(e) => setCode(e.target.value)} />
        </label>

        <label>
          개발 소감:
          <textarea rows={4} value={devReview} onChange={(e) => setDevReview(e.target.value)} />
        </label>

        <label>
          어려웠던 점:
          <textarea rows={4} value={challenges} onChange={(e) => setChallenges(e.target.value)} />
        </label>

        <fieldset className={styles.errorSection}>
          <legend>에러 및 해결</legend>

          <label>
            에러 한 줄 요약:
            <input
              type="text"
              value={errorSummary}
              onChange={(e) => setErrorSummary(e.target.value)}
              placeholder="예: useState 초기화 오류"
            />
          </label>

          <label>
            에러 태그 (콤마로 구분):
            <input
              type="text"
              list="tag-list"
              value={errorTags}
              onChange={(e) => setErrorTags(e.target.value)}
              placeholder="예: React, Hook"
            />
          </label>

          <label>
            해결 방법:
            <textarea
              rows={4}
              value={errorSolution}
              onChange={(e) => setErrorSolution(e.target.value)}
              placeholder="해결한 방법을 간단히 작성"
            />
          </label>
        </fieldset>

        <div className={styles.formBtnGroup}>
          <button type="submit" className={styles.saveBtn}>저장</button>
          <button type="button" className={styles.cancelBtn} onClick={() => navigate(-1)}>취소</button>
        </div>
      </form>
    </div>
  );
}
