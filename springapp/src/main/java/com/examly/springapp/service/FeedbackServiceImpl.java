package com.examly.springapp.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.dto.DashboardStatsResponse;
import com.examly.springapp.dto.FeedbackResponse;
import com.examly.springapp.dto.FeedbackSubmissionRequest;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Admin;
import com.examly.springapp.model.Category;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.FeedbackStatus;
import com.examly.springapp.repository.AdminRepository;
import com.examly.springapp.repository.CategoryRepository;
import com.examly.springapp.repository.FeedbackRepository;
import com.examly.springapp.specification.FeedbackSpecification;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Override
    @Transactional
    public Feedback submitFeedback(FeedbackSubmissionRequest request) {
        Feedback feedback = new Feedback();
        feedback.setSubmitterName(request.getSubmitterName());
        feedback.setSubmitterEmail(request.getSubmitterEmail());
        feedback.setFeedbackContent(request.getFeedbackContent());
        feedback.setRating(request.getRating());
        feedback.setStatus(FeedbackStatus.NEW);

        Feedback saved = feedbackRepository.save(feedback);

        // FR8: Send email notification to admin
        emailNotificationService.notifyAdminNewFeedback(saved);

        return saved;
    }

    @Override
    public Page<FeedbackResponse> getAllFeedback(Pageable pageable, String name, String email,
                                                  Integer rating, FeedbackStatus status, Long categoryId) {

        Specification<Feedback> spec = Specification.where(null);

        if (name != null && !name.isBlank()) {
            spec = spec.and(FeedbackSpecification.hasSubmitterName(name));
        }
        if (email != null && !email.isBlank()) {
            spec = spec.and(FeedbackSpecification.hasSubmitterEmail(email));
        }
        if (rating != null) {
            spec = spec.and(FeedbackSpecification.hasRating(rating));
        }
        if (status != null) {
            spec = spec.and(FeedbackSpecification.hasStatus(status));
        }
        if (categoryId != null) {
            spec = spec.and(FeedbackSpecification.hasCategoryId(categoryId));
        }

        Page<Feedback> page = feedbackRepository.findAll(spec, pageable);
        return page.map(this::toResponse);
    }

    @Override
    public FeedbackResponse getFeedbackById(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback", id));
        return toResponse(feedback);
    }

    @Override
    @Transactional
    public Feedback updateStatus(Long id, FeedbackStatus status, String adminUsername) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback", id));

        FeedbackStatus oldStatus = feedback.getStatus();
        feedback.setStatus(status);
        Feedback updated = feedbackRepository.save(feedback);

        // FR4.4 + FR10: Create audit log
        Long adminId = getAdminId(adminUsername);
        auditLogService.createLog(adminId,
                "STATUS_CHANGE: " + oldStatus + " -> " + status,
                "FEEDBACK", id);

        return updated;
    }

    @Override
    @Transactional
    public Feedback assignCategory(Long id, Long categoryId, String adminUsername) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback", id));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", categoryId));

        feedback.setCategory(category);
        Feedback updated = feedbackRepository.save(feedback);

        // FR10: Create audit log for category assignment
        Long adminId = getAdminId(adminUsername);
        auditLogService.createLog(adminId,
                "CATEGORY_ASSIGNED: " + category.getName(),
                "FEEDBACK", id);

        return updated;
    }

    @Override
    @Transactional
    public void deleteFeedback(Long id, String adminUsername) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback", id));

        // FR5.4 + FR10: Create audit log before deletion
        Long adminId = getAdminId(adminUsername);
        auditLogService.createLog(adminId,
                "DELETED",
                "FEEDBACK", id);

        feedbackRepository.delete(feedback);
    }

    @Override
    public DashboardStatsResponse getDashboardStats() {
        DashboardStatsResponse stats = new DashboardStatsResponse();

        // FR7.1: Total feedback
        stats.setTotalFeedback(feedbackRepository.count());

        // FR7.2: Breakdown by status
        Map<String, Long> statusBreakdown = new LinkedHashMap<>();
        for (FeedbackStatus s : FeedbackStatus.values()) {
            statusBreakdown.put(s.name(), feedbackRepository.countByStatus(s));
        }
        stats.setStatusBreakdown(statusBreakdown);

        // FR7.3: Average rating
        Double avgRating = feedbackRepository.findAverageRating();
        stats.setAverageRating(avgRating != null ? Math.round(avgRating * 100.0) / 100.0 : 0.0);

        // FR7.4: Distribution by category
        Map<String, Long> categoryDist = new HashMap<>();
        List<Object[]> catCounts = feedbackRepository.countByCategory();
        for (Object[] row : catCounts) {
            categoryDist.put((String) row[0], (Long) row[1]);
        }
        stats.setCategoryDistribution(categoryDist);

        return stats;
    }

    private FeedbackResponse toResponse(Feedback feedback) {
        FeedbackResponse response = new FeedbackResponse();
        response.setId(feedback.getId());
        response.setSubmitterName(feedback.getSubmitterName());
        response.setSubmitterEmail(feedback.getSubmitterEmail());
        response.setFeedbackContent(feedback.getFeedbackContent());
        response.setRating(feedback.getRating());
        response.setStatus(feedback.getStatus());
        response.setSubmittedAt(feedback.getSubmittedAt());
        response.setUpdatedAt(feedback.getUpdatedAt());

        if (feedback.getCategory() != null) {
            response.setCategoryName(feedback.getCategory().getName());
            response.setCategoryId(feedback.getCategory().getId());
        }

        return response;
    }

    private Long getAdminId(String adminUsername) {
        Admin admin = adminRepository.findByUsername(adminUsername).orElse(null);
        return admin != null ? admin.getId() : 0L;
    }
}
