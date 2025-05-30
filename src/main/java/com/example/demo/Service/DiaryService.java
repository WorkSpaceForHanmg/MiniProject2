package com.example.demo.Service;

import com.example.demo.DTO.DiaryDTO;
import com.example.demo.entity.Diary;
import com.example.demo.entity.Project;
import com.example.demo.entity.Tag;
import com.example.demo.mapper.DiaryMapper;
import com.example.demo.repository.DiaryRepository;
import com.example.demo.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final ProjectRepository projectRepository;
    private final TagService tagService;
    private final DiaryTagService diaryTagService;

    public List<DiaryDTO.Response> getAllDiaries() {
        return diaryRepository.findAll().stream()
                .map(DiaryMapper::entityToDto)
                .toList();
    }

    public DiaryDTO.Response getDiaryById(Long did) {
        Diary diary = diaryRepository.findById(did)
                .orElseThrow(() -> new IllegalArgumentException("일기를 찾을 수 없습니다."));
        return DiaryMapper.entityToDto(diary);
    }

    public DiaryDTO.Response createDiary(DiaryDTO.Request request) {
        Project project = null;
        if (request.getProjectId() != null) {
            project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다."));
        }

        Diary diary = DiaryMapper.dtoToEntity(request, project);
        diaryRepository.save(diary);

        if (request.getTags() != null && !request.getTags().isEmpty()) {
            List<Tag> tags = tagService.getOrCreateTagsByName(request.getTags());
            diaryTagService.connectTagsToDiary(diary, tags);
        }

        return DiaryMapper.entityToDto(diary);
    }

    public DiaryDTO.Response updateDiary(Long did, DiaryDTO.Request request) {
        Diary diary = diaryRepository.findById(did)
                .orElseThrow(() -> new IllegalArgumentException("일기를 찾을 수 없습니다."));

        Project project = null;
        if (request.getProjectId() != null) {
            project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다."));
        }

        diary.setDate(request.getDate());
        diary.setDevfeel(request.getTitle());
        diary.setDiff(request.getDiff());
        diary.setError(request.getError());
        diary.setExplaination(request.getContent());
        diary.setProject(project);

        // 기존 태그 연결 초기화 후 새로 연결
        diary.getDiaryTags().clear();
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            List<Tag> tags = tagService.getOrCreateTagsByName(request.getTags());
            diaryTagService.connectTagsToDiary(diary, tags);
        }

        return DiaryMapper.entityToDto(diary);
    }

    public void deleteDiary(Long did) {
        diaryRepository.deleteById(did);
    }
}
