package com.examly.springapp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.FeedbackSubmissionRequest;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.service.FeedbackService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    /**
     * FR1: Public feedback submission endpoint.
     * FR1.3: Status is set to "NEW" automatically.
     * FR1.4: Returns a confirmation message.
     */
    @PostMapping("/feedback")
    public ResponseEntity<?> submitFeedback(@Valid @RequestBody FeedbackSubmissionRequest request) {
        Feedback feedback = feedbackService.submitFeedback(request);

        return new ResponseEntity<>(Map.of(
                "message", "Thank you for your feedback! Your submission has been received.",
                "feedbackId", feedback.getId(),
                "status", feedback.getStatus().name()
        ), HttpStatus.CREATED);
    }
}
