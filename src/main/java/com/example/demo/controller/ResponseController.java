package com.example.demo.controller;

import com.example.demo.model.Response;
import com.example.demo.repository.ResponseRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/responses")
@Tag(name = "Responses", description = "API quản lý câu trả lời khảo sát")
public class ResponseController {

    @Autowired
    private ResponseRepository responseRepository;

    @Operation(summary = "Gửi câu trả lời khảo sát")
    @PostMapping
    public ResponseEntity<Response> submitResponse(@RequestBody Response response) {
        Response savedResponse = responseRepository.save(response);
        return ResponseEntity.ok(savedResponse);
    }

    @Operation(summary = "Lấy tất cả câu trả lời theo khảo sát")
    @GetMapping("/survey/{surveyId}")
    public ResponseEntity<List<Response>> getResponsesBySurvey(@PathVariable String surveyId) {
        List<Response> responses = responseRepository.findBySurveyId(surveyId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Lấy tất cả câu trả lời theo người dùng")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Response>> getResponsesByUser(@PathVariable String userId) {
        List<Response> responses = responseRepository.findByUserId(userId);
        return ResponseEntity.ok(responses);
    }
}
