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
}
//a
