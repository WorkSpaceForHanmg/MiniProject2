package com.example.demo.mapper;

import com.example.demo.DTO.DiaryDTO;
import com.example.demo.DTO.DiaryTagDTO;
import com.example.demo.DTO.TagDTO;
import com.example.demo.entity.DiaryTag;

public class DiaryTagMapper {
    public static DiaryTagDTO.Response entityToDto(DiaryTag entity) {
        return DiaryTagDTO.Response.builder()
            .dtid(entity.getDtid())
            .diary(DiaryDTO.SimpleResponse.builder()
                   .did(entity.getDiary().getDid())
                   .date(entity.getDiary().getDate().toString())
                   .title(entity.getDiary().getDevfeel())
                   .build())
            .tag(TagDTO.SimpleResponse.builder()
                 .tid(entity.getTag().getTid())
                 .name(entity.getTag().getName())
                 .build())
            .build();
    }
}
//a