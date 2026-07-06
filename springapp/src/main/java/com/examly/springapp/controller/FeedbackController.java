package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Feedback;
import com.examly.springapp.service.FeedbackService;

@RestController
@RequestMapping("/api")
public class FeedbackController {
    @Autowired
    private FeedbackService fs;

    @PostMapping("/feedback")
    public ResponseEntity<Feedback> addFb(@RequestBody Feedback fb){
        Feedback feed = fs.addFeedback(fb);
        return new ResponseEntity<> (feed,HttpStatus.CREATED);
    }

    
}
