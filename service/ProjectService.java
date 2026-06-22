package com.example.tasktracker.service;

import com.example.tasktracker.dto.ProjectDTOs;
import com.example.tasktracker.exception.ResourceNotFoundException;
import com.example.tasktracker.model.Project;
import com.example.tasktracker.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectDTOs.Response createProject(ProjectDTOs.Create dto) {
        Project project = new Project();
        project.setName(dto.name());
        project.setDescription(dto.description());
        Project saved = projectRepository.saveAndFlush(project);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<ProjectDTOs.Response> getAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public ProjectDTOs.Response getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project with ID " + id + " not found"));
        return mapToResponse(project);
    }

    private ProjectDTOs.Response mapToResponse(Project project) {
        return new ProjectDTOs.Response(
                project.getId(), project.getName(), project.getDescription(),
                project.getCreatedAt(), project.getLastModifiedAt()
        );
    }
}
