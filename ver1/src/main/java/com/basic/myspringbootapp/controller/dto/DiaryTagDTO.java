package com.basic.myspringbootapp.controller.dto;

import com.basic.myspringbootapp.controller.dto.DiaryDTO;
import com.basic.myspringbootapp.controller.dto.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class DiaryTagDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long dtid;
        private DiaryDTO.SimpleResponse diary;
        private TagDTO.SimpleResponse tag;
    }

}
