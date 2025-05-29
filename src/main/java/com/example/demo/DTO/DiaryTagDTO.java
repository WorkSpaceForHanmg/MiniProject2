package com.example.demo.DTO;

import com.example.demo.entity.Diary;
import com.example.demo.entity.DiaryTag;
import com.example.demo.entity.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class DiaryTagDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @NotNull(message = "다이어리 ID는 필수입니다.")
        private Long diaryId;

        @NotNull(message = "태그 ID는 필수입니다.")
        private Long tagId;

        //DTO → Entity
        public DiaryTag toEntity(Diary diary, Tag tag) {
            return DiaryTag.builder()
                    .diary(diary)
                    .tag(tag)
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long dtid;
        private DiaryDTO.SimpleResponse diary;
        private TagDTO.SimpleResponse tag;

        //Entity → DTO
        public static Response fromEntity(DiaryTag diaryTag) {
            return Response.builder()
                    .dtid(diaryTag.getDtid())
                    .diary(DiaryDTO.SimpleResponse.builder()
                            .did(diaryTag.getDiary().getDid())
                            .date(diaryTag.getDiary().getDate())
                            .title(diaryTag.getDiary().getDevfeel())
                            .build())
                    .tag(TagDTO.SimpleResponse.builder()
                            .tid(diaryTag.getTag().getTid())
                            .name(diaryTag.getTag().getName())
                            .build())
                    .build();
        }
    }
}
//a