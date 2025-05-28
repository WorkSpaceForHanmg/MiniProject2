package com.basic.myspringbootapp.service;

import com.basic.myspringbootapp.entity.Diary;
import com.basic.myspringbootapp.entity.Project;
import com.basic.myspringbootapp.repository.DiaryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    // 전체 일기 조회
    public List<Diary> findAllDiaries() {
        return diaryRepository.findAll();
    }

    // ID로 일기 조회
    public Optional<Diary> findDiaryById(Long did) {
        return diaryRepository.findById(did);
    }

    // 프로젝트별 일기 조회
    public List<Diary> findDiariesByProject(Project project) {
        return diaryRepository.findByProject(project);
    }

    // 날짜 범위 내 일기 조회
    public List<Diary> findDiariesByDateRange(LocalDate startDate, LocalDate endDate) {
        return diaryRepository.findByDateBetween(startDate, endDate);
    }

    // 새 일기 저장
    public Diary saveDiary(Diary diary) {
        return diaryRepository.save(diary);
    }

    // 일기 삭제
    public void deleteDiary(Long did) {
        diaryRepository.deleteById(did);
    }
}
