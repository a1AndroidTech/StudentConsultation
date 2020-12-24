package com.a1techandroid.studentconsultant.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.a1techandroid.studentconsultant.MainActivity;
import com.a1techandroid.studentconsultant.Models.ConsultantProfileModel;
import com.a1techandroid.studentconsultant.Models.StudentProfileModel;
import com.a1techandroid.studentconsultant.Models.UserModel;
import com.a1techandroid.studentconsultant.R;
import com.a1techandroid.studentconsultant.SharedPrefrences;
import com.a1techandroid.studentconsultant.StudentQualificationFragment;
import com.a1techandroid.studentconsultant.StudentViewFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ConsultantProfileMaking extends Fragment {
    EditText email, name, phone, ielts, ieltsScore, reading, writing, listening, speaking, lastOne;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefe;
    private DatabaseReference reference;
    UserModel userModel;
    CardView nextButton;
    boolean isReading = false, isWriting = false, isListening = false, isSpeakking= false, isPManagment = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.consultant_profile_making, container, false);
//        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance();
        reference=mDatabase.getReference("ConsultantProfile");
        mRefe=mDatabase.getReference("Consultant");
        userModel = SharedPrefrences.getUser(getActivity());
        initViews(view);
        setValues(userModel);

        if (userModel.getProfileStatus().equals("pending")){
            MainActivity.moreOption.setVisibility(View.GONE);
        }else {
            MainActivity.moreOption.setVisibility(View.GONE);

        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpClicks();
    }

    public void initViews(View view) {
        email = view.findViewById(R.id.email);
        name = view.findViewById(R.id.nameText);
        phone = view.findViewById(R.id.phone);
        ielts = view.findViewById(R.id.ielts);
        ieltsScore = view.findViewById(R.id.ieltsScoreTxt);
        reading = view.findViewById(R.id.readingScore);
        writing = view.findViewById(R.id.writingScore);
        speaking = view.findViewById(R.id.speakingScore);
        listening = view.findViewById(R.id.listeningScore);
        lastOne = view.findViewById(R.id.pManagement);
        nextButton = view.findViewById(R.id.nextButton);

    }

    public void setUpClicks(){
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            saveData();
            }
        });

        reading.setClickable(true);
        reading.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){
                    isReading = true;
                    isWriting = false;
                    isSpeakking = false;
                    isListening = false;
                    isPManagment = false;
                    setDateFormat();
                }
                return false;
            }
        });
        writing.setClickable(true);
        writing.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){
                    isReading = false;
                    isWriting = true;
                    isSpeakking = false;
                    isListening = false;
                    isPManagment = false;
                    setDateFormat();
                }
                return false;
            }
        });
        speaking.setClickable(true);
        speaking.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){
                    isReading = false;
                    isWriting = false;
                    isSpeakking = true;
                    isListening = false;
                    isPManagment = false;
                    setDateFormat();
                }
                return false;
            }
        });
        listening.setClickable(true);
        listening.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){
                    isReading = false;
                    isWriting = false;
                    isSpeakking = false;
                    isListening = true;
                    isPManagment = false;
                    setDateFormat();
                }
                return false;
            }
        });

         lastOne.setClickable(true);
        lastOne.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){
                    isReading = false;
                    isWriting = false;
                    isSpeakking = false;
                    isListening = false;
                    isPManagment = true;
                    setDateFormat();
                }
                return false;
            }
        });


    }

    public void setValues(UserModel values){
        email.setEnabled(false);
        name.setEnabled(false);
        phone.setEnabled(false);
        email.setText(values.getEmail());
        name.setText(values.getName());
        phone.setText(values.getPhone());
    }
    public void saveData(){

        if (ielts.getText().toString().equals("") && reading.getText().toString().equals("") && writing.getText().toString().equals("") && listening.getText().toString().equals("") && speaking.getText().toString().equals("") && lastOne.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Please Fill All Fields & Correct Value", Toast.LENGTH_SHORT).show();

        } else {
            Double number = Double.parseDouble(""+ielts.getText().toString());
//            if (number > 5 ){
//                Toast.makeText(getActivity(), "Please Fill Correct Value", Toast.LENGTH_SHORT).show();
//            }else {
//                if (Double.parseDouble(reading.getText().toString()) > 5 && ) {
//                    Toast.makeText(getActivity(), "Please Fill All Ielts Score Fields", Toast.LENGTH_SHORT).show();
//
//                } else {
                    reference.child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", "")).setValue(getPrfileModel()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                updateProfileStatus();
//                                StudentQualificationFragment fragment = new StudentQualificationFragment();
//                                replaceFragment(fragment);
                            } else {
                                Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
//            }
//            }
    }

    public ConsultantProfileModel getPrfileModel(){
        String nameString = name.getText().toString();
        String emailString = email.getText().toString();
        String phoneString = phone.getText().toString();
        String cgpaString = ielts.getText().toString();
        String ieltsScoreString = ieltsScore.getText().toString();
        String readingString = reading.getText().toString();
        String listeningString = listening.getText().toString();
        String speakingString = speaking.getText().toString();
        String wrtingString = writing.getText().toString();
        String pManagment = lastOne.getText().toString();
        ConsultantProfileModel model = new ConsultantProfileModel(emailString,nameString,phoneString,cgpaString,ieltsScoreString,readingString,listeningString,speakingString,wrtingString,pManagment, "Submitted");
        return model;
    }

    public void updateProfileStatus(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = mRefe.orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    Map<String, Object> postValues = new HashMap<String,Object>();
                    String key = appleSnapshot.getRef().getKey();
                    postValues.put("profileStatus", "Submitted");
                    UserModel model = appleSnapshot.getValue(UserModel.class);
                    model.setProfileStatus("Submitted");
                    mRefe.child(key).updateChildren(postValues);
                    SharedPrefrences.saveUSer(model, getActivity());
                    ConsultantProfileView fragment = new ConsultantProfileView();
                    replaceFragment(fragment);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setDateFormat(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        builderSingle.setTitle("Select One Value");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("1");
        arrayAdapter.add("2");
        arrayAdapter.add("3");
        arrayAdapter.add("4");
        arrayAdapter.add("5");



        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                if (isReading){
                    reading.setText(arrayAdapter.getItem(which));

                }else if (isListening){
                    listening.setText(arrayAdapter.getItem(which));

                }else if (isSpeakking){
                    speaking.setText(arrayAdapter.getItem(which));

                }else if (isWriting){
                    writing.setText(arrayAdapter.getItem(which));

                }else if (isPManagment){
                    lastOne.setText(arrayAdapter.getItem(which));

                }
            }
        });
        builderSingle.show();

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }



}
