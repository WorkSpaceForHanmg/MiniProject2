package com.basic.myspringbootapp.repository;

import com.basic.myspringbootapp.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    // 태그 이름으로 조회
    Optional<Tag> findByName(String name);
}
