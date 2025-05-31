package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "diary_tag")
public class DiaryTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dtid;

    // N:1 다이어리 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "did", nullable = false)
    private Diary diary;

    // N:1 태그 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tid", nullable = false)
    private Tag tag;
}
//a