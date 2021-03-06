package com.tga.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tga.Controller.PostController;
import com.tga.Controller.SimpleCallback;
import com.tga.R;
import com.tga.adapter.PostAdapter;
import com.tga.models.PostModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Community2 extends AppCompatActivity {

    private PostController posts;
    private PostAdapter pAdapter;
    private RecyclerView recyclerView;
    FirebaseUser user ;
    private FloatingActionButton fab;
    private LinearLayout layoutPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Community");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_posts);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        user = FirebaseAuth.getInstance().getCurrentUser();
        String timeStamp = new SimpleDateFormat("dd/MM/yy").format(Calendar.getInstance().getTime());

        posts= new PostController(null,null, timeStamp
                ,user.getUid(),null,0,null);
        posts.listAll(new SimpleCallback<ArrayList<PostModel>>() {
            @Override
            public void callback(ArrayList<PostModel> data) {
                pAdapter = new PostAdapter(getApplicationContext(),data);
                recyclerView.setAdapter(pAdapter);

            }
        });

        fab =(FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddPost2.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
