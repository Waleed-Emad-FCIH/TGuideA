package com.tga.Controller;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tga.models.FeedbackModel;

import java.util.ArrayList;

/**
 * Created by root on 3/9/18.
 */

public class FeedbackController implements DB_Interface{
    private FirebaseDatabase mRef = FirebaseDatabase.getInstance();
    DatabaseReference reference =mRef.getReference().child("FeedBacks");

    private FeedbackModel feedbackModel;


    public FeedbackController(String id, String subject, String content, String userId, boolean isComplain){
        this.feedbackModel = new FeedbackModel();
        feedbackModel.id = id;
        feedbackModel.subject = subject;
        feedbackModel.content = content;
        feedbackModel.userId = userId;
        feedbackModel.isComplain = isComplain;
    }

       @Override
  public void saveToDB() {
               reference.child(feedbackModel.id).setValue(feedbackModel);
       }
    public String getId() {
        return feedbackModel.id;
    }

    public String getSubject() {
        return feedbackModel.subject;
    }

    public String getContent() {
        return feedbackModel.content;
    }

    public boolean isComplain() {
        return feedbackModel.isComplain;
    }

    public String getUserId() {
        return feedbackModel.userId;
    }
    public void addFeedback(){

  saveToDB();

    /* no activity */
    }
    public void delFromDB(){};
    public void updateToDB(){};
    public ArrayList<Object> listAll(){
      return  listAll();
    };
}
