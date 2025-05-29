package com.example.demo.DTO;

import com.example.demo.entity.Project;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class ProjectDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotBlank(message = "프로젝트 이름은 필수입니다.")
        private String name;

        //private String description;

        // DTO → Entity
        public Project toEntity() {
            return Project.builder()
                    .name(this.name)
                    //.description(this.description)
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long pid;
        private String name;
       // private String description;

        // Entity → DTO
        public static Response fromEntity(Project project) {
            return Response.builder()
                    .pid(project.getPid())
                    .name(project.getName())
                    //.description(project.getDescription())
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SimpleResponse {
        private Long pid;
        private String name;
    }
}
//a