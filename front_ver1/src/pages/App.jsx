import React from 'react';
import { DiaryProvider, useDiary } from '../context/DiaryContext';
import { Routes, Route } from 'react-router-dom';

import MainPage from './MainPage';
import DetailPage from './DetailPage';
import NewDiaryForm from './NewDiaryForm';
import ErrorNote from './ErrorNote';

function NewDiaryFormWrapper() {
  const { addDiary } = useDiary();

  const handleSave = (newDiary) => {
    addDiary(newDiary);
  };

  return <NewDiaryForm onSave={handleSave} />;
}

function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<MainPage />} />
      <Route path="/diary/:diaryId" element={<DetailPage />} />
      <Route path="/new" element={<NewDiaryFormWrapper />} />
      <Route path="/error-note" element={<ErrorNote />} />
    </Routes>
  );
}

function App() {
  return (
    <DiaryProvider>
      <AppRoutes />
    </DiaryProvider>
  );
}

export default App;
