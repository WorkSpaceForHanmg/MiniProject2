package com.example.demo.mapper;

import com.example.demo.DTO.DiaryDTO;
import com.example.demo.entity.Diary;
import com.example.demo.entity.Project;
import com.example.demo.entity.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DiaryMapper {

    public static Diary dtoToEntity(DiaryDTO.Request dto, Project project) {
        return Diary.builder()
                .date(dto.getDate())
                .devfeel(dto.getTitle())
                .diff(dto.getDiff())
                .error(dto.getError())
                .explaination(dto.getContent())
                .project(project)
                .diaryTags(new ArrayList<>())
                .build();
    }

    public static DiaryDTO.Response entityToDto(Diary diary) {
        Project project = diary.getProject();

        return DiaryDTO.Response.builder()
                .did(diary.getDid())
                .date(diary.getDate().toString())
                .title(diary.getDevfeel())
                .diff(diary.getDiff())
                .error(diary.getError())
                .content(diary.getExplaination())
                .projectId(project != null ? project.getPid() : null)
                .projectName(project != null ? project.getName() : null)
                .tags(diary.getTags() != null
                        ? diary.getTags().stream()
                        .map(Tag::getName)
                        .collect(Collectors.toList())
                        : List.of()
                )
                .build();
    }
}
