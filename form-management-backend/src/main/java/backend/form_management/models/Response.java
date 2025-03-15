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
import java.util.Map;

@Document (collection = "responses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    @Id
    private String id;
    private String formId; // Liên kết với form
//    private String userId; 
    private List<Answer> answers;
    @CreatedDate
    private Instant createdAt;


}
