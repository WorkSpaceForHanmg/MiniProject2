package com.example.demo.Controller;

import com.example.demo.DTO.ProjectDTO;
import com.example.demo.entity.Project;
import com.example.demo.entity.User;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ProjectDTO.Response> createProject(@RequestBody @Valid ProjectDTO.Request request) {
        // 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Project project = Project.builder()
                .name(request.getName())
                .user(user)  // 사용자 설정
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
        // 현재 사용자의 프로젝트만 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        List<ProjectDTO.Response> projects = projectRepository.findByUser(user).stream()
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
//a