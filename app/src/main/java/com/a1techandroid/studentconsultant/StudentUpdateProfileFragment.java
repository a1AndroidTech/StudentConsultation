package com.a1techandroid.studentconsultant;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.a1techandroid.studentconsultant.Models.StudentProfileModel;
import com.a1techandroid.studentconsultant.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class StudentUpdateProfileFragment extends Fragment {
    EditText email, name, phone, ielts, ieltsScore, reading, writing, listening, speaking;
    UserModel userModel;
    CardView nextButton;
    CardView scorCard;
    DatabaseReference reference;
    FirebaseDatabase rootNode;
    ArrayList<UserModel> listofItem = new ArrayList<>();
    CheckBox checkBox;
    TextInputLayout ieltsScoreLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_profile_activity, container, false);
        rootNode= FirebaseDatabase.getInstance();
        reference=rootNode.getReference("StudentProfile");
        userModel = SharedPrefrences.getUser(getActivity());
        initViews(view);
        itemClicked();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpClick();
        checkValidty();
        setValues(userModel);
    }

    public void itemClicked() {
        //code to check if this checkbox is checked!
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox1 = (CheckBox)v;
                if(checkBox1.isChecked()){
                    checkBox.setChecked(true);
                    scorCard.setVisibility(View.VISIBLE);
                    ieltsScoreLayout.setVisibility(View.VISIBLE);
                }else {
                    checkBox.setChecked(false);
                    scorCard.setVisibility(View.GONE);
                    ieltsScoreLayout.setVisibility(View.GONE);
                }
            }
        });

    }

    public void initViews(View view){
        email=view.findViewById(R.id.email);
        name=view.findViewById(R.id.nameText);
        phone=view.findViewById(R.id.phone);
        ielts=view.findViewById(R.id.ielts);
        ieltsScore=view.findViewById(R.id.ieltsScoreTxt);
        reading=view.findViewById(R.id.readingScore);
        writing=view.findViewById(R.id.writingScore);
        speaking=view.findViewById(R.id.speakingScore);
        listening=view.findViewById(R.id.listeningScore);
        scorCard=view.findViewById(R.id.scorCard);
        scorCard.setVisibility(View.GONE);
        nextButton=view.findViewById(R.id.nextButton);
        checkBox=view.findViewById(R.id.item_check);
        ieltsScoreLayout=view.findViewById(R.id.ieltsScoreLayout);
    }


    public void setUpClick()
    {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           saveData();
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


    public void setValues(UserModel values){
        email.setEnabled(false);
        name.setEnabled(false);
        phone.setEnabled(false);
        email.setText(values.getEmail());
        name.setText(values.getName());
        phone.setText(values.getPhone());
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    public void saveData(){

        if (ielts.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Please Fill All Fields & Correct Value", Toast.LENGTH_SHORT).show();

        } else {
            Double number = Double.parseDouble(""+ielts.getText().toString());
            if (number > 5 ){
                Toast.makeText(getActivity(), "Please Fill Correct Value", Toast.LENGTH_SHORT).show();
            }else {
                if (checkBox.isChecked() && reading.getText().toString().equals("") && writing.getText().toString().equals("") && listening.getText().toString().equals("") && ieltsScore.getText().toString().equals("") && Double.parseDouble(ieltsScore.getText().toString()) > 9 ) {
                    Toast.makeText(getActivity(), "Please Fill All Ielts Score Fields", Toast.LENGTH_SHORT).show();

                } else {
                    reference.child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", "")).setValue(getPrfileModel()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                StudentQualificationFragment fragment = new StudentQualificationFragment();
                                replaceFragment(fragment);
                            } else {
                                Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

        }
    }


}
