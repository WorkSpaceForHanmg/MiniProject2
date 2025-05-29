package com.example.demo.repository_test;

import com.example.demo.entity.Diary;
import com.example.demo.entity.DiaryTag;
import com.example.demo.entity.Project;
import com.example.demo.entity.Tag;
import com.example.demo.repository.DiaryRepository;
import com.example.demo.repository.DiaryTagRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("prod")
class DiaryTagRepositoryTest {

    @Autowired
    private DiaryTagRepository diaryTagRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TagRepository tagRepository;

    @Test
    @DisplayName("특정 태그로 DiaryTag 목록 조회")
    void findByTag_성공() {
        // given
        Project project = new Project("다이어리 태그 테스트");
        projectRepository.save(project);

        Diary diary = Diary.builder()
                .date(LocalDate.now())
                .project(project)
                .devfeel("기분 좋음")
                .build();
        diaryRepository.save(diary);

        Tag tag = Tag.builder()
                .name("테스트 태그")
                .build();
        tagRepository.save(tag);

        DiaryTag diaryTag = DiaryTag.builder()
                .diary(diary)
                .tag(tag)
                .build();
        diaryTagRepository.save(diaryTag);

        // when
        List<DiaryTag> result = diaryTagRepository.findByTag(tag);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTag().getName()).isEqualTo("테스트 태그");
        assertThat(result.get(0).getDiary().getDevfeel()).isEqualTo("기분 좋음");
    }
}
//1