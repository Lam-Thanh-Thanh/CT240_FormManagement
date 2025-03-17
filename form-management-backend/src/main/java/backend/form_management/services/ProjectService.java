package backend.form_management.services;

import backend.form_management.models.Form;
import backend.form_management.models.Project;
import backend.form_management.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

//    //get all projects of userId
//    public List<Project> getAllProjects(String userId) {
//        return projectRepository.findAllByUserId(userId);
//    }

    //get all projects
    public List<Project> getAllProjectsByUserId(String userId) {

        return projectRepository.findAllByUserId(userId);
    }

    //get project by id
    public Optional<Project> getProjectById(String projectId) {
        return projectRepository.findById(String.valueOf(projectId));
    }


    //creat a new project
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }


    //update project
    public Project updateProject(String projectId, Project updatedProject) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            Project existingProject = project.get();
            existingProject.setName(updatedProject.getName());
            existingProject.setDescription(updatedProject.getDescription());
            return projectRepository.save(existingProject);
        }  else {
            throw new RuntimeException("Form not found");
        }

    }


    //add form to project
    public void addFormToProject(String projectId, Form form) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.getForms().add(form);
            projectRepository.save(project);
        }
    }

    //update form of project
    public void updateFormToProject(String projectId, String formId, Form updatedForm) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            // Tìm form trong danh sách forms
            for (int i = 0; i < project.getForms().size(); i++) {
                if (project.getForms().get(i).getId().equals(formId)) {
                    project.getForms().set(i, updatedForm); // Cập nhật form
                    break;
                }
            }      //add new form
            projectRepository.save(project);
        } else {
            throw new RuntimeException("Project not found");
        }
    }

    //delete form of project
    public void deleteFormOfProject(String projectId, String formId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.getForms().removeIf(form -> form.getId().equals(formId));
            projectRepository.save(project);
        }
    }

    //delete project
    public void deleteProject(String projectId) {
        projectRepository.deleteById(projectId);
    }



}
