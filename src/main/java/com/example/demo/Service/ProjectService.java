package com.example.demo.Service;

import com.example.demo.entity.Project;
import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();
    Project getProjectById(Long pid);
    Project saveProject(Project project);
    boolean deleteProjectById(Long pid);
}
//a