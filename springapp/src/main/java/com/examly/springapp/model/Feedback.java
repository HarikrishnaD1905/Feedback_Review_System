package com.examly.springapp.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "Name must not contain special characters")
    @Column(name = "submitter_name")
    private String submitterName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid format")
    @Column(name = "submitter_email", nullable = false)
    private String submitterEmail;

    @NotBlank(message = "Feedback content is required")
    @Size(min = 1, max = 1000, message = "Feedback content must be between 1 and 1000 characters")
    @Column(name = "feedback_content", columnDefinition = "TEXT", nullable = false)
    private String feedbackContent;

    @Min(value = 0, message = "Rating must be between 0 and 5")
    @Max(value = 5, message = "Rating must be between 0 and 5")
    private int rating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeedbackStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Feedback() {}

    public Feedback(String submitterName, String submitterEmail, String feedbackContent, int rating) {
        this.submitterName = submitterName;
        this.submitterEmail = submitterEmail;
        this.feedbackContent = feedbackContent;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubmitterName() {
        return submitterName;
    }

    public void setSubmitterName(String submitterName) {
        this.submitterName = submitterName;
    }

    public String getSubmitterEmail() {
        return submitterEmail;
    }

    public void setSubmitterEmail(String submitterEmail) {
        this.submitterEmail = submitterEmail;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public FeedbackStatus getStatus() {
        return status;
    }

    public void setStatus(FeedbackStatus status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
