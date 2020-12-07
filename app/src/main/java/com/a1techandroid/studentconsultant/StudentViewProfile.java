package com.a1techandroid.studentconsultant;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.a1techandroid.studentconsultant.Adapters.Univeristy_adapter;
import com.a1techandroid.studentconsultant.Models.QualificationModel;
import com.a1techandroid.studentconsultant.Models.StudentProfileModel;
import com.a1techandroid.studentconsultant.Models.Uni_Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentViewProfile extends AppCompatActivity {
    TextView nameTxt, emailTxt, phoneTxt, cgpaText, ieltsTxt;
    TextView readingTx, listeningTx, writingTx, speakingTx;
    TextView sscBoard, sscSubject, sscGrade, sscYear;
    TextView hsscBoard, hsscSubject, hsscGrade, hsscYear;
    TextView baBoard, baSubject, baGrade, baYear;
    TextView maBoard, maSubject, maGrade, maYear;

    DatabaseReference reference;
    DatabaseReference reference1;
    FirebaseDatabase rootNode;
    StudentProfileModel uni_model;
    ArrayList<StudentProfileModel> dataList = new ArrayList();
    ArrayList<QualificationModel> modelList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_profile_view);
        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("StudentProfile");
        reference1=rootNode.getReference("StudentProfileQu");
        initView();
        readValueFromFireBase();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initView(){
    nameTxt = findViewById(R.id.nameet);
    emailTxt = findViewById(R.id.emaiet);
    phoneTxt = findViewById(R.id.phonEt);
    cgpaText = findViewById(R.id.cgpaEt);
    ieltsTxt = findViewById(R.id.ieltsEt);


    readingTx = findViewById(R.id.readingEt);
    writingTx = findViewById(R.id.writingEt);
    speakingTx = findViewById(R.id.speakingEt);
    listeningTx = findViewById(R.id.listeningEt);

        sscBoard = findViewById(R.id.sscBoard);
        sscGrade = findViewById(R.id.sscGrade);
        sscSubject = findViewById(R.id.sscprogram);
        sscYear = findViewById(R.id.sscMarks);

        hsscBoard = findViewById(R.id.hsscBoard);
        hsscGrade = findViewById(R.id.hsscGrade);
        hsscSubject = findViewById(R.id.hsscprogram);
        hsscYear = findViewById(R.id.hsscMarks);

        baBoard = findViewById(R.id.baBoard);
        baGrade = findViewById(R.id.baGrade);
        baSubject = findViewById(R.id.baprogram);
        baYear = findViewById(R.id.baMarks);

        maBoard = findViewById(R.id.maBoard);
        maGrade = findViewById(R.id.maGrade);
        maSubject = findViewById(R.id.maprogram);
        maYear = findViewById(R.id.maMarks);

    }

    public void setValue(ArrayList<StudentProfileModel> uni_model){

        for (int i=0; i< uni_model.size(); i++){
            if (uni_model.get(i).getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                StudentProfileModel studentProfileModel = uni_model.get(i);
                nameTxt.setText(studentProfileModel.getName());
                emailTxt.setText(studentProfileModel.getEmail());
                phoneTxt.setText(studentProfileModel.getPhone());
                cgpaText.setText(studentProfileModel.getCgpa());
                ieltsTxt.setText(studentProfileModel.getIeltsScore());
                readingTx.setText(studentProfileModel.getReading());
                listeningTx.setText(studentProfileModel.getListneing());
                writingTx.setText(studentProfileModel.getWriting());
                speakingTx.setText(studentProfileModel.getSpeaking());
                reference1.child(studentProfileModel.getEmail().replace(".","")).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        QualificationModel  model=snapshot.getValue(QualificationModel.class);
                        modelList.add(model);
                        setValues2(modelList);

//                officers.setUid(snapshot.getKey());
//                listofItems.add(uni_model);
//                settingListAdapter = new Univeristy_adapter(getActivity(), listofItems);
//                listView.setAdapter(settingListAdapter);
//                settingListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        QualificationModel  model=snapshot.getValue(QualificationModel.class);
                        modelList.add(model);
//                officers.setUid(snapshot.getKey());
//                listofItems.remove(officers);
//                settingListAdapter = new Univeristy_adapter(getActivity(), listofItems);
//                listView.setAdapter(settingListAdapter);
//                settingListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }else {
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        }



    }

    public void readValueFromFireBase(){

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                uni_model=snapshot.getValue(StudentProfileModel.class);
//                officers.setUid(snapshot.getKey());
                dataList.add(uni_model);
                setValue(dataList);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                uni_model=snapshot.getValue(StudentProfileModel.class);
//                officers.setUid(snapshot.getKey());
                dataList.remove(uni_model);
               setValue(dataList);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



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

//        sscBoard.setText(model.get(0).getProgram());
//        sscYear.setText(model.get(0).getPassingyear());
//        sscSubject.setText(model.get(0).getSubject());
//        sscGrade.setText(model.get(0).getGrade());
//
//        hsscBoard.setText(model.get(1).getProgram());
//        hsscYear.setText(model.get(1).getPassingyear());
//        hsscSubject.setText(model.get(1).getSubject());
//        hsscGrade.setText(model.get(1).getGrade());
//
//        baBoard.setText(model.get(2).getProgram());
//        baYear.setText(model.get(2).getPassingyear());
//        baSubject.setText(model.get(2).getSubject());
//        baGrade.setText(model.get(2).getGrade());
//
//        maBoard.setText(model.get(3).getProgram());
//        maYear.setText(model.get(3).getPassingyear());
//        maSubject.setText(model.get(3).getSubject());
//        maGrade.setText(model.get(3).getGrade());
    }

}
