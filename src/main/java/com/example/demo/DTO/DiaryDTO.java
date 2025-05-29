package com.example.demo.DTO;

import com.example.demo.entity.Diary;
import com.example.demo.entity.Project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

public class DiaryDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotNull(message = "날짜는 필수입니다.")
        private LocalDate date;

        @NotBlank(message = "개발 소감은 필수입니다.")
        private String title;  // devfeel

        private String diff;   // 어려웠던 점
        private String error;  // 에러 사항 및 대처방안
        private String content; // 코드 설명 (explain)

        @NotNull(message = "프로젝트 정보는 필수입니다.")
        private Long projectId;

        //DTO → Entity
        public Diary toEntity(Project project) {
            return Diary.builder()
                    .date(this.date)
                    .devfeel(this.title)
                    .diff(this.diff)
                    .error(this.error)
                    .explaination(this.content)
                    .project(project)
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long did;
        private LocalDate date;
        private String title;
        private String diff;
        private String error;
        private String content;
        private ProjectDTO.SimpleResponse project;

        //Entity → DTO
        public static Response fromEntity(Diary diary) {
            return Response.builder()
                    .did(diary.getDid())
                    .date(diary.getDate())
                    .title(diary.getDevfeel())
                    .diff(diary.getDiff())
                    .error(diary.getError())
                    .content(diary.getExplaination())
                    .project(ProjectDTO.SimpleResponse.builder()
                            .pid(diary.getProject().getPid())
                            .name(diary.getProject().getName())
                            .build())
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SimpleResponse {
        private Long did;
        private LocalDate date;
        private String title;
    }
}
