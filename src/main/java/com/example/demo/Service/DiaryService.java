package com.example.demo.Service;

import com.example.demo.DTO.DiaryDTO;
import com.example.demo.entity.Diary;
import com.example.demo.entity.Project;
import com.example.demo.mapper.DiaryMapper;
import com.example.demo.repository.DiaryRepository;
import com.example.demo.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final ProjectRepository projectRepository;

    //전체 일기 조회
    public List<DiaryDTO.Response> getAllDiaries() {
        return diaryRepository.findAll().stream()
                .map(DiaryMapper::entityToDto)
                .toList();
    }

    //ID로 일기 조회
    public DiaryDTO.Response getDiaryById(Long did) {
        Diary diary = diaryRepository.findById(did)
                .orElseThrow(() -> new IllegalArgumentException("일기를 찾을 수 없습니다."));
        return DiaryMapper.entityToDto(diary);
    }

    //새 일기 생성
    public DiaryDTO.Response createDiary(DiaryDTO.Request request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다."));

        Diary diary = DiaryMapper.dtoToEntity(request, project);
        Diary saved = diaryRepository.save(diary);
        return DiaryMapper.entityToDto(saved);
    }

    // 일기 수정
    public DiaryDTO.Response updateDiary(Long did, DiaryDTO.Request request) {
        Diary diary = diaryRepository.findById(did)
                .orElseThrow(() -> new IllegalArgumentException("일기를 찾을 수 없습니다."));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("프로젝트가 존재하지 않습니다."));

        diary.setDate(request.getDate());
        diary.setDevfeel(request.getTitle());
        diary.setDiff(request.getDiff());
        diary.setError(request.getError());
        diary.setExplaination(request.getContent());
        diary.setProject(project);

        Diary updated = diaryRepository.save(diary);
        return DiaryMapper.entityToDto(updated);
    }

    //일기 삭제
    public void deleteDiary(Long did) {
        diaryRepository.deleteById(did);
    }

    //프로젝트별 일기 조회
    public List<Diary> findDiariesByProject(Project project) {
        return diaryRepository.findByProject(project);
    }

    //날짜 범위로 일기 조회
    public List<Diary> findDiariesByDateRange(LocalDate startDate, LocalDate endDate) {
        return diaryRepository.findByDateBetween(startDate, endDate);
    }
}
//a