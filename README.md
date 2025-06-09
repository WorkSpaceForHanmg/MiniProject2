# 개발 일기 웹앱, Devry 📝

개발자를 위한 일기 및 오답노트 관리 시스템입니다. 개발 과정에서 발생한 코드, 경험, 에러 해결 과정을 체계적으로 기록하고 관리할 수 있습니다.

Devry = Dev(elopment) + (Dia)ry

## 🚀 주요 기능

- **일기 작성**: 개발 과정, 코드 설명, 소감을 상세히 기록
- **오답노트**: 발생한 에러와 해결 방법을 태그별로 분류하여 관리
- **검색 및 필터링**: 프로젝트, 태그, 날짜별로 일기 검색
- **상세 보기**: 일기 내용을 자세히 보고 수정/삭제 가능
- **반응형 디자인**: 모바일과 데스크톱 모두 지원

## 🛠️ 기술 스택

- **Frontend**: React 18, React Router v6
- **상태 관리**: React Hooks (useState, useEffect, useMemo)
- **HTTP 클라이언트**: Axios
- **스타일링**: CSS Modules
- **빌드 도구**: Create React App

## 📁 프로젝트 구조

```
src/
├── api/
│   ├── apiClient.js          # Axios 클라이언트 설정
│   └── diaryApi.js           # 일기 관련 API 함수들
├── components/
│   ├── App.jsx               # 메인 앱 컴포넌트
│   ├── MainPage.jsx          # 메인 페이지 (일기 목록)
│   ├── DetailPage.jsx        # 일기 상세 보기/수정
│   ├── NewDiaryForm.jsx      # 새 일기 작성 폼
│   └── ErrorNote.jsx         # 오답노트 페이지
├── styles/
│   ├── MainPage.module.css
│   ├── DetailPage.module.css
│   ├── NewDiaryForm.module.css
│   └── ErrorNote.module.css
└── main.jsx                  # React 앱 진입점
```

## 🏃‍♂️ 설치 및 실행

### 1. 저장소 클론
```bash
git clone <repository-url>
cd dev-diary-app
```

### 2. 의존성 설치
```bash
npm install
```

### 3. 환경변수 설정
프로젝트 루트에 `.env` 파일 생성:
```env
REACT_APP_API_URL=http://localhost:8080/api
```

### 4. 개발 서버 실행
```bash
npm start
```

브라우저에서 `http://localhost:3000`으로 접속

### 5. 빌드 (배포용)
```bash
npm run build
```

## 📱 페이지 구성

### 1. 메인 페이지 (`/`)
- 전체 일기 목록 표시
- 프로젝트, 태그, 날짜별 필터링
- 새 일기 작성 버튼
- 오답노트 바로가기 버튼

### 2. 일기 상세 페이지 (`/diary/:id`)
- 선택된 일기의 상세 내용 표시
- 좌측: 일기 목록 (필터링 가능)
- 우측: 상세 내용 및 수정/삭제 기능

### 3. 새 일기 작성 (`/new`)
- 일기 작성 폼
- 프로젝트, 태그, 코드, 소감, 에러 해결 등 입력

### 4. 오답노트 (`/error-note`)
- 에러가 포함된 일기만 표시
- 태그별 필터링
- 에러 해결 과정 요약

## 🔧 주요 컴포넌트

### App.jsx
- 라우팅 설정
- 전역 상태 관리 (일기 데이터)
- API 통신 및 에러 처리

### MainPage.jsx
- 일기 목록 표시
- 필터링 기능
- 네비게이션

### DetailPage.jsx
- 일기 상세 보기
- 인라인 편집 기능
- 좌측 사이드바 (일기 목록)

### NewDiaryForm.jsx
- 새 일기 작성 폼
- 유효성 검사
- 동적 프로젝트/태그 로드

### ErrorNote.jsx
- 에러 전용 목록
- 태그별 필터링
- 에러 해결 과정 요약

## 🌐 API 연동

백엔드 API와의 통신을 위해 다음 엔드포인트를 사용합니다:

- `GET /api/diaries` - 모든 일기 조회
- `POST /api/diaries` - 새 일기 생성
- `GET /api/diaries/:id` - 특정 일기 조회
- `PUT /api/diaries/:id` - 일기 수정
- `DELETE /api/diaries/:id` - 일기 삭제
- `GET /api/projects` - 프로젝트 목록 조회
- `GET /api/tags` - 태그 목록 조회

## 📊 데이터 구조

### 일기 객체
```javascript
{
  id: number,
  date: "YYYY-MM-DD",
  project: "프로젝트명",
  tags: ["React", "JavaScript"],
  summary: "간단한 요약",
  content: JSON.stringify({
    codeExplanation: "코드 설명",
    devReview: "개발 소감",
    challenges: "어려웠던 점",
    errorSummary: "에러 요약",
    errorTags: ["에러", "태그"],
    errorSolution: "해결 방법"
  })
}
```

## 🚀 배포

### Vercel 배포
```bash
npm install -g vercel
vercel --prod
```

### Netlify 배포
```bash
npm run build
# dist 폴더를 Netlify에 드래그 앤 드롭
```

## 🔍 브라우저 지원

- Chrome (최신 2개 버전)
- Firefox (최신 2개 버전)
- Safari (최신 2개 버전)
- Edge (최신 2개 버전)

## 📝 라이센스

MIT License

## 🤝 기여하기

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

