package com.basic.myspringbootapp.controller;

import com.basic.myspringbootapp.dto.ProjectDTO;
import com.basic.myspringbootapp.entity.Project;
import com.basic.myspringbootapp.mapper.ProjectMapper;
import com.basic.myspringbootapp.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // 전체 프로젝트 조회
    @GetMapping
    public ResponseEntity<List<ProjectDTO.SimpleResponse>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        List<ProjectDTO.SimpleResponse> responseList = projects.stream()
                .map(ProjectMapper::entityToSimpleDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    // 단일 프로젝트 조회
    @GetMapping("/{pid}")
    public ResponseEntity<ProjectDTO.Response> getProjectById(@PathVariable Long pid) {
        Project project = projectService.getProjectById(pid);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }
        ProjectDTO.Response response = ProjectMapper.entityToDto(project);
        return ResponseEntity.ok(response);
    }

    // 프로젝트 생성
    @PostMapping
    public ResponseEntity<ProjectDTO.Response> createProject(
            @Valid @RequestBody ProjectDTO.Request request) {

        Project project = ProjectMapper.dtoToEntity(request);
        Project savedProject = projectService.saveProject(project);

        ProjectDTO.Response response = ProjectMapper.entityToDto(savedProject);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 프로젝트 수정
    @PutMapping("/{pid}")
    public ResponseEntity<ProjectDTO.Response> updateProject(
            @PathVariable Long pid,
            @Valid @RequestBody ProjectDTO.Request request) {

        Project existingProject = projectService.getProjectById(pid);
        if (existingProject == null) {
            return ResponseEntity.notFound().build();
        }

        existingProject.setName(request.getName());
        // 만약 description 필드가 있다면
        // existingProject.setDescription(request.getDescription());

        Project updatedProject = projectService.saveProject(existingProject);
        ProjectDTO.Response response = ProjectMapper.entityToDto(updatedProject);
        return ResponseEntity.ok(response);
    }

    // 프로젝트 삭제
    @DeleteMapping("/{pid}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long pid) {
        boolean deleted = projectService.deleteProjectById(pid);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
