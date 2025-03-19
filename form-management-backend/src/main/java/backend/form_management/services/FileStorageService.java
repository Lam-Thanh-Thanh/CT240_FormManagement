package backend.form_management.services;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${upload.dir}")
    private String uploadDir;

    public String saveFile(MultipartFile file) throws IOException {
        if (uploadDir == null || uploadDir.isEmpty()) {
            throw new IOException("Upload directory is not set!");
        }
        if (file.isEmpty()) {
            throw new IOException("Uploaded file is empty!");
        }

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Tạo tên file an toàn
        String safeFileName = file.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
        String fileId = UUID.randomUUID().toString();
        String newFileName = fileId + "_" + safeFileName;
        Path filePath = uploadPath.resolve(newFileName);

        if (!Files.isWritable(uploadPath)) {
            throw new IOException("No write permission for directory: " + uploadDir);
        }

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return  newFileName;  // Trả về đường dẫn API
        } catch (IOException e) {
            throw new IOException("Could not save file: " + newFileName, e);
        }
    }

    // Lấy đường dẫn file trong hệ thống
    public Path getFilePath(String filename) {
        return Paths.get(uploadDir).resolve(filename).normalize();
    }
    // Load file dưới dạng Resource
    public Resource loadFileAsResource(String filename) throws IOException {
        Path filePath = getFilePath(filename);
        UrlResource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new IOException("File not found: " + filename);
        }

        return  resource;
    }

}
