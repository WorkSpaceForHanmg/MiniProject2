// api/diaryApi.js
import apiClient from './apiClient';

export const diaryApi = {
  // 모든 일기 조회
  getAllDiaries: async () => {
    try {
      const response = await apiClient.get('/diaries');
      return response.data;
    } catch (error) {
      console.error('일기 목록 조회 실패:', error);
      throw error;
    }
  },

  // 특정 일기 조회
  getDiaryById: async (id) => {
    try {
      const response = await apiClient.get(`/diaries/${id}`);
      return response.data;
    } catch (error) {
      console.error('일기 조회 실패:', error);
      throw error;
    }
  },

  // 새 일기 생성
  createDiary: async (diaryData) => {
    try {
      const response = await apiClient.post('/diaries', diaryData);
      return response.data;
    } catch (error) {
      console.error('일기 생성 실패:', error);
      throw error;
    }
  },

  // 일기 수정
  updateDiary: async (id, diaryData) => {
    try {
      const response = await apiClient.put(`/diaries/${id}`, diaryData);
      return response.data;
    } catch (error) {
      console.error('일기 수정 실패:', error);
      throw error;
    }
  },

  // 일기 삭제
  deleteDiary: async (id) => {
    try {
      await apiClient.delete(`/diaries/${id}`);
      return true;
    } catch (error) {
      console.error('일기 삭제 실패:', error);
      throw error;
    }
  },

  // 프로젝트 목록 조회
  getProjects: async () => {
    try {
      const response = await apiClient.get('/projects');
      return response.data;
    } catch (error) {
      console.error('프로젝트 목록 조회 실패:', error);
      throw error;
    }
  },

  // 태그 목록 조회
  getTags: async () => {
    try {
      const response = await apiClient.get('/tags');
      return response.data;
    } catch (error) {
      console.error('태그 목록 조회 실패:', error);
      throw error;
    }
  },
};