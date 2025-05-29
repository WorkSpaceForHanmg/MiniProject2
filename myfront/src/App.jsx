import React from "react";
import ProjectForm from "./components/ProjectForm";
import MainPage from "./components/MainPage"

// const dummyDiaries = [
//   {
//     id: 1,
//     date: '2025-05-26',
//     project: '개발 일기 웹앱',
//     tags: ['React', 'Spring'],
//     summary: '오늘은 일기 폼을 완성함...',
//     content: '폼 구성하고 상태 관리 마무리함.',
//   },
//   {
//     id: 2,
//     date: '2025-05-25',
//     project: '개발 일기 웹앱',
//     tags: ['React'],
//     summary: '기초 구조 설계',
//     content: '프로젝트 구조를 설정하고 폴더 나눔.',
//   },
// ];

function App() {
  return (
    <div style={{ minHeight: "100vh", display: "flex", alignItems: "center", justifyContent: "center" }}>
      <MainPage />
    </div>
  );
}

export default App;

