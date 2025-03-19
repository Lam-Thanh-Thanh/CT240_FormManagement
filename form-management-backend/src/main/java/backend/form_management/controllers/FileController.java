package backend.form_management.controllers;

import backend.form_management.services.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")

public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            String filePath = fileStorageService.saveFile(file);
            return ResponseEntity.ok(filePath);
        } catch (IOException e) {
            e.printStackTrace(); // In lỗi chi tiết ra console
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
    }

    // API tải file
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            Resource file = fileStorageService.loadFileAsResource(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(file);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{filename}")
    public ResponseEntity<String> deleteFile(@PathVariable String filename) {
        try {
            boolean deleted = fileStorageService.deleteFile(filename);
            if (deleted) {
                return ResponseEntity.ok("File deleted successfully: " + filename);
            } else {
                return ResponseEntity.status(404).body("File not found: " + filename);
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error deleting file: " + e.getMessage());
        }
    }
}
