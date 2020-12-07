package com.a1techandroid.studentconsultant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.a1techandroid.studentconsultant.Adapters.Univeristy_adapter;
import com.a1techandroid.studentconsultant.Models.StudentProfileModel;
import com.a1techandroid.studentconsultant.Models.Uni_Model;
import com.a1techandroid.studentconsultant.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class StudentProfileActivity extends AppCompatActivity {
    EditText email, name, phone, ielts, ieltsScore, reading, writing, listening, speaking;
    UserModel userModel;
    CardView nextButton;
    CardView scorCard;
    DatabaseReference reference;
    FirebaseDatabase rootNode;
    ArrayList<UserModel> listofItem = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_profile_activity);
        rootNode= FirebaseDatabase.getInstance();
        reference=rootNode.getReference("SC");
        initViews();
        setUpClick();
        readValueFromFirebase();
        enableieltsField();
        checkValidty();
    }

    public void itemClicked(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox)v;
        if(checkBox.isChecked()){
            checkBox.setChecked(true);
            scorCard.setVisibility(View.VISIBLE);
        }else {
            checkBox.setChecked(false);
            scorCard.setVisibility(View.GONE);
        }
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
        scorCard=findViewById(R.id.scorCard);
        scorCard.setVisibility(View.GONE);
//        reading.setEnabled(false);
//        writing.setEnabled(false);
//        speaking.setEnabled(false);
//        listening.setEnabled(false);
        nextButton=findViewById(R.id.nextButton);
//        nextButton.setEnabled(false);
    }


    public void setUpClick()
    {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(StudentProfileActivity.this, StudentQualificationActivity.class);
                Gson gson = new Gson();
                String profileModel = gson.toJson(getPrfileModel());
                i.putExtra("model", profileModel);
                startActivity(i);

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

    public StudentProfileModel getPrfileModel(){
        String nameString = name.getText().toString();
        String emailString = email.getText().toString();
        String phoneString = phone.getText().toString();
        String cgpaString = ielts.getText().toString();
        String ieltsScoreString = ieltsScore.getText().toString();
        String readingString = reading.getText().toString();
        String listeningString = listening.getText().toString();
        String speakingString = speaking.getText().toString();
        String wrtingString = writing.getText().toString();
        StudentProfileModel model = new StudentProfileModel(emailString, nameString, phoneString,  cgpaString, ieltsScoreString,readingString, listeningString, speakingString, wrtingString);
        return model;
    }

    public void readValueFromFirebase(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("SC").child("User").orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    UserModel userModel = appleSnapshot.getValue(UserModel.class);
                    setValues(userModel);
//                            ordersLists.remove(order);
//                            notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setValues(UserModel values){
        email.setText(values.getEmail());
        name.setText(values.getName());
        phone.setText(values.getPhone());
    }
}
