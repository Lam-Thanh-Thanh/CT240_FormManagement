package com.example.demo.controller;

import com.example.demo.model.Survey;
import com.example.demo.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    @Autowired
    private SurveyRepository surveyRepository;

    // Lấy danh sách phiếu khảo sát
    @GetMapping
    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    // Lấy danh sách khảo sát theo dự án
    @GetMapping("/project/{projectId}")
    public List<Survey> getSurveysByProject(@PathVariable String projectId) {
        return surveyRepository.findByProjectId(projectId);
    }

    // Tạo phiếu khảo sát
    @PostMapping
    public Survey createSurvey(@RequestBody Survey survey) {
        return surveyRepository.save(survey);
    }

    // Xóa phiếu khảo sát theo ID
    @DeleteMapping("/{id}")
    public String deleteSurvey(@PathVariable String id) {
        surveyRepository.deleteById(id);
        return "Survey deleted with id: " + id;
    }
}
