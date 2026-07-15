package com.examly.springapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class FeedbackSubmissionRequest {

    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "Name must not contain special characters")
    private String submitterName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid format")
    private String submitterEmail;

    @NotBlank(message = "Feedback content is required")
    @Size(min = 1, max = 1000, message = "Feedback content must be between 1 and 1000 characters")
    private String feedbackContent;

    @Min(value = 0, message = "Rating must be between 0 and 5")
    @Max(value = 5, message = "Rating must be between 0 and 5")
    private int rating;

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
}
