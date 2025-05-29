import React, { useState } from 'react';
import MainPage from './pages/MainPage';
import DiaryDetail from './pages/DetailPage';
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
  const [currentPage, setCurrentPage] = useState('main');
  const [diaries, setDiaries] = useState(initialDiaries);
  const [selectedDiaryId, setSelectedDiaryId] = useState(null);

  const selectedDiary = diaries.find((d) => d.id === selectedDiaryId);

  const handleAddDiary = (newDiary) => {
    const newId = Date.now(); // 간단한 ID 생성
    setDiaries((prev) => [...prev, { ...newDiary, id: newId }]);
    setCurrentPage('main');
  };

  return (
    <>
      {currentPage === 'main' && (
        <MainPage
          diaries={diaries}
          onViewDetail={(id) => {
            setSelectedDiaryId(id);
            setCurrentPage('detail');
          }}
          onCreateNew={() => setCurrentPage('new')}
        />
      )}

      {currentPage === 'detail' && selectedDiary && (
        <DiaryDetail diary={selectedDiary} onBack={() => setCurrentPage('main')} />
      )}

      {currentPage === 'new' && (
        <NewDiaryForm
          onCancel={() => setCurrentPage('main')}
          onSave={handleAddDiary}
        />
      )}
    </>
  );
}
