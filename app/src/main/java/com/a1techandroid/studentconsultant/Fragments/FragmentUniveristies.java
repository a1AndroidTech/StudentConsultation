package com.a1techandroid.studentconsultant.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.a1techandroid.studentconsultant.Adapters.SettingListAdapter;
import com.a1techandroid.studentconsultant.Adapters.Univeristy_adapter;
import com.a1techandroid.studentconsultant.MainActivity;
import com.a1techandroid.studentconsultant.Models.StudentProfileModel;
import com.a1techandroid.studentconsultant.Models.Uni_Model;
import com.a1techandroid.studentconsultant.Models.UserModel;
import com.a1techandroid.studentconsultant.R;
import com.a1techandroid.studentconsultant.SharedPrefrences;
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

import java.util.ArrayList;

public class FragmentUniveristies extends Fragment {
    ListView listView;
    Univeristy_adapter settingListAdapter;
    ArrayList<Uni_Model> listofItems;
    DatabaseReference reference;
    DatabaseReference reference1;
    FirebaseDatabase rootNode;
    Uni_Model uni_model;
    StudentProfileModel st_Profile;
    private ProgressDialog mProgressDialog;
    UserModel userModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_univeristy, container, false);
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Univeristies");
        reference1 = rootNode.getReference("StudentProfile");
        mProgressDialog = new ProgressDialog(getActivity());
        userModel = SharedPrefrences.getUser(getActivity());


        initView(view);
//        addUni();
//        readValueFromFireBase();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.title.setText("Univeristies");
        readValueFromFireBase();
    }

    public void initView(View view) {
        listView = view.findViewById(R.id.listView);
        listofItems = new ArrayList<>();
        listofItems.add(new Uni_Model("Berlin School of Business and Innovation", "Germany", "12-03-2021", "3.5"));
        listofItems.add(new Uni_Model("IUBH University of Applied Sciences", "Germany", "10-01-2021", "3.5"));
        listofItems.add(new Uni_Model("Lancaster University Leipzign", "Germany", "12-12-2020", "3.0"));
        listofItems.add(new Uni_Model("University of Applied Sciences Europe", "England", "03-03-2021", "3.0"));
        listofItems.add(new Uni_Model("Arden University Berlin", "Germany", "12-07-2021", "2.5"));
        listofItems.add(new Uni_Model("Georg-August-Universität Göttingen", "Spain", "01-03-2021", "2.5"));
        listofItems.add(new Uni_Model("Berlin School of Business and Innovation", "Germany", "12-03-2021", "2.0"));
        listofItems.add(new Uni_Model("IUBH University of Applied Sciences", "Germany", "10-01-2021", "2.0"));
        listofItems.add(new Uni_Model("Lancaster University Leipzign", "Germany", "12-12-2020", "2.0 "));
//
//        settingListAdapter = new Univeristy_adapter(getActivity(), listofItems);
//        listView.setAdapter(settingListAdapter);
//        settingListAdapter.notifyDataSetChanged();
    }

    public void addUni() {
        for (int i = 0; i < listofItems.size(); i++) {
            uni_model = listofItems.get(i);
            String key = reference.push().getKey();
            reference.child(key)
                    .setValue(uni_model).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "submitted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }


    public void readValueFromFireBase() {
        mProgressDialog.setMessage("Getting Univeristies");
        mProgressDialog.show();

        reference1.child(userModel.getEmail().replace(".","")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                st_Profile = snapshot.getValue(StudentProfileModel.class);


                if (st_Profile.getCgpa().isEmpty()) {
                    st_Profile.setCgpa("2.0");
                }

                Double gpa = Double.parseDouble("" + st_Profile.getCgpa().trim());
                Query applesQuery = null;
                if (gpa >= 3.5 && gpa <= 4) {
                    applesQuery = reference.orderByChild("criteria").equalTo("3.5");
                } else if (gpa >= 3.0 && gpa <= 3.5) {
                    applesQuery = reference.orderByChild("criteria").equalTo("3.0");
                } else if (gpa >= 2.5 && gpa <= 3.0) {
                    applesQuery = reference.orderByChild("criteria").equalTo("2.5");
                } else if (gpa >= 2.0  && gpa <= 2.5) {
                    applesQuery = reference.orderByChild("criteria").equalTo("2.0");
                }
                applesQuery.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Uni_Model uni_model = snapshot.getValue(Uni_Model.class);
//                officers.setUid(snapshot.getKey());

                        listofItems.add(uni_model);
                        settingListAdapter = new Univeristy_adapter(getActivity(), listofItems);
                        listView.setAdapter(settingListAdapter);
                        settingListAdapter.notifyDataSetChanged();
                        mProgressDialog.hide();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        Uni_Model officers = snapshot.getValue(Uni_Model.class);
//                officers.setUid(snapshot.getKey());
                        listofItems.remove(officers);
                        settingListAdapter = new Univeristy_adapter(getActivity(), listofItems);
                        listView.setAdapter(settingListAdapter);
                        settingListAdapter.notifyDataSetChanged();
                        mProgressDialog.hide();
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        reference1.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                st_Profile=snapshot.getValue(StudentProfileModel.class);
////                officers.setUid(snapshot.getKey());
////                dataList.add(uni_model);
////                setValue(uni_model);
//
//
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                st_Profile=snapshot.getValue(StudentProfileModel.class);
////                officers.setUid(snapshot.getKey());
////                setValue(uni_model);
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
//
//
//    }
//
    }
}
