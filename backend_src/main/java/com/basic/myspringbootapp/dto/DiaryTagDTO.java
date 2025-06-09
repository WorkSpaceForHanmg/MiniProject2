<<<<<<< HEAD:ver1/src/main/java/com/basic/myspringbootapp/dto/DiaryTagDTO.java
package com.basic.myspringbootapp.dto;

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
        private SimpleResponse diary;
        private TagDTO.SimpleResponse tag;
    }

}
=======
package com.basic.myspringbootapp.dto;

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
>>>>>>> edf276bb12f89f14946d40b902912c48538d07ed:backend_src/main/java/com/basic/myspringbootapp/dto/DiaryTagDTO.java
