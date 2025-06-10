import React, { createContext, useContext, useState, useMemo } from 'react';

const DiaryContext = createContext();

const initialDiaries = [
  // 기존 초기 데이터 예시
];

export function DiaryProvider({ children }) {
  const [diaries, setDiaries] = useState(initialDiaries);

  // diaries가 바뀔 때마다 모든 태그를 중복 없이 추출
  const tags = useMemo(() => {
    const tagSet = new Set();
    diaries.forEach(diary => {
      if (diary.tags && Array.isArray(diary.tags)) {
        diary.tags.forEach(tag => tagSet.add(tag));
      }
    });
    return Array.from(tagSet);
  }, [diaries]);

  const addDiary = (newDiary) => {
    const newId = Date.now();
    setDiaries((prev) => [...prev, { ...newDiary, id: newId }]);
  };

  const updateDiary = (id, updatedData) => {
    setDiaries((prev) =>
      prev.map(diary => (diary.id === id ? { ...diary, ...updatedData } : diary))
    );
  };

  const deleteDiary = (id) => {
    setDiaries((prev) => prev.filter(diary => diary.id !== id));
  };

  return (
    <DiaryContext.Provider value={{ diaries, tags, addDiary, updateDiary, deleteDiary }}>
      {children}
    </DiaryContext.Provider>
  );
}

export const useDiary = () => useContext(DiaryContext);
