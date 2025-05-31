package com.example.demo.mapper;

import com.example.demo.DTO.TagDTO;
import com.example.demo.entity.Tag;

public class TagMapper {

    // DTO → Entity (등록 시 사용)
    public static Tag dtoToEntity(TagDTO.Request dto) {
        if (dto == null) return null;

        return Tag.builder()
                .name(dto.getName())
                .build();
    }

    // Entity → DTO (응답용)
    public static TagDTO.Response entityToDto(Tag tag) {
        if (tag == null) return null;

        return TagDTO.Response.builder()
                .tid(tag.getTid())
                .name(tag.getName())
                .build();
    }

    // Entity → 간단 응답용 DTO
    public static TagDTO.SimpleResponse entityToSimpleDto(Tag tag) {
        if (tag == null) return null;

        return TagDTO.SimpleResponse.builder()
                .tid(tag.getTid())
                .name(tag.getName())
                .build();
    }
}
//a