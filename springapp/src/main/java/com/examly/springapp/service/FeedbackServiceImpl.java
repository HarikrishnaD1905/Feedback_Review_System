package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

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
        Optional<Feedback> fb=frepo.findById(id);
        if(fb.isPresent()){
            Feedback f=fb.get();
            f.setStatus(status);
            return frepo.save(f);
            
        }
        return null;
    }

    @Override
    public boolean deleteFeedback(Long id){
        Optional<Feedback> fb=frepo.findById(id);
        if(fb.isPresent()){
            frepo.delete(fb.get());   
            return true;         
        }
        return false;
    }
}
