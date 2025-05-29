package com.example.demo.Controller;

import com.example.demo.DTO.TagDTO;
import com.example.demo.entity.Tag;
import com.example.demo.mapper.TagMapper;
import com.example.demo.repository.TagRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagRepository tagRepository;

    @PostMapping
    public ResponseEntity<TagDTO.Response> createTag(@RequestBody @Valid TagDTO.Request request) {
        Tag tag = Tag.builder()
                .name(request.getName())
                .build();
        Tag saved = tagRepository.save(tag);
        return ResponseEntity.ok(TagMapper.entityToDto(saved));
    }

    @GetMapping
    public ResponseEntity<List<TagDTO.Response>> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        List<TagDTO.Response> responseList = tags.stream()
                .map(TagMapper::entityToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }
}