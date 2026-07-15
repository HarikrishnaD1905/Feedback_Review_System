package com.examly.springapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.examly.springapp.dto.DashboardStatsResponse;
import com.examly.springapp.dto.FeedbackResponse;
import com.examly.springapp.dto.FeedbackSubmissionRequest;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.FeedbackStatus;

public interface FeedbackService {

    Feedback submitFeedback(FeedbackSubmissionRequest request);

    Page<FeedbackResponse> getAllFeedback(Pageable pageable, String name, String email,
                                          Integer rating, FeedbackStatus status, Long categoryId);

    FeedbackResponse getFeedbackById(Long id);

    Feedback updateStatus(Long id, FeedbackStatus status, String adminUsername);

    Feedback assignCategory(Long id, Long categoryId, String adminUsername);

    void deleteFeedback(Long id, String adminUsername);

    DashboardStatsResponse getDashboardStats();
}
