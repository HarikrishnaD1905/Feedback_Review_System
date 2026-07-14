package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.StatusRequest;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.FeedbackStatus;
import com.examly.springapp.service.FeedbackService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/admin")
public class AdminFeedbackController {
    @Autowired
    private FeedbackService fs;

    @GetMapping("/feedback")
    public ResponseEntity<List<Feedback>> getAllFeedback(){
        List<Feedback> feedbacks = fs.getAllFeedback();
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @PutMapping("/feedback/{id}/status")
    public ResponseEntity<Feedback> updateStatus(@PathVariable Long id,@RequestBody StatusRequest request){
        if (request == null || request.getStatus() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Feedback fb = fs.updateStatus(id, request.getStatus());
        if(fb==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }        
        return new ResponseEntity<>(fb, HttpStatus.OK);
    }

    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        boolean df=fs.deleteFeedback(id);
        if(df){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
