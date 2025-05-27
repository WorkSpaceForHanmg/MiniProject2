package com.basic.myspringbootapp.repository;

import com.basic.myspringbootapp.domain.Diary;
import com.basic.myspringbootapp.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    // 특정 프로젝트의 일기 리스트 조회
    List<Diary> findByProject(Project project);

    // 특정 날짜 범위 내 일기 조회
    List<Diary> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
