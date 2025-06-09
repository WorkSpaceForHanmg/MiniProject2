package com.basic.myspringbootapp.service;

import com.basic.myspringbootapp.entity.Project;
import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();
    Project getProjectById(Long pid);
    Project saveProject(Project project);
    boolean deleteProjectById(Long pid);
    Project findByName(String name);           // 이름으로 프로젝트 조회
    Project createProjectIfNotExist(String name);  // 없으면 새로 생성
}

