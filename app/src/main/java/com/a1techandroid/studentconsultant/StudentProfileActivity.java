package com.a1techandroid.studentconsultant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.a1techandroid.studentconsultant.Models.UserModel;

public class StudentProfileActivity extends AppCompatActivity {
    EditText email, name, phone, ielts, ieltsScore, reading, writing, listening, speaking;
    UserModel userModel;
    CardView nextButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_profile_activity);
        initViews();
        getData();
        setUpClick();
        enableieltsField();
        checkValidty();
    }

    public void initViews(){
        email=findViewById(R.id.email);
        name=findViewById(R.id.nameText);
        phone=findViewById(R.id.phone);
        ielts=findViewById(R.id.ielts);
        ieltsScore=findViewById(R.id.ieltsScoreTxt);
        reading=findViewById(R.id.readingScore);
        writing=findViewById(R.id.writingScore);
        speaking=findViewById(R.id.speakingScore);
        listening=findViewById(R.id.listeningScore);
//        reading.setEnabled(false);
//        writing.setEnabled(false);
//        speaking.setEnabled(false);
//        listening.setEnabled(false);
        nextButton=findViewById(R.id.nextButton);
//        nextButton.setEnabled(false);
    }

    public void getData(){
        userModel= SharedPrefrences.getUser(getApplicationContext());
        email.setText(userModel.getEmail());
        name.setText(userModel.getName());
        phone.setText(userModel.getPhone());
    }

    public void setUpClick()
    {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentProfileActivity.this, StudentQualificationActivity.class));
            }
        });
    }

    public void enableieltsField(){
        if (ielts.getText().toString().equals("yes")){
            reading.setEnabled(true);
            writing.setEnabled(true);
            speaking.setEnabled(true);
            listening.setEnabled(true);
        }
    }

    public void checkValidty(){
        if (email.getText().toString().isEmpty() && name.getText().toString().isEmpty() && phone.getText().toString().isEmpty() && phone.getText().toString().isEmpty() && phone.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }else {
            nextButton.setEnabled(true);
        }
    }
}
