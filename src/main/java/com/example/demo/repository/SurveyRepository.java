package com.example.demo.repository;

import com.example.demo.model.Survey;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface SurveyRepository extends MongoRepository<Survey, String> {
    List<Survey> findByProjectId(String projectId);
}
