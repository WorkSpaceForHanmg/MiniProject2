// NewDiaryForm.jsx (수정된 버전)
import React, { useState, useEffect } from 'react';
import styles from '../styles/NewDiaryForm.module.css';
import { useNavigate } from 'react-router-dom';
import { diaryApi } from '../api/diaryApi';

// 기본 데이터 (서버에서 로드 실패시 사용)
const defaultProjects = ['개발 일기 웹앱', '프로젝트 A', '프로젝트 B'];
const defaultTags = ['React', 'Spring', 'JavaScript', 'SQL', 'JAVA'];

export default function NewDiaryForm({ onSave, loading }) {
  const [date, setDate] = useState('');
  const [project, setProject] = useState('');
  const [tags, setTags] = useState('');
  const [code, setCode] = useState('');
  const [devReview, setDevReview] = useState('');
  const [challenges, setChallenges] = useState('');
  const [errorSummary, setErrorSummary] = useState('');
  const [errorTags, setErrorTags] = useState('');
  const [errorSolution, setErrorSolution] = useState('');

  const [projects, setProjects] = useState(defaultProjects);
  const [availableTags, setAvailableTags] = useState(defaultTags);
  const [formLoading, setFormLoading] = useState(false);

  const navigate = useNavigate();

  // 컴포넌트 마운트 시 프로젝트와 태그 목록 로드
  useEffect(() => {
    const loadFormData = async () => {
      try {
        setFormLoading(true);
        const [projectsData, tagsData] = await Promise.all([
          diaryApi.getProjects(),
          diaryApi.getTags()
        ]);
        setProjects(projectsData);
        setAvailableTags(tagsData);
      } catch (err) {
        console.error('폼 데이터 로드 실패:', err);
        // 기본 데이터 사용
      } finally {
        setFormLoading(false);
      }
    };

    loadFormData();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const newDiary = {
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

    await onSave(newDiary);
    navigate('/');
  };

  return (
    <div className={styles.newDiaryContainer}>
      <header className={styles.newDiaryHeader}>
        <button className={styles.backBtn} onClick={() => navigate(-1)} disabled={loading}>
          ←뒤로가기
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
            disabled={loading}
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
            disabled={loading || formLoading}
          />
          <datalist id="project-list">
            {projects.map((p) => (
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
            disabled={loading}
          />
          <datalist id="tag-list">
            {availableTags.map((t) => (
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
            disabled={loading}
          />
        </label>

        <label>
          개발 소감:
          <textarea
            rows={4}
            value={devReview}
            onChange={(e) => setDevReview(e.target.value)}
            disabled={loading}
          />
        </label>

        <label>
          어려웠던 점:
          <textarea
            rows={4}
            value={challenges}
            onChange={(e) => setChallenges(e.target.value)}
            disabled={loading}
          />
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
              disabled={loading}
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
              disabled={loading}
            />
          </label>

          <label>
            해결 방법:
            <textarea
              rows={4}
              value={errorSolution}
              onChange={(e) => setErrorSolution(e.target.value)}
              placeholder="해결한 방법을 간단히 작성"
              disabled={loading}
            />
          </label>
        </fieldset>

        <div className={styles.formBtnGroup}>
          <button
            type="submit"
            className={styles.saveBtn}
            disabled={loading || formLoading}
          >
            {loading ? '저장 중...' : '저장'}
          </button>
          <button
            type="button"
            className={styles.cancelBtn}
            onClick={() => navigate(-1)}
            disabled={loading}
          >
            취소
          </button>
        </div>
      </form>
    </div>
  );
}