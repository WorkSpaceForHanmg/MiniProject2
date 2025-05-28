import React, { useState } from 'react';
import styles from '../styles/NewDiaryForm.module.css';


const dummyProjects = ['개발 일기 웹앱', '프로젝트 A', '프로젝트 B'];
const dummyTags = ['React', 'Spring', 'JavaScript'];

export default function NewDiaryForm({ onCancel }) {
  const [date, setDate] = useState('');
  const [project, setProject] = useState('');
  const [tags, setTags] = useState('');
  const [code, setCode] = useState('');
  const [devReview, setDevReview] = useState('');
  const [challenges, setChallenges] = useState('');
  const [errors, setErrors] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    alert('새 일기 저장 완료 (로직 구현 필요)');
    onCancel();
  };

  return (
    <div className={styles.newDiaryContainer}>
      <header className={styles.newDiaryHeader}>
        <button className={styles.backBtn} onClick={onCancel}>
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
            placeholder="코드와 설명을 작성하세요"
          />
        </label>

        <label>
          개발 소감:
          <textarea
            rows={4}
            value={devReview}
            onChange={(e) => setDevReview(e.target.value)}
            placeholder="개발하면서 느낀 점"
          />
        </label>

        <label>
          어려웠던 점:
          <textarea
            rows={4}
            value={challenges}
            onChange={(e) => setChallenges(e.target.value)}
            placeholder="개발 중 어려웠던 점"
          />
        </label>

        <label>
          에러 발생 및 해결 방법:
          <textarea
            rows={4}
            value={errors}
            onChange={(e) => setErrors(e.target.value)}
            placeholder="발생한 에러와 해결 방법"
          />
        </label>

        <div className={styles.formBtnGroup}>
          <button type="submit" className={styles.saveBtn}>
            저장
          </button>
          <button type="button" className={styles.cancelBtn} onClick={onCancel}>
            취소
          </button>
        </div>
      </form>
    </div>
  );
}
