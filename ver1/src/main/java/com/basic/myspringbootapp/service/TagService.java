package com.basic.myspringbootapp.service;

import com.basic.myspringbootapp.domain.Tag;
import com.basic.myspringbootapp.repository.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    // 전체 태그 조회
    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    // ID로 태그 조회
    public Optional<Tag> findTagById(Long tid) {
        return tagRepository.findById(tid);
    }

    // 이름으로 태그 조회
    public Optional<Tag> findTagByName(String name) {
        return tagRepository.findByName(name);
    }

    // 새 태그 저장
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    // 태그 삭제
    public void deleteTag(Long tid) {
        tagRepository.deleteById(tid);
    }
}
