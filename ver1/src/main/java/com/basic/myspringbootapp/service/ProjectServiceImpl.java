package com.basic.myspringbootapp.service;

import com.basic.myspringbootapp.domain.Project;
import com.basic.myspringbootapp.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(Long pid) {
        Optional<Project> projectOpt = projectRepository.findById(pid);
        return projectOpt.orElse(null);
    }

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public boolean deleteProjectById(Long pid) {
        if (projectRepository.existsById(pid)) {
            projectRepository.deleteById(pid);
            return true;
        }
        return false;
    }
}
