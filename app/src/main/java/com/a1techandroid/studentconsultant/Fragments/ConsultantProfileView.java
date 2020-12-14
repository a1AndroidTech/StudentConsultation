package com.a1techandroid.studentconsultant.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.a1techandroid.studentconsultant.ConsultantProfile;
import com.a1techandroid.studentconsultant.Models.ConsultantProfileModel;
import com.a1techandroid.studentconsultant.Models.StudentProfileModel;
import com.a1techandroid.studentconsultant.Models.UserModel;
import com.a1techandroid.studentconsultant.R;
import com.a1techandroid.studentconsultant.SharedPrefrences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConsultantProfileView extends Fragment {
    TextView nameTxt, emailTxt, phoneTxt, cgpaText, ieltsTxt;
    TextView readingTx, listeningTx, writingTx, speakingTx, pManagment;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefe;
    private DatabaseReference reference;
    ConsultantProfileModel model;
    UserModel userModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.consultant_profile_view, container, false);
//        mAuth = FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();
        reference=mDatabase.getReference("ConsultantProfile");
        userModel = SharedPrefrences.getUser(getActivity());
        initView(view);
        readValueFromFireBase();
        return view;
    }

    public void initView(View view) {
        nameTxt = view.findViewById(R.id.nameet);
        emailTxt = view.findViewById(R.id.emaiet);
        phoneTxt = view.findViewById(R.id.phonEt);
        cgpaText = view.findViewById(R.id.cgpaEt);
        ieltsTxt = view.findViewById(R.id.ieltsEt);


        readingTx = view.findViewById(R.id.readingEt);
        writingTx = view.findViewById(R.id.writingEt);
        speakingTx = view.findViewById(R.id.speakingEt);
        listeningTx = view.findViewById(R.id.listeningEt);
        pManagment = view.findViewById(R.id.pManagment);

    }

    public void readValueFromFireBase(){


        reference.child(userModel.getEmail().replace(".","")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model=snapshot.getValue(ConsultantProfileModel.class);

                setValue(model);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setValue(ConsultantProfileModel uni_model) {


        nameTxt.setText(uni_model.getName());
        emailTxt.setText(uni_model.getEmail());
        phoneTxt.setText(uni_model.getPhone());
        cgpaText.setText(uni_model.getNumberOfYear());
        ieltsTxt.setText(uni_model.getDescription());
        readingTx.setText(uni_model.getMarkeeting());
        listeningTx.setText(uni_model.getOrgSkill());
        writingTx.setText(uni_model.getMarkeetSearch());
        speakingTx.setText(uni_model.getCommunSkill());
        pManagment.setText(uni_model.getpManagment());

    }

}
