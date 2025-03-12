package backend.form_management.controllers;

import backend.form_management.models.Form;
import backend.form_management.services.CloudinaryService;
import backend.form_management.services.FormService;
import backend.form_management.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class FormController {
    @Autowired
    private FormService formService ;
    @Autowired
    private ProjectService projectService ;
    @Autowired
    private CloudinaryService cloudinaryService ;

//    @GetMapping("")
//    public ResponseEntity<List<Form>> getAllFormOfProject(@PathVariable("projectId")String projectId) {
//        return new ResponseEntity<>(formService.getAllFormsByProjectId(projectId), HttpStatus.OK);
//    }

    @GetMapping("/forms/{formId}")
    public ResponseEntity<Optional<Form>> getFormDetails(@PathVariable("formId") String formId) {
        return new ResponseEntity<Optional<Form>>(formService.getFormById(formId), HttpStatus.OK);
    }

    @PostMapping("/{projectId}/form-create")
    public Form createFormForProject( @PathVariable("projectId") String projectId, @RequestBody Form form) {
        form.setProjectId(projectId);
        Form createdForm = formService.createForm(form);
        projectService.addFormToProject(projectId, createdForm);
        return createdForm;
    }



    @PutMapping("/{projectId}/forms/{formId}/update")
    public ResponseEntity<Form> updateForm(@PathVariable("projectId") String projectId, @PathVariable("formId") String formId, @RequestBody Form updatedForm) {
        Form form = formService.updateForm(formId, updatedForm);
        projectService.updateFormToProject(projectId,formId, updatedForm);
        return ResponseEntity.ok(form);
    }

    @DeleteMapping("/{projectId}/forms/{formId}/delete")
    public ResponseEntity<String> deleteFormOfProject(@PathVariable("projectId") String projectId, @PathVariable("formId") String formId) {
            projectService.deleteFormOfProject(projectId, formId);
        formService.deleteForm(formId);

        return ResponseEntity.ok("Form deleted successfully");
    }

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage( @RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = cloudinaryService.uploadFile(file);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    @DeleteMapping("/delete-image")
    public ResponseEntity<String> deleteImage(@RequestParam String publicId) {
        try {
            String result = cloudinaryService.deleteFile(publicId);
            if ("ok".equals(result)) {
                return ResponseEntity.ok("Image deleted successfully");
            } else {
                return ResponseEntity.badRequest().body("Failed to delete image");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }




}
