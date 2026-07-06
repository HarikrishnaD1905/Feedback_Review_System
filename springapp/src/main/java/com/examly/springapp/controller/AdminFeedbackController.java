package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.FeedbackStatus;
import com.examly.springapp.service.FeedbackService;

@RestController
@RequestMapping("/api/admin")
public class AdminFeedbackController {
    @Autowired
    private FeedbackService fs;

    @GetMapping("/feedback")
    public ResponseEntity<List<Feedback>> getAllFeedback(){
        List<Feedback> feedbacks = fs.getAllFeedback();
        for(Feedback fb: feedbacks){
            fb.setStatus(FeedbackStatus.APPROVED);
        }
        return new ResponseEntity<>(feedbacks,HttpStatus.OK);
    }

    //@PutMapping("/feedback/{id}/status")
    //public ResponseEntity<> updateFeedStatus(@PathVariable Long id,)
}
