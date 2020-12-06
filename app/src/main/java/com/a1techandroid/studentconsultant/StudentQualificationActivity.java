package com.a1techandroid.studentconsultant;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.a1techandroid.studentconsultant.Models.QualificationModel;
import com.a1techandroid.studentconsultant.Models.StudentProfileModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

public class StudentQualificationActivity extends AppCompatActivity {
    StudentProfileModel studentProfileModel;
    EditText sscBoard, hsscBoard, baBoard, maBoard;
    EditText sscSubj, hsscSubj, baSubj, maSubj;
    EditText sscGrade, hsscGrade, baGrade, maGrade ;
    EditText sscPassingYear, hsscPassingYear, baPassingYear, maPassingYear;
    CardView Submit;

    DatabaseReference reference, reference2;
    FirebaseDatabase rootNode;
    StudentProfileModel model;
    QualificationModel model1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_qualification_activity);

        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("StudentProfile");
        reference2=rootNode.getReference("StudentProfileQu");
        initViews();
        Gson gson = new Gson();
        String profileModel = getIntent().getStringExtra("model");
        model = gson.fromJson(profileModel, StudentProfileModel.class);
        initViews();
        setupClickS();
    }

    public void initViews(){
        sscBoard = findViewById(R.id.sscBoard);
        hsscBoard = findViewById(R.id.hsscBoard);
        baBoard = findViewById(R.id.baBoard);
        maBoard = findViewById(R.id.maBoard);

        sscSubj = findViewById(R.id.sscSubj);
        hsscSubj = findViewById(R.id.hsscSubj);
        baSubj = findViewById(R.id.baSubj);
        maSubj = findViewById(R.id.maSubj);

        sscGrade = findViewById(R.id.sscGrade);
        hsscGrade = findViewById(R.id.hsscGrade);
        baGrade = findViewById(R.id.baGrade);
        maGrade = findViewById(R.id.maGrade);

        sscPassingYear = findViewById(R.id.sscPassingYear);
        hsscPassingYear = findViewById(R.id.hsscPassingYear);
        baPassingYear = findViewById(R.id.baPassingYear);
        maPassingYear = findViewById(R.id.maPassingYear);

        Submit = findViewById(R.id.nextButton);
    }

    public void setupClickS(){
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "submitted successfully", Toast.LENGTH_SHORT).show();
                            reference2.child(model.getEmail().replace(".", ""))
                                    .setValue(getqualificationModel()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "submitted successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    public ArrayList<QualificationModel> getqualificationModel(){
        String sscB = sscBoard.getText().toString();
        String sscS = sscSubj.getText().toString();
        String sscG = sscGrade.getText().toString();
        String sscP = sscPassingYear.getText().toString();
        ArrayList<QualificationModel> list = new ArrayList<>();
        model1 = new QualificationModel("SSC", sscB, sscS, sscG, sscP);
        list.add(model1);
        model1 = new QualificationModel("HSSC", hsscBoard.getText().toString(), hsscSubj.getText().toString(), hsscGrade.getText().toString(), hsscGrade.getText().toString());
        list.add(model1);
        model1 = new QualificationModel("BA", baBoard.getText().toString(), baSubj.getText().toString(), baGrade.getText().toString(), baGrade.getText().toString());
        list.add(model1);
        model1 = new QualificationModel("MA", maBoard.getText().toString(), maSubj.getText().toString(), maGrade.getText().toString(), maGrade.getText().toString());
        list.add(model1);
        return list;
    }
}
