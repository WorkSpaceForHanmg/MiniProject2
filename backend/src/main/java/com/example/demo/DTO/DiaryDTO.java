package com.example.demo.DTO;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

public class DiaryDTO {
      @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        private LocalDate date;
        private String devfeel;
        private String diff;
        private String error;
        private String explaination;
        private Long projectId;
        private List<String> tags;
        private List<Long> tagIds;
    }
    
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long did;
        private LocalDate date;
        private String devfeel;
        private String diff;
        private String error;
        private String explaination;
        private Long projectId;
        private String projectName;
        private List<String> tags;
    }
    
    //  검색했을 때 검색결과를 간단하게 보여주는 DTO
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor    public static class SimpleResponse {
        private Long did;
        private LocalDate date;
        private String devfeel;
        private String projectName;
    }
}