package com.basic.myspringbootapp.mapper;

import com.basic.myspringbootapp.controller.dto.DiaryDTO;
import com.basic.myspringbootapp.controller.dto.ProjectDTO;
import com.basic.myspringbootapp.domain.Diary;
import com.basic.myspringbootapp.domain.Project;

public class DiaryMapper {

    // DTO → Entity 변환
    public static Diary dtoToEntity(DiaryDTO.Request dto, Project project) {
        if (dto == null || project == null) return null;

        return Diary.builder()
                .date(dto.getDate())
                .devfeel(dto.getTitle())  // title → devfeel
                .diff(dto.getDiff())
                .error(dto.getError())
                .explain(dto.getContent())
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

        return DiaryDTO.Response.builder()
                .did(entity.getDid())
                .date(entity.getDate())
                .title(entity.getDevfeel())
                .diff(entity.getDiff())
                .error(entity.getError())
                .content(entity.getExplain())
                .project(projectDto)
                .build();
    }

    // Entity → 간단 DTO 변환
    public static DiaryDTO.SimpleResponse entityToSimpleDto(Diary entity) {
        if (entity == null) return null;

        return DiaryDTO.SimpleResponse.builder()
                .did(entity.getDid())
                .date(entity.getDate())
                .title(entity.getDevfeel())
                .build();
    }
}
