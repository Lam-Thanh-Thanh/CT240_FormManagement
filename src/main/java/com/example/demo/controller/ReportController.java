package com.example.demo.controller;

import com.example.demo.model.Response;
import com.example.demo.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ResponseRepository responseRepository;

    // Thống kê số lượng câu trả lời theo khảo sát
    @GetMapping("/survey/{surveyId}")
    public ResponseEntity<Map<String, Object>> getSurveyReport(@PathVariable String surveyId) {
        List<Response> responses = responseRepository.findBySurveyId(surveyId);
        
        Map<String, Integer> questionStats = new HashMap<>();
        for (Response response : responses) {
            for (Map.Entry<String, String> entry : response.getAnswers().entrySet()) {
                String question = entry.getKey();
                questionStats.put(question, questionStats.getOrDefault(question, 0) + 1);
            }
        }

        Map<String, Object> report = new HashMap<>();
        report.put("totalResponses", responses.size());
        report.put("questionStats", questionStats);

        return ResponseEntity.ok(report);
    }

    // Thống kê số khảo sát mà một người đã tham gia
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getUserSurveyStats(@PathVariable String userId) {
        List<Response> responses = responseRepository.findByUserId(userId);
        
        Set<String> participatedSurveys = new HashSet<>();
        for (Response response : responses) {
            participatedSurveys.add(response.getSurveyId());
        }

        Map<String, Object> report = new HashMap<>();
        report.put("totalSurveysParticipated", participatedSurveys.size());
        report.put("surveys", participatedSurveys);

        return ResponseEntity.ok(report);
    }
}
