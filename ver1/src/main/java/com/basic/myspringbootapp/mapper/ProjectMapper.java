package com.basic.myspringbootapp.mapper;

import com.basic.myspringbootapp.dto.ProjectDTO;
import com.basic.myspringbootapp.entity.Project;

public class ProjectMapper {

    public static Project dtoToEntity(ProjectDTO.Request dto) {
        if (dto == null) return null;

        return Project.builder()
                .name(dto.getName())
                .build();
    }

    public static ProjectDTO.Response entityToDto(Project entity) {
        if (entity == null) return null;

        return ProjectDTO.Response.builder()
                .pid(entity.getPid())
                .name(entity.getName())
                .build();
    }

    public static ProjectDTO.SimpleResponse entityToSimpleDto(Project entity) {
        if (entity == null) return null;

        return ProjectDTO.SimpleResponse.builder()
                .pid(entity.getPid())
                .name(entity.getName())
                .build();
    }
}
