// App.jsx
import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import MainPage from './pages/MainPage';
import DetailPage from './pages/DetailPage';
import NewDiaryForm from './pages/NewDiaryForm';

const initialDiaries = [
  {
    id: 1,
    date: '2025-05-26',
    project: '개발 일기 웹앱',
    tags: ['React', 'Spring'],
    summary: '오늘은 일기 폼을 완성함...',
    content: '폼 구성하고 상태 관리 마무리함.',
  },
  {
    id: 2,
    date: '2025-05-25',
    project: '개발 일기 웹앱',
    tags: ['React'],
    summary: '기초 구조 설계',
    content: '프로젝트 구조를 설정하고 폴더 나눔.',
  },
];

export default function App() {
  const [diaries, setDiaries] = useState(initialDiaries);

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
    <Router>
      <Routes>
        <Route path="/" element={<MainPage diaries={diaries} />} />
        <Route
          path="/detail/:id"
          element={
            <DetailPage
              diaries={diaries}
              onUpdateDiary={handleUpdateDiary}
              onDeleteDiary={handleDeleteDiary}
            />
          }
        />
        <Route path="/new" element={<NewDiaryForm onSave={handleAddDiary} />} />
      </Routes>
    </Router>
  );
}
