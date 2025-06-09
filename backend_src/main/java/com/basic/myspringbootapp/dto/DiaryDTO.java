package com.basic.myspringbootapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class DiaryDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotNull(message = "날짜는 필수입니다.")
        private LocalDate date;

        @NotBlank(message = "개발 소감은 필수입니다.")
        private String title;  // 개발 소감(devfeel)

        private String diff;   // 어려웠던 점

        private String error;  // 에러 사항 및 대처방안

        private String content; // 코드 설명 (explain)

        @NotNull(message = "프로젝트 정보는 필수입니다.")
        private String projectName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long did;
        private LocalDate date;
        private String title;   // devfeel
        private String diff;
        private String error;
        private String content; // explain
        private String codeExplanation;
        private String devReview;
        private String challenges;
        private String errorSummary;
        private List<String> errorTags;
        private String errorSolution;
        private ProjectDTO.SimpleResponse project;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SimpleResponse {
        private Long did;
        private LocalDate date;
        private String title;  // devfeel
        private List<TagDTO.SimpleResponse> tags;
    }
}
