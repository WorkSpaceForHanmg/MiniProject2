// App.jsx (수정된 버전)
import React, { useState, useEffect } from 'react';
import { Routes, Route } from 'react-router-dom';

import MainPage from './components/MainPage';
import DetailPage from './components/DetailPage';
import NewDiaryForm from './components/NewDiaryForm';
import ErrorNote from './components/ErrorNote';
import { diaryApi } from './api/diaryApi';

const initialDiaries = [
  {
    id: 1,
    date: '2025-05-26',
    project: '개발 일기 웹앱',
    tags: ['React', 'Spring'],
    summary: '오늘은 일기 폼을 완성함...',
    content: JSON.stringify({
      codeExplanation: '폼 구성하고 상태 관리 마무리함.',
      devReview: '',
      challenges: '',
      errorSummary: '',
      errorTags: [],
      errorSolution: '',
    }),
  },
  {
    id: 2,
    date: '2025-05-25',
    project: '개발 일기 웹앱',
    tags: ['React'],
    summary: '기초 구조 설계',
    content: JSON.stringify({
      codeExplanation: '프로젝트 구조를 설정하고 폴더 나눔.',
      devReview: '',
      challenges: '',
      errorSummary: '',
      errorTags: [],
      errorSolution: '',
    }),
  },
];

function App() {
  const [diaries, setDiaries] = useState(initialDiaries);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // 컴포넌트 마운트 시 서버에서 일기 데이터 로드
  useEffect(() => {
    const loadDiaries = async () => {
      try {
        setLoading(true);
        setError(null);
        const data = await diaryApi.getAllDiaries();
        setDiaries(data);
      } catch (err) {
        console.error('일기 데이터 로드 실패:', err);
        setError('일기 데이터를 불러오는데 실패했습니다.');
        // 에러 발생 시 초기 데이터 사용
        setDiaries(initialDiaries);
      } finally {
        setLoading(false);
      }
    };

    loadDiaries();
  }, []);

  // 새 일기 추가
  const handleAddDiary = async (newDiary) => {
    try {
      setLoading(true);
      const createdDiary = await diaryApi.createDiary(newDiary);
      setDiaries((prev) => [...prev, createdDiary]);
      setError(null);
    } catch (err) {
      console.error('일기 추가 실패:', err);
      setError('일기 추가에 실패했습니다.');
      // 오프라인 모드: 로컬에만 추가
      const newId = Date.now();
      setDiaries((prev) => [...prev, { ...newDiary, id: newId }]);
    } finally {
      setLoading(false);
    }
  };

  // 일기 수정
  const handleUpdateDiary = async (id, updatedDiary) => {
    try {
      setLoading(true);
      const updated = await diaryApi.updateDiary(id, updatedDiary);
      setDiaries((prev) =>
        prev.map((d) => (d.id === id ? { ...d, ...updated } : d))
      );
      setError(null);
    } catch (err) {
      console.error('일기 수정 실패:', err);
      setError('일기 수정에 실패했습니다.');
      // 오프라인 모드: 로컬에만 수정
      setDiaries((prev) =>
        prev.map((d) => (d.id === id ? { ...d, ...updatedDiary } : d))
      );
    } finally {
      setLoading(false);
    }
  };

  // 일기 삭제
  const handleDeleteDiary = async (id) => {
    try {
      setLoading(true);
      await diaryApi.deleteDiary(id);
      setDiaries((prev) => prev.filter((d) => d.id !== id));
      setError(null);
    } catch (err) {
      console.error('일기 삭제 실패:', err);
      setError('일기 삭제에 실패했습니다.');
      // 오프라인 모드: 로컬에서만 삭제
      setDiaries((prev) => prev.filter((d) => d.id !== id));
    } finally {
      setLoading(false);
    }
  };

  if (loading && diaries.length === 0) {
    return <div style={{ textAlign: 'center', padding: '20px' }}>로딩 중...</div>;
  }

  return (
    <div>
      {error && (
        <div style={{ 
          backgroundColor: '#ffe6e6', 
          color: '#d00', 
          padding: '10px', 
          textAlign: 'center',
          marginBottom: '10px'
        }}>
          {error}
        </div>
      )}
      <Routes>
        <Route path="/" element={<MainPage diaries={diaries} loading={loading} />} />
        <Route
          path="/diary/:diaryId"
          element={
            <DetailPage
              diaries={diaries}
              onUpdateDiary={handleUpdateDiary}
              onDeleteDiary={handleDeleteDiary}
              loading={loading}
            />
          }
        />
        <Route 
          path="/new" 
          element={<NewDiaryForm onSave={handleAddDiary} loading={loading} />} 
        />
        <Route path="/error-note" element={<ErrorNote diaries={diaries} />} />
      </Routes>
    </div>
  );
}

export default App;