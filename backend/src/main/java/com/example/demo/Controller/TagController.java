package com.example.demo.Controller;

import com.example.demo.DTO.TagDTO;
import com.example.demo.Service.TagService;
import com.example.demo.entity.Tag;
import com.example.demo.mapper.TagMapper;
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

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagDTO.Response> createTag(@RequestBody @Valid TagDTO.Request request) {
        Tag tag = Tag.builder()
                .name(request.getName())
                .build();
        Tag saved = tagService.saveTag(tag);
        return ResponseEntity.ok(TagMapper.entityToDto(saved));
    }

    @GetMapping
    public ResponseEntity<List<TagDTO.Response>> getAllTags() {
        List<Tag> tags = tagService.findAllTags();
        List<TagDTO.Response> responseList = tags.stream()
                .map(TagMapper::entityToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
//a