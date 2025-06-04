import React, { useState } from 'react';
import styles from '../styles/NewDiaryForm.module.css';
import { useNavigate } from 'react-router-dom';

const dummyProjects = ['개발 일기 웹앱', '프로젝트 A', '프로젝트 B'];
const dummyTags = ['React', 'Spring', 'JavaScript', 'SQL', 'JAVA'];

const splitTags = (tagString) =>
  tagString
    .split(',')
    .map(tag => tag.trim())
    .filter(Boolean);

export default function NewDiaryForm({ onSave }) {
  const [form, setForm] = useState({
    date: '',
    project: '',
    tags: '',
    summary: '',
    code: '',
    devReview: '',
    challenges: '',
    errorSummary: '',
    errorTags: '',
    errorSolution: '',
  });

  const [isSubmitting, setIsSubmitting] = useState(false);
  const [feedbackMessage, setFeedbackMessage] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!form.date || !form.project) {
      setFeedbackMessage('날짜와 프로젝트명은 필수 입력 항목입니다.');
      return;
    }

    setIsSubmitting(true);
    setFeedbackMessage('저장 중입니다...');

    try {
      const newDiary = {
        // id는 context에서 부여하므로 여기서 빼는 것이 안전함
        date: form.date,
        project: form.project,
        tags: splitTags(form.tags),
        summary: form.summary.trim() || '내용 없음',
        content: JSON.stringify({
          codeExplanation: form.code.trim(),
          devReview: form.devReview.trim(),
          challenges: form.challenges.trim(),
          errorSummary: form.errorSummary.trim(),
          errorTags: splitTags(form.errorTags),
          errorSolution: form.errorSolution.trim(),
        }),
      };

      await new Promise(resolve => setTimeout(resolve, 1000)); // API 연동 전 임시 지연
      await onSave(newDiary);

      setFeedbackMessage('성공적으로 저장되었습니다!');
      setTimeout(() => navigate('/'), 1000);
    } catch (error) {
      console.error(error);
      setFeedbackMessage('저장에 실패했습니다. 다시 시도해주세요.');
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className={styles.newDiaryContainer}>
      <header className={styles.newDiaryHeader}>
        <button className={styles.backBtn} onClick={() => navigate(-1)} disabled={isSubmitting}>
          ← 뒤로가기
        </button>
        <h2>새 일기 작성</h2>
      </header>

      <form className={styles.newDiaryForm} onSubmit={handleSubmit}>
        <label>
          날짜:
          <input
            type="date"
            name="date"
            value={form.date}
            onChange={handleChange}
            required
          />
        </label>

        <label>
          프로젝트명:
          <input
            type="text"
            name="project"
            list="project-list"
            value={form.project}
            onChange={handleChange}
            required
            placeholder="프로젝트 선택 또는 입력"
          />
          <datalist id="project-list">
            {dummyProjects.map(p => (
              <option key={p} value={p} />
            ))}
          </datalist>
        </label>

        <label>
          태그 (콤마로 구분):
          <input
            type="text"
            name="tags"
            list="tag-list"
            value={form.tags}
            onChange={handleChange}
            placeholder="예: React, JavaScript"
          />
          <datalist id="tag-list">
            {dummyTags.map(t => (
              <option key={t} value={t} />
            ))}
          </datalist>
        </label>

        <label>
          요약:
          <textarea
            name="summary"
            rows={2}
            value={form.summary}
            onChange={handleChange}
          />
        </label>

        <label>
          코드 및 코드 설명:
          <textarea
            name="code"
            rows={6}
            value={form.code}
            onChange={handleChange}
          />
        </label>

        <label>
          개발 소감:
          <textarea
            name="devReview"
            rows={4}
            value={form.devReview}
            onChange={handleChange}
          />
        </label>

        <label>
          어려웠던 점:
          <textarea
            name="challenges"
            rows={4}
            value={form.challenges}
            onChange={handleChange}
          />
        </label>

        <fieldset className={styles.errorSection}>
          <legend>에러 및 해결</legend>

          <label>
            에러 한 줄 요약:
            <input
              type="text"
              name="errorSummary"
              value={form.errorSummary}
              onChange={handleChange}
              placeholder="예: useState 초기화 오류"
            />
          </label>

          <label>
            에러 태그 (콤마로 구분):
            <input
              type="text"
              name="errorTags"
              list="tag-list"
              value={form.errorTags}
              onChange={handleChange}
              placeholder="예: React, Hook"
            />
          </label>

          <label>
            해결 방법:
            <textarea
              name="errorSolution"
              rows={4}
              value={form.errorSolution}
              onChange={handleChange}
              placeholder="해결한 방법을 간단히 작성"
            />
          </label>
        </fieldset>

        {feedbackMessage && <p className={styles.feedback}>{feedbackMessage}</p>}

        <div className={styles.formBtnGroup}>
          <button type="submit" className={styles.saveBtn} disabled={isSubmitting}>
            {isSubmitting ? '저장 중...' : '저장'}
          </button>
          <button
            type="button"
            className={styles.cancelBtn}
            onClick={() => navigate(-1)}
            disabled={isSubmitting}
          >
            취소
          </button>
        </div>
      </form>
    </div>
  );
}
