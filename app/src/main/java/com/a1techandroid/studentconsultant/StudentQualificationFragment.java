package com.a1techandroid.studentconsultant;

import android.app.ProgressDialog;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.a1techandroid.studentconsultant.Fragments.FragmentUniveristies;
import com.a1techandroid.studentconsultant.Models.QualificationModel;
import com.a1techandroid.studentconsultant.Models.StudentProfileModel;
import com.a1techandroid.studentconsultant.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentQualificationFragment extends Fragment {
    StudentProfileModel studentProfileModel;
    EditText sscBoard, hsscBoard, baBoard, maBoard;
    EditText sscSubj, hsscSubj, baSubj, maSubj;
    EditText sscGrade, hsscGrade, baGrade, maGrade ;
    EditText sscPassingYear, hsscPassingYear, baPassingYear, maPassingYear;
    CardView Submit;

    DatabaseReference reference, reference2;
    FirebaseDatabase rootNode;
    UserModel model;
    QualificationModel model1;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.s_qualification_activity, container, false);
        rootNode = FirebaseDatabase.getInstance();
        reference2=rootNode.getReference("StudentProfileQu");
        mProgressDialog = new ProgressDialog(getActivity());
        model = SharedPrefrences.getUser(getActivity());
        initViews(view);
        setupClickS();
        return view;
    }



    public void initViews(View view){
        sscBoard = view.findViewById(R.id.sscBoard);
        hsscBoard = view.findViewById(R.id.hsscBoard);
        baBoard = view.findViewById(R.id.baBoard);
        maBoard = view.findViewById(R.id.maBoard);

        sscSubj = view.findViewById(R.id.sscSubj);
        hsscSubj = view.findViewById(R.id.hsscSubj);
        baSubj = view.findViewById(R.id.baSubj);
        maSubj = view.findViewById(R.id.maSubj);

        sscGrade = view.findViewById(R.id.sscGrade);
        hsscGrade = view.findViewById(R.id.hsscGrade);
        baGrade = view.findViewById(R.id.baGrade);
        maGrade = view.findViewById(R.id.maGrade);

        sscPassingYear = view.findViewById(R.id.sscPassingYear);
        hsscPassingYear = view.findViewById(R.id.hsscPassingYear);
        baPassingYear = view.findViewById(R.id.baPassingYear);
        maPassingYear = view.findViewById(R.id.maPassingYear);

        sscPassingYear.setClickable(true);
        sscPassingYear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){
                    setDateFormat();
                }
                return false;
            }
        });

        Submit = view.findViewById(R.id.nextButton);
    }

    public void setupClickS(){
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getqualificationModel().size() < 4){
                    Toast.makeText(getActivity(), "Please Fill First All Acedamic Details", Toast.LENGTH_SHORT).show();
                }else {
                    mProgressDialog.setTitle("Updating Profile");
                    mProgressDialog.setMessage("please wait...");
                    mProgressDialog.show();
                reference2.child(model.getEmail().replace(".", ""))
                        .setValue(getqualificationModel()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            FragmentUniveristies fragmentUniveristies = new FragmentUniveristies();
                            replaceFragment(fragmentUniveristies);
                            Toast.makeText(getActivity(), "submitted successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            mProgressDialog.hide();
                            Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }}
        });
    }

    public ArrayList<QualificationModel> getqualificationModel(){
        String sscB = sscBoard.getText().toString();
        String sscS = sscSubj.getText().toString();
        String sscG = sscGrade.getText().toString();
        String sscP = sscPassingYear.getText().toString();

        String hsscB = hsscBoard.getText().toString();
        String hsscS = hsscSubj.getText().toString();
        String hsscG = hsscGrade.getText().toString();
        String hsscP = hsscPassingYear.getText().toString();

        String baB = baBoard.getText().toString();
        String baS = baSubj.getText().toString();
        String baG = baGrade.getText().toString();
        String baP = baPassingYear.getText().toString();

        String maB = maBoard.getText().toString();
        String maS = maSubj.getText().toString();
        String maG = maGrade.getText().toString();
        String maP = maPassingYear.getText().toString();


        ArrayList<QualificationModel> list = new ArrayList<>();

        if (sscB.equals("") && sscS.equals("") && sscG.equals("") && sscP.equals("")){
//            Toast.makeText(getActivity(), "Please Enter SSC Details", Toast.LENGTH_SHORT).show();
        }else {
            model1 = new QualificationModel("SSC", sscB, sscS, sscG, sscP);
            list.add(model1);
        }
        if (hsscB.equals("") && hsscS.equals("") && hsscG.equals("") && hsscP.equals("")){
//            Toast.makeText(getActivity(), "Please Enter HSSC Details", Toast.LENGTH_SHORT).show();
        }else {
            model1 = new QualificationModel("HSSC", hsscB, hsscS, hsscG, hsscP);
            list.add(model1);
        }

        if (baB.equals("") && baS.equals("") && baG.equals("") && baP.equals("")){
//            Toast.makeText(getActivity(), "Please Enter BA Details", Toast.LENGTH_SHORT).show();
        }else {
            model1 = new QualificationModel("BA", baB, baS, baG, baP);
            list.add(model1);
        }
        if (maB.equals("") && maS.equals("") && maG.equals("") && maP.equals("")){
//            Toast.makeText(getActivity(), "Please Enter MA Details", Toast.LENGTH_SHORT).show();
        }else {
            model1 = new QualificationModel("MA", maB, maS, maG, maP);
            list.add(model1);
        }
        return list;
    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    public void setDateFormat(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        builderSingle.setTitle("Select One Year");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("2001");
        arrayAdapter.add("2002");
        arrayAdapter.add("2003");
        arrayAdapter.add("2004");
        arrayAdapter.add("2005");
        arrayAdapter.add("2006");
        arrayAdapter.add("2007");
        arrayAdapter.add("2008");
        arrayAdapter.add("2009");
        arrayAdapter.add("2010");
        arrayAdapter.add("2011");
        arrayAdapter.add("2012");
        arrayAdapter.add("2013");
        arrayAdapter.add("2014");
        arrayAdapter.add("2015");
        arrayAdapter.add("2016");
        arrayAdapter.add("2017");
        arrayAdapter.add("2018");
        arrayAdapter.add("2019");

//        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                sscPassingYear.setText(arrayAdapter.getItem(which));
//                Toast.makeText(getActivity(), "1"+arrayAdapter.getItem(which), Toast.LENGTH_SHORT).show();
//            }
//        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(getActivity());
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected Item is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "2"+arrayAdapter.getItem(which), Toast.LENGTH_SHORT).show();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();

    }


}