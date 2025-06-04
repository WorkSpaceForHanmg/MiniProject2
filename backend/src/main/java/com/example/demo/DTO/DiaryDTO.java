package com.example.demo.DTO;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

public class DiaryDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private LocalDate date;
        private String title;
        private String diff;
        private String error;
        private String content;
        private Long projectId;
        private List<String> tags;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private Long did;
        private String date;
        private String title;
        private String diff;
        private String error;
        private String content;
        private Long projectId;
        private String projectName;
        private List<String> tags;
    }

    //  검색했을 때 검색결과를 간단하게 보여주는 DTO
    @Getter
    @Builder
    @AllArgsConstructor
    public static class SimpleResponse {
        private Long did;
        private String date;
        private String title;
    }
}