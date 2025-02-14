package com.example.demo.controller;

import com.example.demo.model.Project;
import com.example.demo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    // Lấy danh sách tất cả dự án
    @GetMapping
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // Lấy danh sách dự án của một chủ dự án
    @GetMapping("/owner/{ownerId}")
    public List<Project> getProjectsByOwner(@PathVariable String ownerId) {
        return projectRepository.findByOwnerId(ownerId);
    }

    // Tạo dự án mới
    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectRepository.save(project);
    }

    // Cập nhật thông tin dự án
    @PutMapping("/{id}")
    public Project updateProject(@PathVariable String id, @RequestBody Project updatedProject) {
        return projectRepository.findById(id).map(project -> {
            project.setName(updatedProject.getName());
            project.setDescription(updatedProject.getDescription());
            return projectRepository.save(project);
        }).orElseGet(() -> projectRepository.save(updatedProject));
    }

    // Xóa dự án theo ID
    @DeleteMapping("/{id}")
    public String deleteProject(@PathVariable String id) {
        projectRepository.deleteById(id);
        return "Project deleted with id: " + id;
    }
}
