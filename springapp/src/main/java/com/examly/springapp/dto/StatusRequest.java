package com.examly.springapp.dto;

import com.examly.springapp.model.FeedbackStatus;

public class StatusRequest {

    private FeedbackStatus status;

    public FeedbackStatus getStatus() {
        return status;
    }

    public void setStatus(FeedbackStatus status) {
        this.status = status;
    }
}