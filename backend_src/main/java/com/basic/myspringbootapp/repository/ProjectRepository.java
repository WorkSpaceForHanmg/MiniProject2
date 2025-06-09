package com.basic.myspringbootapp.repository;

import com.basic.myspringbootapp.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    // 프로젝트 이름으로 조회
    Optional<Project> findByName(String name);
}
