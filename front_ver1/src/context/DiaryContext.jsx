import React, { createContext, useContext, useState } from 'react';

const DiaryContext = createContext();

const initialDiaries = [/* 기존 초기 데이터 */];

export function DiaryProvider({ children }) {
  const [diaries, setDiaries] = useState(initialDiaries);

  const addDiary = (newDiary) => {
    const newId = Date.now();
    setDiaries((prev) => [...prev, { ...newDiary, id: newId }]);
  };

  const updateDiary = (id, updatedDiary) => {
    setDiaries((prev) =>
      prev.map((d) => (d.id === id ? { ...d, ...updatedDiary } : d))
    );
  };

  const deleteDiary = (id) => {
    setDiaries((prev) => prev.filter((d) => d.id !== id));
  };

  return (
    <DiaryContext.Provider value={{ diaries, addDiary, updateDiary, deleteDiary }}>
      {children}
    </DiaryContext.Provider>
  );
}

export const useDiary = () => useContext(DiaryContext);
