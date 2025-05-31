package com.example.demo.repository_test;

import com.example.demo.entity.Tag;
import com.example.demo.repository.TagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("prod")
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    @DisplayName("태그 이름으로 조회 성공")
    void findByName_성공() {
        // given
        Tag tag = Tag.builder()
                .name("Spring")
                .build();
        tagRepository.save(tag);

        // when
        Optional<Tag> result = tagRepository.findByName("Spring");

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Spring");
    }

    @Test
    @DisplayName("존재하지 않는 태그 이름 조회시 빈 Optional 반환")
    void findByName_실패() {
        // when
        Optional<Tag> result = tagRepository.findByName("NonExistentTag");

        // then
        assertThat(result).isNotPresent();
    }
}
//1