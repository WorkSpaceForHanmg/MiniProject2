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
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @Column(nullable = false, unique = true)
    private String name;

    //@Column(columnDefinition = "TEXT")
    //private String description;

    public Project(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diary> diaries = new ArrayList<>();
}
