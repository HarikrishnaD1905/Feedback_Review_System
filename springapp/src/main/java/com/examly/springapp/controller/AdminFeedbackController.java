package com.examly.springapp.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.CategoryAssignRequest;
import com.examly.springapp.dto.DashboardStatsResponse;
import com.examly.springapp.dto.FeedbackResponse;
import com.examly.springapp.dto.StatusRequest;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.FeedbackStatus;
import com.examly.springapp.service.FeedbackService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminFeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    /**
     * FR3: Get all feedback — paginated, sortable, filterable.
     * FR11: Pagination with configurable page size and number.
     * FR11.3: Sortable by submittedAt (default newest first), rating, status.
     * FR3.3: Filters by name, email, rating, status, category.
     */
    @GetMapping("/feedback")
    public ResponseEntity<Page<FeedbackResponse>> getAllFeedback(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "submittedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) FeedbackStatus status,
            @RequestParam(required = false) Long categoryId) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<FeedbackResponse> feedbackPage = feedbackService.getAllFeedback(
                pageable, name, email, rating, status, categoryId);

        return new ResponseEntity<>(feedbackPage, HttpStatus.OK);
    }

    /**
     * FR3.4: View full details of a single feedback submission.
     */
    @GetMapping("/feedback/{id}")
    public ResponseEntity<FeedbackResponse> getFeedbackById(@PathVariable Long id) {
        FeedbackResponse response = feedbackService.getFeedbackById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * FR4: Update feedback status.
     * FR4.2: Statuses are NEW, IN_PROGRESS, RESOLVED, ARCHIVED.
     * FR4.4: Audit log entry is created.
     */
    @PutMapping("/feedback/{id}/status")
    public ResponseEntity<FeedbackResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusRequest request,
            Principal principal) {

        if (request == null || request.getStatus() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Feedback updated = feedbackService.updateStatus(id, request.getStatus(), principal.getName());
        FeedbackResponse response = feedbackService.getFeedbackById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * FR6.2: Assign a category to a feedback submission.
     */
    @PutMapping("/feedback/{id}/category")
    public ResponseEntity<FeedbackResponse> assignCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryAssignRequest request,
            Principal principal) {

        feedbackService.assignCategory(id, request.getCategoryId(), principal.getName());
        FeedbackResponse response = feedbackService.getFeedbackById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * FR5: Delete feedback.
     * FR5.4: Audit log entry is created.
     */
    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id, Principal principal) {
        feedbackService.deleteFeedback(id, principal.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * FR7: Admin Dashboard stats.
     */
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardStatsResponse> getDashboardStats() {
        DashboardStatsResponse stats = feedbackService.getDashboardStats();
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}
