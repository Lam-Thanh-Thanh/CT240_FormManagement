package backend.form_management.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;

@Document (collection = "questions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    private String id;
    private String content;
    private String formId;
    private String type;    //

        private String fileUrl;   //////////////////////////////////////
        private String resourceType;   //////////////////////////////////////
    private String publicId;
    private String textUrl;

    private List<Option> options;
//    private List<Answer> answers;

}
