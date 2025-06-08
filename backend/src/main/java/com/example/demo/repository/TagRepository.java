package com.example.demo.repository;

import com.example.demo.entity.Tag;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    // 태그 이름으로 조회 (전역)
    Optional<Tag> findByName(String name);
    
    // 사용자별 태그 이름으로 조회
    Optional<Tag> findByUserAndName(User user, String name);
    
    // 사용자별 태그 목록 조회
    List<Tag> findByUser(User user);
}
//a