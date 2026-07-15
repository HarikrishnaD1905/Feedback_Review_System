package com.examly.springapp.dto;

import java.util.Map;

public class DashboardStatsResponse {

    private long totalFeedback;
    private Map<String, Long> statusBreakdown;
    private double averageRating;
    private Map<String, Long> categoryDistribution;

    public DashboardStatsResponse() {}

    public long getTotalFeedback() {
        return totalFeedback;
    }

    public void setTotalFeedback(long totalFeedback) {
        this.totalFeedback = totalFeedback;
    }

    public Map<String, Long> getStatusBreakdown() {
        return statusBreakdown;
    }

    public void setStatusBreakdown(Map<String, Long> statusBreakdown) {
        this.statusBreakdown = statusBreakdown;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public Map<String, Long> getCategoryDistribution() {
        return categoryDistribution;
    }

    public void setCategoryDistribution(Map<String, Long> categoryDistribution) {
        this.categoryDistribution = categoryDistribution;
    }
}
