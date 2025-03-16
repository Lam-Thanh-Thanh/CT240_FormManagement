package backend.form_management.controllers;

import backend.form_management.models.Form;
import backend.form_management.models.Project;
import backend.form_management.services.FormService;
import backend.form_management.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
 @Autowired
 private FormService formService;



//    @GetMapping("/")
//    public ResponseEntity<List<Project> > getAllProjects() {
//        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
//    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Project> > getAllProjectsByUserId(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(projectService.getAllProjectsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{projectId}/details")
    public ResponseEntity<Optional<Project>> getProjectDetails(@PathVariable("projectId") String projectId) {
        return new ResponseEntity<Optional<Project>>(projectService.getProjectById(projectId), HttpStatus.OK);
    }



    @PostMapping("/create")
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

@PutMapping("/{projectId}/update")
public ResponseEntity<Project> updateProject(@PathVariable("projectId") String projectId, @RequestBody Project project) {
    Project project1 = projectService.updateProject(projectId, project);

    return ResponseEntity.ok(project1);
}

    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable("projectId") String projectId) throws IOException {
        projectService.deleteProject(projectId);
        List<Form> forms = formService.getAllFormsOfProject(projectId);
        for (Form form : forms) {
            formService.deleteForm(form.getId());
        }
        return ResponseEntity.ok("Project deleted successfully");
    }

}
