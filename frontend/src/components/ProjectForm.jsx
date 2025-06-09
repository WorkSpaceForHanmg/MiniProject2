import React, { useState } from "react";
import "../styles/ProjectForm.css";

export default function ProjectForm() {
  const [formData, setFormData] = useState({
    date: "",
    projectName: "",
    tag: "",
    summary: "",
    challenges: "",
    solution: "",
    file: null,
    codeExplanation: "",
  });

  const handleChange = (e) => {
    const { name, value, files } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: files ? files[0] : value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    alert('새 일기 저장 완료 (로직 구현 필요)');
    console.log("폼 데이터:", formData);

  };

  return (
    <>
        <form onSubmit={handleSubmit} className="form-container">
        <div className="form-row">
            <input type="date" name="date" value={formData.date} onChange={handleChange} />
            <input type="text" name="projectName" placeholder="프로젝트명" value={formData.projectName} onChange={handleChange} />
            <select name="tag" value={formData.tag} onChange={handleChange}>
            <option value="">태그</option>
            <option value="frontend">Frontend</option>
            <option value="backend">Backend</option>
            <option value="fullstack">Fullstack</option>
            </select>
        </div>

        <input type="text" name="summary" className="form-input" placeholder="개발 소감" value={formData.summary} onChange={handleChange} />
        <input type="text" name="challenges" className="form-input" placeholder="어려웠던 점" value={formData.challenges} onChange={handleChange} />
        <input type="text" name="solution" className="form-input" placeholder="에러 발생 및 해결" value={formData.solution} onChange={handleChange} />

        <div className="upload-box">
            <label>
            <input type="file" name="file" onChange={handleChange} style={{ display: "none" }} />
            Drag files here or <span>choose from folder</span>
            </label>
        </div>

        <input type="text" name="codeExplanation" className="form-input" placeholder="간단한 코드 설명" value={formData.codeExplanation} onChange={handleChange} />

        <button type="submit" className="submit-button">저장</button>
        </form>

        {/* ✅ 뒤로가기 버튼 */}
        <button className="back-button" onClick={() => window.history.back()}>
            ← 뒤로가기
        </button>
    </>


  );
}
