package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.FeedbackStatus;
import com.examly.springapp.repository.FeedbackRepository;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository frepo;

    @Override
    public Feedback addFeedback(Feedback fb){
        fb.setStatus(FeedbackStatus.PENDING);
        return frepo.save(fb);
    }

    @Override
    public List<Feedback> getAllFeedback(){
        return frepo.findAll();
    }

    @Override
    public List<Feedback> getUserFeedback(String userid){
        List<Feedback> list=frepo.findByUserid(userid);
        for(Feedback fb: list){
            fb.setStatus(FeedbackStatus.APPROVED);
        }
        return list;
    }

    @Override
    public Feedback updateStatus(Long id, FeedbackStatus status){
        Feedback fb=frepo.findById(id).orElseThrow();
        fb.setStatus(status);
        return frepo.save(fb);
    }

    @Override
    public void deleteFeedback(Long id){
        frepo.deleteById(id);
    }
}
