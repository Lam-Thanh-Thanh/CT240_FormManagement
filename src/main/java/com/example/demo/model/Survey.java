package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Document(collection = "surveys")
public class Survey {
    @Id
    private String id;
    private String title;
    private String description;
    private String projectId;
    private List<Question> questions;
    private Date createdAt;

    public Survey() {
        this.createdAt = new Date();
    }

    public Survey(String title, String description, String projectId, List<Question> questions) {
        this.title = title;
        this.description = description;
        this.projectId = projectId;
        this.questions = questions;
        this.createdAt = new Date();
    }

    // Getter & Setter
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public String getProjectId() {return projectId;}
    public void setProjectId(String projectId) {this.projectId = projectId;}
    public List<Question> getQuestions() {return questions;}
    public void setQuestions(List<Question> questions) {this.questions = questions;}
    public Date getCreatedAt() {return createdAt;}
    public void setCreatedAt(Date createdAt) {this.createdAt = createdAt;}

    // Nested class cho câu hỏi
    public static class Question {
        private String questionText;
        private List<String> options;

        public Question() {}

        public Question(String questionText, List<String> options) {
            this.questionText = questionText;
            this.options = options;
        }

        // Getter & Setter
        public String getQuestionText() {return questionText;}
        public void setQuestionText(String questionText) {this.questionText = questionText;}
        public List<String> getOptions() {return options;}
        public void setOptions(List<String> options) {this.options = options;}
    }
}
