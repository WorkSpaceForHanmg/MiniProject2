package com.example.demo.repository_test;

import com.example.demo.entity.Diary;
import com.example.demo.entity.Project;
import com.example.demo.repository.DiaryRepository;
import com.example.demo.repository.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("prod") // MariaDB 실제 설정 사용
class DiaryRepositoryTest {


    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    @DisplayName("프로젝트로 일기 목록 조회")
    void findByProject_성공() {
        // given
        Project project = new Project("테스트 프로젝트");
        projectRepository.save(project);

        Diary diary = new Diary(LocalDate.now(), "테스트 내용", project);
        diaryRepository.save(diary);

        // when
        List<Diary> diaries = diaryRepository.findByProject(project);

        // then
        assertThat(diaries).hasSize(1);
        assertThat(diaries.get(0).getDevfeel()).isEqualTo("테스트 내용");
    }

    @Test
    @DisplayName("날짜 범위로 일기 조회")
    void findByDateBetween_성공() {
        // given
        Project project = new Project("범위 테스트");
        projectRepository.save(project);

        Diary diary1 = new Diary(LocalDate.of(2024, 1, 1), "일기1", project);
        Diary diary2 = new Diary(LocalDate.of(2024, 1, 5), "일기2", project);
        diaryRepository.saveAll(List.of(diary1, diary2));

        // when
        List<Diary> results = diaryRepository.findByDateBetween(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 10)
        );

        // then
        assertThat(results).hasSize(2);
    }

    @Test
    @DisplayName("날짜가 null인 경우 저장 실패 (무결성 검증)")
    void date가_null이면_예외발생() {
        // given
        Project project = new Project("무결성 테스트");
        projectRepository.save(project);

        Diary diary = new Diary(null, "내용", project); // 날짜 null

        // when & then
        assertThrows(DataIntegrityViolationException.class, () -> {
            diaryRepository.saveAndFlush(diary);
        });
    }
}
