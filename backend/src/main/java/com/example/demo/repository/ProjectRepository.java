package com.example.demo.repository;

import com.example.demo.entity.Project;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    // 프로젝트 이름으로 조회
    Optional<Project> findByName(String name);
    
    // 사용자별 프로젝트 조회
    List<Project> findByUser(User user);
}
//a