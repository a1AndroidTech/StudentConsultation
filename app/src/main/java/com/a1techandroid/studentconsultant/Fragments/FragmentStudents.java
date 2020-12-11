package com.a1techandroid.studentconsultant.Fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.a1techandroid.studentconsultant.Adapters.AdminSideAdapter;
import com.a1techandroid.studentconsultant.Adapters.SliderAdapter;
import com.a1techandroid.studentconsultant.Adapters.StudentListAdapter;
import com.a1techandroid.studentconsultant.Adapters.Univeristy_adapter;
import com.a1techandroid.studentconsultant.Models.Student_Model;
import com.a1techandroid.studentconsultant.Models.Uni_Model;
import com.a1techandroid.studentconsultant.Models.UserModel;
import com.a1techandroid.studentconsultant.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class FragmentStudents extends Fragment {

    DatabaseReference reference;
    FirebaseDatabase rootNode;

    private ProgressDialog mProgressDialog;
    ListView listView;
    Univeristy_adapter settingListAdapter;
    AdminSideAdapter studentListAdapter;
    ArrayList<UserModel> listofItems;
    ArrayList<Student_Model> studentList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_univeristy, container, false);
        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("Student");
        mProgressDialog = new ProgressDialog(getActivity());
        initViews(view);
//        readValueFromFireBase();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        readValueFromFireBase();
    }

    public void initViews(View view){

    listView=view.findViewById(R.id.listView);
    }

    public void readValueFromFireBase(){
        mProgressDialog.setTitle("Getting Students");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.show();
        listofItems = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    UserModel uni_model=data.getValue(UserModel.class);
////                officers.setUid(snapshot.getKey());
                listofItems.add(uni_model);
                studentListAdapter = new AdminSideAdapter(getActivity(), listofItems);
                listView.setAdapter(studentListAdapter);
                studentListAdapter.notifyDataSetChanged();
                mProgressDialog.hide();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        reference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                Student_Model uni_model=snapshot.getValue(Student_Model.class);
////                officers.setUid(snapshot.getKey());
//                listofItems.add(uni_model);
//                studentListAdapter = new StudentListAdapter(getActivity(), listofItems);
//                listView.setAdapter(studentListAdapter);
//                studentListAdapter.notifyDataSetChanged();
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
//                Student_Model uni_model=snapshot.getValue(Student_Model.class);
////                officers.setUid(snapshot.getKey());
//                listofItems.add(uni_model);
//                studentListAdapter = new StudentListAdapter(getActivity(), listofItems);
//                listView.setAdapter(studentListAdapter);
//                studentListAdapter.notifyDataSetChanged();
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

    }


}

