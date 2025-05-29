package com.example.demo.repository_test;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("prod")
class EntityIntegrityTest {

    @Autowired private ProjectRepository projectRepository;
    @Autowired private DiaryRepository diaryRepository;
    @Autowired private TagRepository tagRepository;
    @Autowired private DiaryTagRepository diaryTagRepository;

    @Test
    @DisplayName("Diary의 날짜가 null이면 저장 실패")
    void diary_date_null_예외() {
        Project project = new Project("무결성 테스트");
        projectRepository.save(project);

        Diary diary = Diary.builder()
                .date(null)
                .devfeel("내용")
                .project(project)
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> {
            diaryRepository.saveAndFlush(diary);
        });
    }

    @Test
    @DisplayName("Project 이름이 null이면 저장 실패")
    void project_name_null_예외() {
        Project project = new Project(null);

        assertThrows(DataIntegrityViolationException.class, () -> {
            projectRepository.saveAndFlush(project);
        });
    }

    @Test
    @DisplayName("Tag 이름 중복 시 저장 실패")
    void tag_name_duplicate_예외() {
        Tag tag1 = Tag.builder().name("중복태그").build();
        Tag tag2 = Tag.builder().name("중복태그").build();

        tagRepository.save(tag1);

        assertThrows(DataIntegrityViolationException.class, () -> {
            tagRepository.saveAndFlush(tag2);
        });
    }

    @Test
    @DisplayName("DiaryTag의 diary가 null이면 저장 실패")
    void diarytag_diary_null_예외() {
        Tag tag = Tag.builder().name("태그1").build();
        tagRepository.save(tag);

        DiaryTag diaryTag = DiaryTag.builder()
                .diary(null)
                .tag(tag)
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> {
            diaryTagRepository.saveAndFlush(diaryTag);
        });
    }

    @Test
    @DisplayName("DiaryTag의 tag가 null이면 저장 실패")
    void diarytag_tag_null_예외() {
        Project project = new Project("태그 null 테스트");
        projectRepository.save(project);

        Diary diary = Diary.builder()
                .date(LocalDate.now())
                .project(project)
                .devfeel("기분")
                .build();
        diaryRepository.save(diary);

        DiaryTag diaryTag = DiaryTag.builder()
                .diary(diary)
                .tag(null)
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> {
            diaryTagRepository.saveAndFlush(diaryTag);
        });
    }
}
//1
