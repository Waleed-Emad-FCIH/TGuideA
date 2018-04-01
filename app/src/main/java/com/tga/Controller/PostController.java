package com.tga.Controller;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tga.models.CommentModel;
import com.tga.models.PostModel;

import java.util.ArrayList;

/**
 * Created by root on 3/9/18.
 */

public class PostController {
    
    private PostModel postModel; 
    
    public PostController(String id, String content, String date, String userId,
                          ArrayList<String> commentsID){
        this.postModel = new PostModel();
        postModel.id = id;
        postModel.content = content;
        postModel.date = date;
        postModel.userId = userId;
        postModel.commentsID = commentsID;
        this.create();
    }

    public void create()
    {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("posts");

        postModel.id = dRef.push().getKey();
        dRef.child(postModel.id).setValue(postModel);
    }
    public void update()
    {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("posts");
        dRef.child(postModel.id).setValue(postModel);
        dRef.child((postModel.id)).child("commentsID").setValue(postModel.commentsID);

    }
    public String getId() {
        return postModel.id;
    }

    public void setId(String id) {
        postModel.id = id;
    }

    public String getContent() {
        return postModel.content;
    }

    public void setContent(String content) {

        postModel.content = content;
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("posts");
        dRef.child(getId()).child("content").setValue(postModel.content);
    }

    public String getDate() {
        return postModel.date;
    }

    public String getUserId() {
        return postModel.userId;
    }

    public ArrayList<String> getComments()
    {

        return postModel.commentsID;
    }

    public void addComment(String commentID) {
        postModel.commentsID.add(commentID);
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef=fd.getReference("posts");
        dRef.child(getId()).child("commentsID").setValue(postModel.commentsID);

    }

    public void delCommentId(String comntID){

        postModel.commentsID.remove(comntID);
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("posts");
        dRef.child(getId()).child(getId()).setValue(postModel.commentsID);
    }
    //
    public void editComment(String comntID){ }

    public void delPost(String postId) {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("posts");
        dRef.child(postId).removeValue();
    }

    public void editPost() {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("posts");
        dRef.child(getId()).child("content").setValue(postModel.content);
    }
    
    private class Comment {
        private CommentModel commentModel;

        public Comment( String id , String content, String date, String userId ){
            commentModel.id = id;
            commentModel.content = content;
            commentModel.date = date;
            commentModel.userId = userId;

        }
        public void createComment()
        {
            FirebaseDatabase fd = FirebaseDatabase.getInstance();
            DatabaseReference dRef = fd.getReference("comments");
            commentModel.id = dRef.push().getKey();
            dRef.child(commentModel.id).setValue(commentModel);
            //add comment Id to post comments Id
            addComment(commentModel.id);

        }

        public String getId() {
            return commentModel.id;
        }

        public String getContent() {
            return commentModel.content;
        }

        public void setContent(String content) {
            commentModel.content = content;
        }

        public String getDate() {
            return commentModel.date;
        }

        public String getUserId() {
            return commentModel.userId;
        }

        public void delComment(){
            FirebaseDatabase fd = FirebaseDatabase.getInstance();
            DatabaseReference dRef = fd.getReference("comments");
            dRef.child(commentModel.id).removeValue();
            //delete comment id from post comments id
            delCommentId(commentModel.id);
        }

        public void editComment( String newCommentContent) {
            FirebaseDatabase fd = FirebaseDatabase.getInstance();
            DatabaseReference dRef = fd.getReference("comments");
            setContent(newCommentContent);
            dRef.child(commentModel.id).setValue(commentModel);

        }
    }
}
