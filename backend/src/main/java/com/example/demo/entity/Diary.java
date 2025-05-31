package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "diary")
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long did;

    @Column(nullable = false)
    private LocalDate date;

    // 다이어리 ↔ 프로젝트 N:1 관계 (선택 사항)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid", nullable = true) // ← 여기 변경!
    private Project project;

    @Column(length = 500)
    private String devfeel;

    @Column(length = 500)
    private String diff;

    @Column(length = 500)
    private String error;

    @Column(columnDefinition = "TEXT")
    private String explaination;

    // 다이어리 ↔ 태그 N:다 관계 (중간 테이블 DiaryTag)
    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaryTag> diaryTags = new ArrayList<>();

    // Mapper에서 사용하기 위한 태그 리스트 추출 메서드
    public List<Tag> getTags() {
        return diaryTags.stream()
                .map(DiaryTag::getTag)
                .toList();
    }

    // 필요 시 기본 생성자 외 추가 생성자도 유지
    public Diary(LocalDate date, String devfeel, Project project) {
        this.date = date;
        this.devfeel = devfeel;
        this.project = project;
    }
}
