package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.FeedbackStatus;

public interface FeedbackService {

    Feedback addFeedback(Feedback fb);

    List<Feedback> getAllFeedback();

    List<Feedback> getUserFeedback(String userid);

    Feedback updateStatus(Long id, FeedbackStatus status);

    void deleteFeedback(Long id);
} 
