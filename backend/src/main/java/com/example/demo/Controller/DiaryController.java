package com.example.demo.Controller;

import com.example.demo.DTO.DiaryDTO;
import com.example.demo.Service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    // 전체 조회 (현재 사용자의 일기만)
    @GetMapping
    public ResponseEntity<List<DiaryDTO.Response>> getAllDiaries() {
        List<DiaryDTO.Response> diaries = diaryService.getAllDiaries();
        return ResponseEntity.ok(diaries);
    }

    // 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<DiaryDTO.Response> getDiary(@PathVariable Long id) {
        DiaryDTO.Response diary = diaryService.getDiaryById(id);
        return ResponseEntity.ok(diary);
    }

    // 프로젝트별 일기 조회
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<DiaryDTO.Response>> getDiariesByProject(@PathVariable Long projectId) {
        List<DiaryDTO.Response> diaries = diaryService.getDiariesByProject(projectId);
        return ResponseEntity.ok(diaries);
    }

    // 날짜 범위별 일기 조회
    @GetMapping("/range")
    public ResponseEntity<List<DiaryDTO.Response>> getDiariesByDateRange(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<DiaryDTO.Response> diaries = diaryService.getDiariesByDateRange(startDate, endDate);
        return ResponseEntity.ok(diaries);
    }

    // 생성
    @PostMapping
    public ResponseEntity<DiaryDTO.Response> createDiary(@RequestBody DiaryDTO.Request request) {
        DiaryDTO.Response created = diaryService.createDiary(request);
        return ResponseEntity.ok(created);
    }

    // 수정
    @PatchMapping("/{id}")
    public ResponseEntity<DiaryDTO.Response> updateDiary(@PathVariable Long id, @RequestBody DiaryDTO.Request request) {
        DiaryDTO.Response updated = diaryService.updateDiary(id, request);
        return ResponseEntity.ok(updated);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiary(@PathVariable Long id) {
        diaryService.deleteDiary(id);
        return ResponseEntity.noContent().build();
    }
}