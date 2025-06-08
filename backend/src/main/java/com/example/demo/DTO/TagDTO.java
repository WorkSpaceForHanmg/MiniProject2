package com.example.demo.DTO;

import com.example.demo.entity.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class TagDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotBlank(message = "태그 이름은 필수입니다.")
        private String name;

        // DTO → Entity
        public Tag toEntity() {
            return Tag.builder()
                    .name(this.name)
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long tid;
        private String name;

        // Entity → DTO
        public static Response fromEntity(Tag tag) {
            return Response.builder()
                    .tid(tag.getTid())
                    .name(tag.getName())
                    .build();
        }
    }    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SimpleResponse {
        private Long tid;
        private String name;
    }
}