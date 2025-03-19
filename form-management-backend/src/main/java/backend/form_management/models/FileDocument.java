package backend.form_management.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "files")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDocument {
    @Id
    private String id;
    private String fileName;
    private String contentType;
    private long size;
    private String filePath; // Đường dẫn lưu file trong hệ thống
}
