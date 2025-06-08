package com.example.demo.Service;

import com.example.demo.DTO.DiaryDTO;
import com.example.demo.entity.Diary;
import com.example.demo.entity.Project;
import com.example.demo.entity.Tag;
import com.example.demo.entity.User;
import com.example.demo.mapper.DiaryMapper;
import com.example.demo.repository.DiaryRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TagService tagService;
    private final DiaryTagService diaryTagService;

    // 현재 인증된 사용자 가져오기
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    public List<DiaryDTO.Response> getAllDiaries() {
        User currentUser = getCurrentUser();
        return diaryRepository.findByUser(currentUser).stream()
                .map(DiaryMapper::entityToDto)
                .toList();
    }

    public DiaryDTO.Response getDiaryById(Long did) {
        User currentUser = getCurrentUser();
        Diary diary = diaryRepository.findById(did)
                .orElseThrow(() -> new IllegalArgumentException("일기를 찾을 수 없습니다."));
        
        // 일기 소유자 검증
        if (!diary.getUser().equals(currentUser)) {
            throw new IllegalArgumentException("해당 일기에 접근할 권한이 없습니다.");
        }
        
        return DiaryMapper.entityToDto(diary);
    }

    public DiaryDTO.Response createDiary(DiaryDTO.Request request) {
        User currentUser = getCurrentUser();
        
        Project project = null;
        if (request.getProjectId() != null) {
            project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다."));
            
            // 프로젝트 소유자 검증
            if (!project.getUser().equals(currentUser)) {
                throw new IllegalArgumentException("해당 프로젝트에 접근할 권한이 없습니다.");
            }
        }        Diary diary = DiaryMapper.dtoToEntity(request, project);
        diary.setUser(currentUser); // 현재 사용자 설정
        diaryRepository.save(diary);

        // 태그 ID로 태그 연결
        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            List<Tag> tags = tagService.getTagsByIds(request.getTagIds(), currentUser);
            diaryTagService.connectTagsToDiary(diary, tags);
        }

        return DiaryMapper.entityToDto(diary);
    }

    public DiaryDTO.Response updateDiary(Long did, DiaryDTO.Request request) {
        User currentUser = getCurrentUser();
        Diary diary = diaryRepository.findById(did)
                .orElseThrow(() -> new IllegalArgumentException("일기를 찾을 수 없습니다."));

        // 일기 소유자 검증
        if (!diary.getUser().equals(currentUser)) {
            throw new IllegalArgumentException("해당 일기를 수정할 권한이 없습니다.");
        }

        Project project = null;
        if (request.getProjectId() != null) {
            project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다."));
            
            // 프로젝트 소유자 검증
            if (!project.getUser().equals(currentUser)) {
                throw new IllegalArgumentException("해당 프로젝트에 접근할 권한이 없습니다.");
            }
        }        diary.setDate(request.getDate());
        diary.setDevfeel(request.getDevfeel());
        diary.setDiff(request.getDiff());
        diary.setError(request.getError());
        diary.setExplaination(request.getExplaination());
        diary.setProject(project);        // 기존 태그 연결 초기화 후 새로 연결
        diary.getDiaryTags().clear();
        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            List<Tag> tags = tagService.getTagsByIds(request.getTagIds(), currentUser);
            diaryTagService.connectTagsToDiary(diary, tags);
        }

        return DiaryMapper.entityToDto(diary);
    }

    public void deleteDiary(Long did) {
        User currentUser = getCurrentUser();
        Diary diary = diaryRepository.findById(did)
                .orElseThrow(() -> new IllegalArgumentException("일기를 찾을 수 없습니다."));
        
        // 일기 소유자 검증
        if (!diary.getUser().equals(currentUser)) {
            throw new IllegalArgumentException("해당 일기를 삭제할 권한이 없습니다.");
        }
        
        diaryRepository.deleteById(did);
    }

    // 고급 조회 메서드들
    public List<DiaryDTO.Response> getDiariesByProject(Long projectId) {
        User currentUser = getCurrentUser();
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다."));
        
        // 프로젝트 소유자 검증
        if (!project.getUser().equals(currentUser)) {
            throw new IllegalArgumentException("해당 프로젝트에 접근할 권한이 없습니다.");
        }
        
        return diaryRepository.findByUserAndProject(currentUser, project).stream()
                .map(DiaryMapper::entityToDto)
                .toList();
    }

    public List<DiaryDTO.Response> getDiariesByDateRange(LocalDate startDate, LocalDate endDate) {
        User currentUser = getCurrentUser();
        return diaryRepository.findByUserAndDateBetween(currentUser, startDate, endDate).stream()
                .map(DiaryMapper::entityToDto)
                .toList();
    }
}
