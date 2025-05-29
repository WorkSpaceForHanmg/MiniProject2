package com.example.demo.repository_test;

import com.example.demo.entity.Project;
import com.example.demo.repository.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("prod")
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    @DisplayName("프로젝트 이름으로 조회 성공")
    void findByName_성공() {
        // given
        Project project = new Project("개발일지 프로젝트");
        projectRepository.save(project);

        // when
        Optional<Project> result = projectRepository.findByName("개발일지 프로젝트");

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("개발일지 프로젝트");
    }

    @Test
    @DisplayName("존재하지 않는 프로젝트 이름 조회시 빈 Optional 반환")
    void findByName_실패() {
        // when
        Optional<Project> result = projectRepository.findByName("없는 프로젝트");

        // then
        assertThat(result).isNotPresent();
    }
}
