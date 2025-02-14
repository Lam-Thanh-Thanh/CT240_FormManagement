package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Map;

@Document(collection = "responses")
public class Response {
    @Id
    private String id;
    private String surveyId;  // ID của khảo sát
    private String userId;    // ID của người trả lời
    private Map<String, String> answers; // Câu hỏi và câu trả lời

    public Response() {}

    public Response(String surveyId, String userId, Map<String, String> answers) {
        this.surveyId = surveyId;
        this.userId = userId;
        this.answers = answers;
    }

    // Getter & Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSurveyId() { return surveyId; }
    public void setSurveyId(String surveyId) { this.surveyId = surveyId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public Map<String, String> getAnswers() { return answers; }
    public void setAnswers(Map<String, String> answers) { this.answers = answers; }
}
