package com.basic.myspringbootapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @GetMapping
    public ResponseEntity<List<String>> getTags() {
        return ResponseEntity.ok(List.of("React", "Spring Boot", "JPA"));
    }
}
