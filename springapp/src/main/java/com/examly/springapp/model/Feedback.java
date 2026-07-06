package com.examly.springapp.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Feedback {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userid;
    private String productid;
    private int rating;
    private String comment;

    @Enumerated(EnumType.STRING)
    private FeedbackStatus status;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAt;

    public Feedback(){}

    public Feedback(String userid,String productid, int rating, String comment){
        this.userid=userid;
        this.productid=productid;
        this.rating=rating;
        this.comment=comment;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public String getUserId(){
        return userid;
    }

    public void setUserId(String userid){
        this.userid=userid;
    }

    public String getProductId(){
        return productid;
    }

    public void setProductId(String productid){
        this.productid=productid;
    }

    public int getRating(){
        return rating;
    }

    public void setRating(int rating){
        this.rating=rating;
    }

    public String getComment(){
        return comment;
    }

    public void setComment(String comment){
        this.comment=comment;
    }

    public FeedbackStatus getStatus(){
        return status;
    }

    public void setStatus(FeedbackStatus status){
        this.status=status;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt=createdAt;
    }
}
