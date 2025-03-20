package backend.form_management.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "forms")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Form {
    @Id
    private String id;

    private String title;
    private String description;
    private String projectId;
    private List<Question> questions;
    @CreatedDate
    private Instant createdAt;  // Ngày tạo tự động
    @LastModifiedDate
    private Instant lastModifiedAt;
    private Instant expirationDate;  // Thời gian hết hạn của form

//    private List<Response> response;    //
}
