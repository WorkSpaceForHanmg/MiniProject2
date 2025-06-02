import React, { useState } from 'react';
import { Routes, Route } from 'react-router-dom';

import MainPage from './MainPage';
import DetailPage from './DetailPage';
import NewDiaryForm from './NewDiaryForm';
import ErrorNote from './ErrorNote';

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

  // newDiary는 이미 객체 형태 (content는 JSON 문자열)
  const handleAddDiary = (newDiary) => {
    const newId = Date.now();
    setDiaries((prev) => [...prev, { ...newDiary, id: newId }]);
  };

  const handleUpdateDiary = (id, updatedDiary) => {
    setDiaries((prev) =>
      prev.map((d) => (d.id === id ? { ...d, ...updatedDiary } : d))
    );
  };

  const handleDeleteDiary = (id) => {
    setDiaries((prev) => prev.filter((d) => d.id !== id));
  };

  return (
    <Routes>
      <Route path="/" element={<MainPage diaries={diaries} />} />
      <Route
        path="/diary/:diaryId"
        element={
          <DetailPage
            diaries={diaries}
            onUpdateDiary={handleUpdateDiary}
            onDeleteDiary={handleDeleteDiary}
          />
        }
      />
      <Route path="/new" element={<NewDiaryForm onSave={handleAddDiary} />} />
      <Route path="/error-note" element={<ErrorNote />} />
    </Routes>
  );
}

export default App;
