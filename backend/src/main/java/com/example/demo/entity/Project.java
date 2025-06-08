package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "project", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "name"}))
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @Column(nullable = false)
    private String name;

    // 프로젝트 소유자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;    //@Column(columnDefinition = "TEXT")
    //private String description;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Diary> diaries = new ArrayList<>();

    public Project(String name) {
        this.name = name;
    }
}