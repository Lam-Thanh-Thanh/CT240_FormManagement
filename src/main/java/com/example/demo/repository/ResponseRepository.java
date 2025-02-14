package com.example.demo.repository;

import com.example.demo.model.Response;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ResponseRepository extends MongoRepository<Response, String> {
    List<Response> findBySurveyId(String surveyId);
    List<Response> findByUserId(String userId);
}
