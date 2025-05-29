import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from '../styles/NewDiaryForm.module.css';

const dummyProjects = ['개발 일기 웹앱', '프로젝트 A', '프로젝트 B'];
const dummyTags = ['React', 'Spring', 'JavaScript'];

export default function NewDiaryForm({ onSave }) {
  const navigate = useNavigate();

  const [date, setDate] = useState('');
  const [project, setProject] = useState('');
  const [tags, setTags] = useState('');
  const [code, setCode] = useState('');
  const [devReview, setDevReview] = useState('');
  const [challenges, setChallenges] = useState('');
  const [errors, setErrors] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!date || !project) {
      alert('날짜와 프로젝트명은 필수 입력 항목입니다.');
      return;
    }

    const newDiary = {
      id: Date.now(), // 임시로 timestamp를 id로 사용
      date,
      project,
      tags: tags
        .split(',')
        .map((tag) => tag.trim())
        .filter((tag) => tag.length > 0),
      summary: devReview || '내용 없음',
      content: `
[코드 설명]
${code}

[개발 소감]
${devReview}

[어려웠던 점]
${challenges}

[에러 해결]
${errors}
      `.trim(),
    };

    onSave(newDiary);
    navigate('/'); // 저장 후 메인 페이지로 이동
  };

  return (
    <div className={styles.newDiaryContainer}>
      <header className={styles.newDiaryHeader}>
        <button className={styles.backBtn} onClick={() => navigate('/')}>
          ← 뒤로가기
        </button>
        <h2>새 일기 작성</h2>
      </header>

      <form className={styles.newDiaryForm} onSubmit={handleSubmit}>
        <label>
          날짜:
          <input
            type="date"
            value={date}
            onChange={(e) => setDate(e.target.value)}
            required
          />
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
          <textarea
            rows={6}
            value={code}
            onChange={(e) => setCode(e.target.value)}
          />
        </label>

        <label>
          개발 소감:
          <textarea
            rows={4}
            value={devReview}
            onChange={(e) => setDevReview(e.target.value)}
          />
        </label>

        <label>
          어려웠던 점:
          <textarea
            rows={4}
            value={challenges}
            onChange={(e) => setChallenges(e.target.value)}
          />
        </label>

        <label>
          에러 발생 및 해결 방법:
          <textarea
            rows={4}
            value={errors}
            onChange={(e) => setErrors(e.target.value)}
          />
        </label>

        <div className={styles.formBtnGroup}>
          <button type="submit" className={styles.saveBtn}>
            저장
          </button>
          <button
            type="button"
            className={styles.cancelBtn}
            onClick={() => navigate('/')}
          >
            취소
          </button>
        </div>
      </form>
    </div>
  );
}
