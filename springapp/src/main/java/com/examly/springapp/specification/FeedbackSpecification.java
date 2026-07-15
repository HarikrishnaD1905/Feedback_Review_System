package com.examly.springapp.specification;

import org.springframework.data.jpa.domain.Specification;

import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.FeedbackStatus;

public class FeedbackSpecification {

    private FeedbackSpecification() {}

    public static Specification<Feedback> hasSubmitterName(String name) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("submitterName")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Feedback> hasSubmitterEmail(String email) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("submitterEmail")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<Feedback> hasRating(Integer rating) {
        return (root, query, cb) ->
                cb.equal(root.get("rating"), rating);
    }

    public static Specification<Feedback> hasStatus(FeedbackStatus status) {
        return (root, query, cb) ->
                cb.equal(root.get("status"), status);
    }

    public static Specification<Feedback> hasCategoryId(Long categoryId) {
        return (root, query, cb) ->
                cb.equal(root.get("category").get("id"), categoryId);
    }
}
