import React, { useState } from "react";
import "../styles/MainPage.css";

export default function MyDiary() {
  const [selectedDate, setSelectedDate] = useState("");
  const [project, setProject] = useState("");
  const [tag, setTag] = useState("");

  return (
    <div className="page-container">
      <h1 className="title">My Diary ✏️</h1>

      <div className="diary-container">
        {/* 왼쪽 필터 및 캘린더 영역 */}
        <div className="filter-calendar">
          <div className="filter-row">
            <select value={project} onChange={(e) => setProject(e.target.value)}>
              <option value="">프로젝트명</option>
              <option value="프로젝트1">프로젝트1</option>
            </select>

            <select value={tag} onChange={(e) => setTag(e.target.value)}>
              <option value="">태그</option>
              <option value="Tag1">Tag1</option>
              <option value="Tag2">Tag2</option>
            </select>
          </div>

          <div className="calendar-section">
            <input
              type="date"
              value={selectedDate}
              onChange={(e) => setSelectedDate(e.target.value)}
            />
          </div>
        </div>

        <div className="diary-list">
          <button className="new-entry-button">새 일기 작성</button>
        </div>
      </div>
    </div>
  );
}
