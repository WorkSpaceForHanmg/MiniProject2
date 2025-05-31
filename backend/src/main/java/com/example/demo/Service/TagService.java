package com.example.demo.Service;

import com.example.demo.entity.Tag;
import com.example.demo.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Tag> findTagById(Long tid) {
        return tagRepository.findById(tid);
    }

    @Transactional(readOnly = true)
    public Optional<Tag> findTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    public void deleteTag(Long tid) {
        tagRepository.deleteById(tid);
    }

    // 태그 이름 리스트 → 존재하면 가져오고, 없으면 생성
    @Transactional
    public List<Tag> getOrCreateTagsByName(List<String> tagNames) {
        return tagNames.stream()
                .map(name -> tagRepository.findByName(name)
                        .orElseGet(() -> tagRepository.save(Tag.builder().name(name).build()))
                )
                .toList();
    }
}
