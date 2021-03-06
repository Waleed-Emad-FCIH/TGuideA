package com.tga.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.tga.R;
import com.tga.adapter.AgentAdapter;
import com.tga.adapter.CustomAdapter;
import com.tga.models.AgentModel;
import com.tga.models.ChatMessage;

import java.util.ArrayList;

public class MyMessage extends AppCompatActivity {
    private ListView mUsersList;
    private DatabaseReference mDatabaseRefernce;
    private ProgressDialog mPrgress;
    private AgentAdapter mAdapter;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Message ");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));
String s = getIntent().getStringExtra("key");
if(s.equals("Agents"))
        mDatabaseRefernce = FirebaseDatabase.getInstance().getReference().child("Agents");
       else
    mDatabaseRefernce = FirebaseDatabase.getInstance().getReference().child("tourists");

        mUsersList = (ListView)findViewById(R.id.users_list);
        mPrgress= new ProgressDialog(this);

        mAdapter = new AgentAdapter(this,Retrieve());
        mUsersList.setAdapter(mAdapter);

    }
    ArrayList<AgentModel> Retrieve(){
        ArrayList < AgentModel > agents = new ArrayList<>();

        mDatabaseRefernce.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {

      if (dataSnapshot.hasChildren())
      {

          for(DataSnapshot myitem:dataSnapshot.getChildren()) {
              AgentModel agentModel = new AgentModel();
              agentModel.setName(myitem.child("name").getValue().toString());
              //agentModel.setCompany(myitem.child("company").getValue().toString());
              agentModel.setId(myitem.child("id").getValue().toString());
              agents.add(agentModel);
          }
      }
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                        Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_LONG).show();

                                                    }
                                                });
             return agents;


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
