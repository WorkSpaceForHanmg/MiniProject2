package com.basic.myspringbootapp.mapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.basic.myspringbootapp.dto.DiaryDTO;
import com.basic.myspringbootapp.dto.ProjectDTO;
import com.basic.myspringbootapp.dto.TagDTO;
import com.basic.myspringbootapp.entity.Diary;
import com.basic.myspringbootapp.entity.Project;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DiaryMapper {

    // DTO → Entity 변환
    public static Diary dtoToEntity(DiaryDTO.Request dto, Project project) {
        if (dto == null || project == null) return null;

        return Diary.builder()
                .date(dto.getDate())
                .devfeel(dto.getTitle())  // title → devfeel
                .diff(dto.getDiff())
                .error(dto.getError())
                .explaination(dto.getContent())
                .project(project)
                .build();
    }

    // Entity → DTO 변환
    public static DiaryDTO.Response entityToDto(Diary entity) {
        if (entity == null) return null;

        ProjectDTO.SimpleResponse projectDto = ProjectDTO.SimpleResponse.builder()
                .pid(entity.getProject().getPid())
                .name(entity.getProject().getName())
                .build();

        // content 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        String content = entity.getExplaination();

        String codeExplanation = null;
        String devReview = null;
        String challenges = null;
        String errorSummary = null;
        List<String> errorTags = null;
        String errorSolution = null;
        try {
            Map<String, Object> parsed = objectMapper.readValue(content, new TypeReference<Map<String, Object>>() {
            });
            codeExplanation = (String) parsed.get("codeExplanation");
            devReview = (String) parsed.get("devReview");
            challenges = (String) parsed.get("challenges");
            errorSummary = (String) parsed.get("errorSummary");
            errorSolution = (String) parsed.get("errorSolution");
            Object rawErrorTags = parsed.get("errorTags");
            if (rawErrorTags != null) {
                errorTags = objectMapper.convertValue(rawErrorTags, new TypeReference<>() {});
            }
        } catch (Exception e) {
            System.err.println("⚠ content 파싱 실패: " + e.getMessage());
        }

        return DiaryDTO.Response.builder()
                .did(entity.getDid())
                .date(entity.getDate())
                .title(entity.getDevfeel())
                .diff(entity.getDiff())
                .error(entity.getError())
                .content(content)
                .project(projectDto)
                .codeExplanation(codeExplanation)
                .devReview(devReview)
                .challenges(challenges)
                .errorSummary(errorSummary)
                .errorTags(errorTags)
                .errorSolution(errorSolution)
                .build();
    }

    // Entity → 간단 DTO 변환
    public static DiaryDTO.SimpleResponse entityToSimpleDto(Diary entity) {
        if (entity == null) return null;
        List<TagDTO.SimpleResponse> tags =
                entity.getDiaryTags() != null
                        ? entity.getDiaryTags().stream()
                        .map(diaryTag -> TagDTO.SimpleResponse.builder()
                                .tid(diaryTag.getTag().getTid())
                                .name(diaryTag.getTag().getName())
                                .build())
                        .collect(Collectors.toList())
                        : List.of();

        return DiaryDTO.SimpleResponse.builder()
                .did(entity.getDid())
                .date(entity.getDate())
                .title(entity.getDevfeel())
                .tags(tags)
                .build();
    }
}
