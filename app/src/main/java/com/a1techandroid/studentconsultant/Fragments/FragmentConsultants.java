package com.a1techandroid.studentconsultant.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.a1techandroid.studentconsultant.Adapters.AdminSideAdapter;
import com.a1techandroid.studentconsultant.Adapters.ConsultantAdapter;
import com.a1techandroid.studentconsultant.Adapters.StudentListAdapter;
import com.a1techandroid.studentconsultant.Adapters.Univeristy_adapter;
import com.a1techandroid.studentconsultant.ConsultantProfile;
import com.a1techandroid.studentconsultant.Models.ConsultantProfileModel;
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

import java.util.ArrayList;

public class FragmentConsultants extends Fragment {
    ListView listView;
    ConsultantAdapter settingListAdapter;
    ArrayList<UserModel> listofItems;
    DatabaseReference reference;
    FirebaseDatabase rootNode;
    Uni_Model uni_model;
    ConsultantAdapter studentListAdapter;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_univeristy, container, false);
        rootNode=FirebaseDatabase.getInstance();
        reference= rootNode.getReference("Consultant");
        mProgressDialog = new ProgressDialog(getActivity());
        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        readValueFromFireBase();
    }

    public void initViews(View view){
        listView = view.findViewById(R.id.listView);

    }
    public void readValueFromFireBase(){
        mProgressDialog.setTitle("Getting Consultants");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.show();
        listofItems  =new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    UserModel uni_model=data.getValue(UserModel.class);
////                officers.setUid(snapshot.getKey());
                    listofItems.add(uni_model);
                    studentListAdapter = new ConsultantAdapter(getActivity(), listofItems);
                    listView.setAdapter(studentListAdapter);
                    studentListAdapter.notifyDataSetChanged();
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}

