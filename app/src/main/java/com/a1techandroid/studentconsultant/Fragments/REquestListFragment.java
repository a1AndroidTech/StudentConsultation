package com.a1techandroid.studentconsultant.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.a1techandroid.studentconsultant.Adapters.RequestAdapter;
import com.a1techandroid.studentconsultant.Models.RequestModel;
import com.a1techandroid.studentconsultant.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class REquestListFragment extends Fragment {
    ArrayList<RequestModel> list ;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefe;
    RequestAdapter requestAdapter;
ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.request_list, container, false);
        mDatabase = FirebaseDatabase.getInstance();
        mRefe = mDatabase.getReference("requests");
        initViews(view);
        return view;
    }

    public void initViews(View view){
     listView= view.findViewById(R.id.listView);
    }

    public void getListFormBAckend(){
        list = new ArrayList<>();
        mRefe.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                RequestModel models=snapshot.getValue(RequestModel.class);
//                officers.setUid(snapshot.getKey());
                list.add(models);
                requestAdapter = new RequestAdapter(getActivity(), list);
                listView.setAdapter(requestAdapter);
                requestAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                RequestModel models=snapshot.getValue(RequestModel.class);
//                officers.setUid(snapshot.getKey());
                list.remove(models);
                requestAdapter.notifyDataSetChanged();
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
