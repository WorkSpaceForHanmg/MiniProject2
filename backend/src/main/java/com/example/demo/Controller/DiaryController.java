package com.example.demo.Controller;

import com.example.demo.DTO.DiaryDTO;
import com.example.demo.Service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    // 전체 조회
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
//a