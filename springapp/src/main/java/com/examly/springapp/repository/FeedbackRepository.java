package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.FeedbackStatus;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>, JpaSpecificationExecutor<Feedback> {

    List<Feedback> findBySubmitterEmail(String submitterEmail);

    Page<Feedback> findByStatus(FeedbackStatus status, Pageable pageable);

    Page<Feedback> findByCategoryId(Long categoryId, Pageable pageable);

    long countByStatus(FeedbackStatus status);

    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.rating > 0")
    Double findAverageRating();

    @Query("SELECT f.category.name, COUNT(f) FROM Feedback f WHERE f.category IS NOT NULL GROUP BY f.category.name")
    List<Object[]> countByCategory();
}
