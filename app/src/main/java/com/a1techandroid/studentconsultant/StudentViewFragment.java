package com.a1techandroid.studentconsultant;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.a1techandroid.studentconsultant.Adapters.Univeristy_adapter;
import com.a1techandroid.studentconsultant.Models.QualificationModel;
import com.a1techandroid.studentconsultant.Models.RequestModel;
import com.a1techandroid.studentconsultant.Models.StudentProfileModel;
import com.a1techandroid.studentconsultant.Models.Uni_Model;
import com.a1techandroid.studentconsultant.Models.UserModel;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StudentViewFragment extends Fragment {
    TextView nameTxt, emailTxt, phoneTxt, cgpaText, ieltsTxt;
    TextView readingTx, listeningTx, writingTx, speakingTx;
    TextView sscBoard, sscSubject, sscGrade, sscYear;
    TextView hsscBoard, hsscSubject, hsscGrade, hsscYear;
    TextView baBoard, baSubject, baGrade, baYear;
    TextView maBoard, maSubject, maGrade, maYear;

    TextView applyUni, applyScholorship, status;

    DatabaseReference reference;
    DatabaseReference reference1;
    DatabaseReference reference2;
    DatabaseReference reference3;
    FirebaseDatabase rootNode;
    StudentProfileModel uni_model;
    UserModel userModel;
    ArrayList<StudentProfileModel> dataList = new ArrayList();
    ArrayList<QualificationModel> modelList = new ArrayList<>();
    ImageView ssc, hssc, ba, ma, passport;
    RequestModel model1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_profile_view, container, false);
        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("StudentProfile");
        reference1=rootNode.getReference("StudentProfileQu");
        reference2=rootNode.getReference("requests");
        reference3=rootNode.getReference("Student");
        userModel = SharedPrefrences.getUser(getActivity());
        initView(view);
        readValueFromFireBase();

        if (!FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("admin@gmail.com")){
            if (userModel.getProfileStatus().equals("pending")){
                MainActivity.moreOption.setVisibility(View.GONE);
            }else {
                MainActivity.moreOption.setVisibility(View.VISIBLE);

            }
        }


        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    public void initView(View view){
    nameTxt = view.findViewById(R.id.nameet);
    emailTxt = view.findViewById(R.id.emaiet);
    phoneTxt = view.findViewById(R.id.phonEt);
    cgpaText = view.findViewById(R.id.cgpaEt);
    ieltsTxt = view.findViewById(R.id.ieltsEt);


    readingTx = view.findViewById(R.id.readingEt);
    writingTx = view.findViewById(R.id.writingEt);
    speakingTx = view.findViewById(R.id.speakingEt);
    listeningTx = view.findViewById(R.id.listeningEt);

        sscBoard = view.findViewById(R.id.sscBoard);
        sscGrade = view.findViewById(R.id.sscGrade);
        sscSubject = view.findViewById(R.id.sscprogram);
        sscYear = view.findViewById(R.id.sscMarks);

        hsscBoard = view.findViewById(R.id.hsscBoard);
        hsscGrade = view.findViewById(R.id.hsscGrade);
        hsscSubject = view.findViewById(R.id.hsscprogram);
        hsscYear = view.findViewById(R.id.hsscMarks);

        baBoard = view.findViewById(R.id.baBoard);
        baGrade = view.findViewById(R.id.baGrade);
        baSubject = view.findViewById(R.id.baprogram);
        baYear = view.findViewById(R.id.baMarks);

        maBoard = view.findViewById(R.id.maBoard);
        maGrade = view.findViewById(R.id.maGrade);
        maSubject = view.findViewById(R.id.maprogram);
        maYear = view.findViewById(R.id.maMarks);

        applyUni = view.findViewById(R.id.uniName);
        applyScholorship = view.findViewById(R.id.schName);
        status = view.findViewById(R.id.status);

        ssc = view.findViewById(R.id.ssc);
        hssc = view.findViewById(R.id.hssc);
        ba = view.findViewById(R.id.ba);
        ma = view.findViewById(R.id.ma);
        passport = view.findViewById(R.id.passport);


    }

    public void setValue(StudentProfileModel uni_model){


                nameTxt.setText(uni_model.getName());
                emailTxt.setText(uni_model.getEmail());
                phoneTxt.setText(uni_model.getPhone());
                cgpaText.setText(uni_model.getCgpa());
                ieltsTxt.setText(uni_model.getIeltsScore());
                readingTx.setText(uni_model.getReading());
                listeningTx.setText(uni_model.getListneing());
                writingTx.setText(uni_model.getWriting());
                speakingTx.setText(uni_model.getSpeaking());
                reference1.child(userModel.getEmail().replace(".","")).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        QualificationModel  model=snapshot.getValue(QualificationModel.class);
                        modelList.add(model);
                        setValues2(modelList);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        QualificationModel  model=snapshot.getValue(QualificationModel.class);
                        modelList.add(model);
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




    }

    public void readValueFromFireBase(){

//        Query applesQuery = reference.orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
//                    uni_model = appleSnapshot.getValue(StudentProfileModel.class);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
        reference.child(userModel.getEmail().replace(".","")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uni_model=snapshot.getValue(StudentProfileModel.class);
//                officers.setUid(snapshot.getKey());
//                dataList.add(uni_model);
                setValue(uni_model);
                getUSerDetail();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        reference.child(userModel.getEmail().replace(".","")).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                uni_model=snapshot.getValue(StudentProfileModel.class);
////                officers.setUid(snapshot.getKey());
////                dataList.add(uni_model);
//                setValue(uni_model);
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                uni_model=snapshot.getValue(StudentProfileModel.class);
////                officers.setUid(snapshot.getKey());
//               setValue(uni_model);
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });



//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                uni_model=snapshot.getValue(StudentProfileModel.class);
//                setValue(uni_model);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//

    }

    public void setValues2(ArrayList<QualificationModel> model){

        for (int i =0; i<model.size(); i++){
            if (i == 0){
                sscBoard.setText(model.get(0).getProgram());
                sscYear.setText(model.get(0).getPassingyear());
                sscSubject.setText(model.get(0).getSubject());
                sscGrade.setText(model.get(0).getGrade());
            }else if (i ==1){
                hsscBoard.setText(model.get(1).getProgram());
                hsscYear.setText(model.get(1).getPassingyear());
                hsscSubject.setText(model.get(1).getSubject());
                hsscGrade.setText(model.get(1).getGrade());
            }else  if (i ==2){
                baBoard.setText(model.get(2).getProgram());
                baYear.setText(model.get(2).getPassingyear());
                baSubject.setText(model.get(2).getSubject());
                baGrade.setText(model.get(2).getGrade());
            }else if (i == 3){
                maBoard.setText(model.get(3).getProgram());
                maYear.setText(model.get(3).getPassingyear());
                maSubject.setText(model.get(3).getSubject());
                maGrade.setText(model.get(3).getGrade());
            }
        }

        reference2.child(userModel.getEmail().replace(".","")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model1= snapshot.getValue(RequestModel.class);
                if (model1 != null) {
                    applyUni.setText(model1.getUniName());
                    applyScholorship.setText(model1.getScName());
                    loadPictures(model1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void loadPictures(RequestModel model){

        if (model.getSsc() != ""){
            Glide.with(getActivity())
                    .load(model.getSsc())
                    .centerCrop()
                    .into(ssc);
        }


            if (model.getHssc() != ""){
            Glide.with(getActivity())
                    .load(model.getHssc())
                    .centerCrop()
                    .into(hssc);
        }

            if (model.getBa() != ""){
            Glide.with(getActivity())
                    .load(model.getBa())
                    .centerCrop()
                    .into(ba);
        }

            if (model.getMa() != ""){
            Glide.with(getActivity())
                    .load(model.getMa())
                    .centerCrop()
                    .into(ma);
        }

            if (model.getPassport() != ""){
            Glide.with(getActivity())
                    .load(model.getPassport())
                    .centerCrop()
                    .into(passport);
        }
    }

    public void getUSerDetail(){
        reference3.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserModel userModel1 = snapshot.getValue(UserModel.class);

                if (userModel1.getEmail().equals(userModel.getEmail())){
                    status.setText(userModel1.getProfileStatus());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
