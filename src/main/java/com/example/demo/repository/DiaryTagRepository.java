package com.example.demo.repository;

import com.example.demo.entity.DiaryTag;
import com.example.demo.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryTagRepository extends JpaRepository<DiaryTag, Long> {
    // 특정 태그에 연결된 DiaryTag 리스트 조회
    List<DiaryTag> findByTag(Tag tag);
}