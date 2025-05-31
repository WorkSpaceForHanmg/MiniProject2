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
        private String title;         // summary
        private String diff;
        private String error;
        private String content;       // explanation
        private Long projectId;       // 선택된 프로젝트 ID
        private List<String> tags;    // 태그 이름 리스트 (선택사항)
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
        private List<String> tags;    // 태그 이름 리스트 (선택사항)
    }
}
