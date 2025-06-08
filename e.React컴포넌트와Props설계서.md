# React 컴포넌트 설계 문서

## 문서 정보
- **작성자**: AI Assistant
- **작성일**: 2025-06-08
- **버전**: v1.0
- **검토자**: 검토 대기

---

## 1. MainPage 컴포넌트

### 컴포넌트명
**MainPage** - 메인 대시보드 페이지

### 목적 및 역할
- **주요 목적**: 개발 일기 목록을 표시하고 필터링 기능을 제공하는 메인 페이지

### 기능 요구사항
- [x] 일기 목록 표시 및 필터링 (프로젝트, 태그, 날짜별)
- [x] 일기 상세 보기 네비게이션
- [x] 새 일기 작성 네비게이션
- [x] 오답노트 페이지 네비게이션

### 컴포넌트 분류
- **타입**: [x] 페이지 컴포넌트
- **복잡도**: [x] 보통 (50-100줄)
- **상태 관리**: [x] 로컬 상태만

### Props 인터페이스
| Props명 | 타입 | 필수여부 | 기본값 | 설명 |
|---------|------|----------|--------|------|
| diaries | array | 필수 | - | 일기 데이터 배열 |

### 로컬 상태 (useState)
| 상태명 | 타입 | 초기값 | 용도 |
|--------|------|--------|------|
| selectedProject | string | "" | 프로젝트 필터 |
| selectedTag | string | "" | 태그 필터 |
| selectedDate | string | "" | 날짜 필터 |

---

## 2. DetailPage 컴포넌트

### 컴포넌트명
**DetailPage** - 일기 상세 조회 및 편집 페이지

### 목적 및 역할
- **주요 목적**: 선택된 일기의 상세 내용을 표시하고 편집/삭제 기능을 제공

### 기능 요구사항
- [x] 일기 상세 내용 표시
- [x] 일기 편집 모드 전환
- [x] 일기 내용 수정 및 저장
- [x] 일기 삭제 기능
- [x] 사이드바에서 다른 일기 선택
- [x] 프로젝트/태그 필터링

### 컴포넌트 분류
- **타입**: [x] 페이지 컴포넌트
- **복잡도**: [x] 복합 (100줄 이상)
- **상태 관리**: [x] 로컬 상태만

### Props 인터페이스
| Props명 | 타입 | 필수여부 | 기본값 | 설명 |
|---------|------|----------|--------|------|
| diaries | array | 필수 | - | 일기 데이터 배열 |
| onUpdateDiary | function | 필수 | - | 일기 수정 콜백 함수 |
| onDeleteDiary | function | 필수 | - | 일기 삭제 콜백 함수 |

### 로컬 상태 (useState)
| 상태명 | 타입 | 초기값 | 용도 |
|--------|------|--------|------|
| projectFilter | string | "" | 프로젝트 필터 |
| tagFilter | string | "" | 태그 필터 |
| isEditing | boolean | false | 편집 모드 상태 |
| editForm | object | {} | 편집 폼 데이터 |

---

## 3. NewDiaryForm 컴포넌트

### 컴포넌트명
**NewDiaryForm** - 새 일기 작성 폼

### 목적 및 역할
- **주요 목적**: 새로운 개발 일기를 작성하고 저장하는 폼 제공

### 기능 요구사항
- [x] 일기 기본 정보 입력 (날짜, 프로젝트, 태그)
- [x] 개발 내용 입력 (코드, 소감, 어려웠던 점)
- [x] 에러 정보 입력 (요약, 태그, 해결방법)
- [x] 프로젝트/태그 자동완성
- [x] 폼 유효성 검사

### 컴포넌트 분류
- **타입**: [x] 페이지 컴포넌트
- **복잡도**: [x] 복합 (100줄 이상)
- **상태 관리**: [x] 로컬 상태만

### Props 인터페이스
| Props명 | 타입 | 필수여부 | 기본값 | 설명 |
|---------|------|----------|--------|------|
| onSave | function | 필수 | - | 일기 저장 콜백 함수 |
| loading | boolean | 선택 | false | 로딩 상태 |

### 로컬 상태 (useState)
| 상태명 | 타입 | 초기값 | 용도 |
|--------|------|--------|------|
| date | string | "" | 일기 날짜 |
| project | string | "" | 프로젝트명 |
| tags | string | "" | 태그 문자열 |
| code | string | "" | 코드 내용 |
| devReview | string | "" | 개발 소감 |
| challenges | string | "" | 어려웠던 점 |
| errorSummary | string | "" | 에러 요약 |
| errorTags | string | "" | 에러 태그 |
| errorSolution | string | "" | 에러 해결방법 |
| projects | array | [] | 프로젝트 목록 |
| availableTags | array | [] | 사용 가능한 태그 목록 |
| formLoading | boolean | false | 폼 데이터 로딩 상태 |

---

## 4. ErrorNote 컴포넌트

### 컴포넌트명
**ErrorNote** - 에러 오답노트 페이지

### 목적 및 역할
- **주요 목적**: 일기에서 기록된 에러들을 모아서 오답노트 형태로 제공

### 기능 요구사항
- [x] 에러가 있는 일기 목록 표시
- [x] 에러 태그별 필터링
- [x] 에러 해결방법 표시
- [x] 원본 일기로 이동 링크

### 컴포넌트 분류
- **타입**: [x] 페이지 컴포넌트
- **복잡도**: [x] 보통 (50-100줄)
- **상태 관리**: [x] 로컬 상태만

### Props 인터페이스
| Props명 | 타입 | 필수여부 | 기본값 | 설명 |
|---------|------|----------|--------|------|
| diaries | array | 필수 | - | 일기 데이터 배열 |

### 로컬 상태 (useState)
| 상태명 | 타입 | 초기값 | 용도 |
|--------|------|--------|------|
| selectedTag | string | "" | 선택된 에러 태그 |

---

## 5. ProjectForm 컴포넌트

### 컴포넌트명
**ProjectForm** - 프로젝트 폼 (레거시)

### 목적 및 역할
- **주요 목적**: 구 버전의 일기 작성 폼 (현재 미사용)

### 기능 요구사항
- [x] 기본적인 일기 작성 기능
- [x] 파일 업로드 기능

### 컴포넌트 분류
- **타입**: [x] 기능 컴포넌트
- **복잡도**: [x] 보통 (50-100줄)
- **상태 관리**: [x] 로컬 상태만

---

## 파일 구조

```
src/
├── api/
│   ├── apiClient.js
│   └── diaryApi.js         # API 서비스
├── components/
│   ├── DetailPage.jsx      # 상세 페이지         
│   ├── ErrorNote.jsx       # 오답노트 페이지
│   ├── MainPage.jsx        # 메인 페이지
│   ├── NewDiaryForm.jsx    # 새 일기 작성 폼
│   └── ProjectForm.jsx     # 레거시 폼
├── styles/
│   ├── App.css              
│   ├── DetailPage.module.css            
│   ├── ErrorNote.module.css
│   ├── index.css
│   ├── MainPage.css
│   ├── MainPage.module.css
│   ├── NewDiaryForm.module.css
│   └── ProjectForm.css
├── App.jsx
└── main.jsx
```

## API 연동

### 주요 API 엔드포인트
| API 엔드포인트 | 메서드 | 용도 | 호출 시점 |
|----------------|--------|------|-----------|
| /api/diaries | GET | 일기 목록 조회 | 페이지 로드 |
| /api/diaries | POST | 새 일기 생성 | 일기 저장 |
| /api/diaries/:id | PUT | 일기 수정 | 일기 편집 |
| /api/diaries/:id | DELETE | 일기 삭제 | 삭제 버튼 |
| /api/projects | GET | 프로젝트 목록 | 폼 로드 |
| /api/tags | GET | 태그 목록 | 폼 로드 |

## 데이터 구조

### Diary 객체 구조
```javascript
{
  id: number,
  date: string,
  project: string,
  tags: string[],
  summary: string,
  content: string // JSON 문자열
}
```

### Content JSON 구조
```javascript
{
  codeExplanation: string,
  devReview: string,
  challenges: string,
  errorSummary: string,
  errorTags: string[],
  errorSolution: string
}
```

## 성능 최적화

### 적용된 최적화
- [x] useMemo를 통한 필터링 결과 캐싱
- [x] useEffect를 통한 사이드 이펙트 관리
- [x] JSON 파싱 결과 메모이제이션

### 권장 추가 최적화
- [ ] React.memo로 컴포넌트 메모이제이션
- [ ] useCallback으로 함수 메모이제이션
- [ ] 가상화를 통한 긴 목록 최적화

## 외부 의존성

### 라이브러리
- **react-router-dom**: 페이지 라우팅
- **React**: 핵심 라이브러리

### API 서비스
- **diaryApi**: 일기 CRUD 및 메타데이터 API

### 유틸리티
- **JSON.parse/stringify**: 컨텐츠 데이터 처리
- **Array.filter/map**: 데이터 조작

## 컴포넌트 간 관계도

```
App
├── MainPage
│   └── props: { diaries }
├── DetailPage
│   └── props: { diaries, onUpdateDiary, onDeleteDiary }
├── NewDiaryForm
│   └── props: { onSave, loading }
├── ErrorNote
│   └── props: { diaries }
└── ProjectForm (레거시)
```

## 주요 기술적 특징

1. **모듈화된 CSS**: CSS Modules 사용으로 스타일 충돌 방지
2. **JSON 기반 컨텐츠**: 구조화된 일기 내용을 JSON으로 저장
3. **실시간 필터링**: useMemo를 활용한 효율적인 필터링
4. **라우터 연동**: React Router를 통한 SPA 네비게이션
5. **에러 처리**: try-catch를 통한 안전한 JSON 파싱
