package com.basic.myspringbootapp.domain;

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

    // 다이어리 ↔ 프로젝트 N:1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid", nullable = false)
    private Project project;

    @Column(length = 500)
    private String devfeel;

    @Column(length = 500)
    private String diff;

    @Column(length = 500)
    private String error;

    @Column(columnDefinition = "TEXT")
    private String explain;

    // 하나의 다이어리에 여러 태그가 연결됨
    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaryTag> diaryTags = new ArrayList<>();

}
