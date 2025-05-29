package com.example.demo.Controller;

import com.example.demo.DTO.ProjectDTO;
import com.example.demo.entity.Project;
import com.example.demo.repository.ProjectRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectRepository projectRepository;

    @PostMapping
    public ResponseEntity<ProjectDTO.Response> createProject(@RequestBody @Valid ProjectDTO.Request request) {
        Project project = Project.builder()
                .name(request.getName())
                .build();
        Project saved = projectRepository.save(project);

        ProjectDTO.Response response = ProjectDTO.Response.builder()
                .pid(saved.getPid())
                .name(saved.getName())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO.Response>> getAllProjects() {
        List<ProjectDTO.Response> projects = projectRepository.findAll().stream()
                .map(project -> ProjectDTO.Response.builder()
                        .pid(project.getPid())
                        .name(project.getName())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO.Response> getProject(@PathVariable Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 없습니다."));
        ProjectDTO.Response response = ProjectDTO.Response.builder()
                .pid(project.getPid())
                .name(project.getName())
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}")
    public ResponseEntity<ProjectDTO.Response> updateProject(@PathVariable Long id,
                                                             @RequestBody @Valid ProjectDTO.Request request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 없습니다."));

        project.setName(request.getName());
        //project.setDescription(request.getDescription());

        Project updated = projectRepository.save(project);

        ProjectDTO.Response response = ProjectDTO.Response.builder()
                .pid(updated.getPid())
                .name(updated.getName())
                //.description(updated.getDescription())
                .build();

        return ResponseEntity.ok(response);
    }
} 
