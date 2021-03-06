package com.tga.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tga.Controller.ProgramController;
import com.tga.Controller.SimpleCallback;
import com.tga.Controller.SimpleSession;
import com.tga.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class EditProgram extends AppCompatActivity {

    private ImageView imgAddPlaces;
    private EditText imgProgramStart,imgProgramEnd, txtTitle, txtDescription, txtHotelName;
    private EditText txtPrice, txtMinNo, txtMaxNo;
    private LinearLayout llPrice, llMinNo, llMaxNo;
    private int mYear,mMonth,mDay;
    private Button btnUpdateProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_program);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Program");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));

        imgAddPlaces = (ImageView)findViewById(R.id.imgAddPlaces);
        imgProgramStart = (EditText)findViewById(R.id.imgProgramStart);
        imgProgramEnd =(EditText)findViewById(R.id.imgProgramEnd);
        btnUpdateProgram = (Button) findViewById(R.id.btnEditProgram);
        txtDescription = (EditText) findViewById(R.id.etxtProgramDesc);
        txtTitle = (EditText) findViewById(R.id.etxtProgramTitle);
        txtHotelName = (EditText) findViewById(R.id.txtPHotelName);
        txtPrice = (EditText) findViewById(R.id.txtPrice);
        txtMaxNo = (EditText) findViewById(R.id.txtMaxNo);
        txtMinNo = (EditText) findViewById(R.id.txtMinNo);
        llPrice = (LinearLayout) findViewById(R.id.llPrice);
        llMinNo = (LinearLayout) findViewById(R.id.llMinNo);
        llMaxNo = (LinearLayout) findViewById(R.id.llMaxNo);

        if (SimpleSession.isNull()){
            Toast.makeText(getApplicationContext(), "Session ended", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Login.class));
            finish();
        }
        SimpleSession session = SimpleSession.getInstance();

        String progID = getIntent().getStringExtra("PROG_ID");
        ProgramController.getByID(new SimpleCallback<ProgramController>() {
            @Override
            public void callback(final ProgramController pc) {
                imgProgramStart.setText(pc.getStartDate());
                imgProgramEnd.setText(pc.getEndDate());
                txtTitle.setText(pc.getTitle());
                txtDescription.setText(pc.getDescription());
                txtHotelName.setText(pc.getHotelName());
                if (session.getUserRole() == SimpleSession.AGENT_ROLE){
                    llPrice.setVisibility(View.VISIBLE);
                    llMaxNo.setVisibility(View.VISIBLE);
                    llMinNo.setVisibility(View.VISIBLE);
                    txtPrice.setText(String.valueOf(pc.getPrice()));
                    txtMinNo.setText(String.valueOf(pc.getMinNo()));
                    txtMaxNo.setText(String.valueOf(pc.getMaxNo()));
                }

                imgAddPlaces.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),AllPlaces.class);
                        startActivity(intent);
                    }
                });

                imgProgramStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar mcurrentDate = Calendar.getInstance();
                        mYear = mcurrentDate.get(Calendar.YEAR);
                        mMonth = mcurrentDate.get(Calendar.MONTH);
                        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog mDatePicker = new DatePickerDialog(EditProgram.this, new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                                Calendar myCalendar = Calendar.getInstance();
                                myCalendar.set(Calendar.YEAR, selectedyear);
                                myCalendar.set(Calendar.MONTH, selectedmonth);
                                myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                                String myFormat = "dd/MM/yy"; //Change as you need
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                                imgProgramStart.setText(sdf.format(myCalendar.getTime()));

                                mDay = selectedday;
                                mMonth = selectedmonth;
                                mYear = selectedyear;
                            }
                        }, mYear, mMonth, mDay);
                        //mDatePicker.setTitle("Select date");
                        mDatePicker.show();
                    }
                });

                imgProgramEnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar mcurrentDate = Calendar.getInstance();
                        mYear = mcurrentDate.get(Calendar.YEAR);
                        mMonth = mcurrentDate.get(Calendar.MONTH);
                        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog mDatePicker = new DatePickerDialog(EditProgram.this, new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                                Calendar myCalendar = Calendar.getInstance();
                                myCalendar.set(Calendar.YEAR, selectedyear);
                                myCalendar.set(Calendar.MONTH, selectedmonth);
                                myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                                String myFormat = "dd/MM/yy"; //Change as you need
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                                imgProgramEnd.setText(sdf.format(myCalendar.getTime()));

                                mDay = selectedday;
                                mMonth = selectedmonth;
                                mYear = selectedyear;
                            }
                        }, mYear, mMonth, mDay);
                        //mDatePicker.setTitle("Select date");
                        mDatePicker.show();
                    }
                });

                btnUpdateProgram.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        if (!checkText()) {
                            pc.editProgram(txtTitle.getText().toString(), txtDescription.getText().toString(),
                                    imgProgramStart.getText().toString(), imgProgramEnd.getText().toString(),
                                    txtHotelName.getText().toString());
                            if (session.getUserRole() == SimpleSession.AGENT_ROLE){
                                if (!checkAgentText()) {
                                    pc.setPrice(Double.parseDouble(txtPrice.getText().toString()));
                                    pc.setMinNo(Integer.parseInt(txtMinNo.getText().toString()));
                                    pc.setMaxNo(Integer.parseInt(txtMaxNo.getText().toString()));
                                } else {
                                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                            pc.updateToDB();
                            Toast.makeText(getApplicationContext(), "Successfully updated", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }, progID);
    }

    private boolean checkText(){
        return txtTitle.getText().toString().isEmpty() || txtDescription.getText().toString().isEmpty() ||
                txtHotelName.getText().toString().isEmpty() || imgProgramStart.getText().toString().isEmpty() ||
                imgProgramEnd.getText().toString().isEmpty() ;
    }

    private boolean checkAgentText(){
        return txtPrice.getText().toString().isEmpty() || txtMinNo.getText().toString().isEmpty() ||
                txtMaxNo.getText().toString().isEmpty();
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
