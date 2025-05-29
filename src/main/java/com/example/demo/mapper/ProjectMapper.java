package com.example.demo.mapper;

import com.example.demo.DTO.ProjectDTO;
import com.example.demo.entity.Project;

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
//a