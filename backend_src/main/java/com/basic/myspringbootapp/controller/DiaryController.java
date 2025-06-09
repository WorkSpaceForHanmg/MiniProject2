package com.basic.myspringbootapp.controller;

import com.basic.myspringbootapp.dto.DiaryDTO;
import com.basic.myspringbootapp.entity.Diary;
import com.basic.myspringbootapp.entity.Project;
import com.basic.myspringbootapp.mapper.DiaryMapper;
import com.basic.myspringbootapp.service.DiaryService;
import com.basic.myspringbootapp.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;
    private final ProjectService projectService;

    // 전체 일기 조회
    @GetMapping
    public ResponseEntity<List<DiaryDTO.SimpleResponse>> getAllDiaries() {
        List<DiaryDTO.SimpleResponse> responseList = diaryService.findAllDiaries().stream()
                .map(DiaryMapper::entityToSimpleDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    // 단일 일기 조회
    @GetMapping("/{did}")
    public ResponseEntity<DiaryDTO.Response> getDiaryById(@PathVariable Long did) {
        Optional<Diary> diaryOpt = diaryService.findDiaryById(did);
        return diaryOpt.map(diary -> ResponseEntity.ok(DiaryMapper.entityToDto(diary)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 프로젝트별 일기 조회
    @GetMapping("/project/{pid}")
    public ResponseEntity<List<DiaryDTO.SimpleResponse>> getDiariesByProject(@PathVariable Long pid) {
        Project project = projectService.getProjectById(pid);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }

        List<DiaryDTO.SimpleResponse> responseList = diaryService.findDiariesByProject(project).stream()
                .map(DiaryMapper::entityToSimpleDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    // 날짜 범위로 일기 조회
    @GetMapping("/range")
    public ResponseEntity<List<DiaryDTO.SimpleResponse>> getDiariesByDateRange(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<DiaryDTO.SimpleResponse> responseList = diaryService.findDiariesByDateRange(startDate, endDate).stream()
                .map(DiaryMapper::entityToSimpleDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    // 일기 생성
    @PostMapping
    public ResponseEntity<DiaryDTO.Response> createDiary(@Valid @RequestBody DiaryDTO.Request request) {
        // ✅ 1. 프로젝트 이름으로 조회
        Project project = projectService.findByName(request.getProjectName());

        // ✅ 2. 없으면 새로 생성
        if (project == null) {
            project = new Project();
            project.setName(request.getProjectName());
            project = projectService.saveProject(project);
        }

        // ✅ 3. 일기 저장
        Diary diary = DiaryMapper.dtoToEntity(request, project);
        Diary savedDiary = diaryService.saveDiary(diary);
        DiaryDTO.Response response = DiaryMapper.entityToDto(savedDiary);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{did}")
    public ResponseEntity<DiaryDTO.Response> updateDiary(
            @PathVariable Long did,
            @Valid @RequestBody DiaryDTO.Request request) {

        Optional<Diary> optionalDiary = diaryService.findDiaryById(did);
        if (optionalDiary.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // ✅ 1. 프로젝트 이름 기반으로 조회
        Project project = projectService.findByName(request.getProjectName());

        // ✅ 2. 없으면 새로 생성
        if (project == null) {
            project = new Project();
            project.setName(request.getProjectName());
            project = projectService.saveProject(project);
        }

        // ✅ 3. 기존 일기 내용 수정
        Diary diary = optionalDiary.get();
        diary.setDate(request.getDate());
        diary.setDevfeel(request.getTitle());
        diary.setDiff(request.getDiff());
        diary.setError(request.getError());
        diary.setExplaination(request.getContent());
        diary.setProject(project);

        Diary updatedDiary = diaryService.saveDiary(diary);
        return ResponseEntity.ok(DiaryMapper.entityToDto(updatedDiary));
    }
    // 일기 삭제
    @DeleteMapping("/{did}")
    public ResponseEntity<Void> deleteDiary(@PathVariable Long did) {
        Optional<Diary> optionalDiary = diaryService.findDiaryById(did);
        if (optionalDiary.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        diaryService.deleteDiary(did);
        return ResponseEntity.noContent().build();
    }

}
