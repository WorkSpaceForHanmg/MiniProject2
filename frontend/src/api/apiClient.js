// api/apiClient.js
import axios from 'axios';

// API 베이스 URL 설정 (환경변수 또는 기본값 사용)
const BASE_URL = '/api'; // process.env.REACT_APP_API_URL ||

// axios 인스턴스 생성
const apiClient = axios.create({
  baseURL: BASE_URL,
  timeout: 10000, // 10초 타임아웃
  headers: {
    'Content-Type': 'application/json',
  },
});

// 요청 인터셉터 (토큰 등 인증 정보 추가)
apiClient.interceptors.request.use(
  (config) => {
    // 로컬스토리지에서 토큰 가져오기 (필요시)
    const token = localStorage.getItem('authToken');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 응답 인터셉터 (에러 처리)
apiClient.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response?.status === 401) {
      // 인증 실패 시 처리
      localStorage.removeItem('authToken');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default apiClient;