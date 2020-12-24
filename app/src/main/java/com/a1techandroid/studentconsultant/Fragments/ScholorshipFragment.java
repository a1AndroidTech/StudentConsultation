package com.a1techandroid.studentconsultant.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.a1techandroid.studentconsultant.Adapters.Scholorship_adapter;
import com.a1techandroid.studentconsultant.Adapters.Univeristy_adapter;
import com.a1techandroid.studentconsultant.MainActivity;
import com.a1techandroid.studentconsultant.Models.Scholorship_model;
import com.a1techandroid.studentconsultant.Models.Uni_Model;
import com.a1techandroid.studentconsultant.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ScholorshipFragment extends Fragment {
    ListView listView;
    Scholorship_adapter settingListAdapter;
    ArrayList<Scholorship_model> listofItems;
    DatabaseReference reference;
    FirebaseDatabase rootNode;
    Scholorship_model scholorship_model;
    Uni_Model uni_model;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_univeristy, container, false);
        rootNode=FirebaseDatabase.getInstance();
        Intent i = getActivity().getIntent();
        Bundle bundle = getArguments();
        uni_model= (Uni_Model) bundle.getSerializable("UniKey");
        reference=rootNode.getReference("Scholorship");
        initView(view);
//        addUni();
        readValueFromFireBase();
        addUni();
        return view;
    }

    public void initView(View view){
        listView=view.findViewById(R.id.listView);
        listofItems= new ArrayList<>();

    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.title.setText("Scholorships");
        readValueFromFireBase();
    }

    public void addUni(){
//        scholorship_model = new Scholorship_model("Eiffel Excellence Scholarship Programme", "22-10-2021","Berlin School of Business and Innovation");
        listofItems.add(new Scholorship_model("Eiffel Excellence Scholarship Programme","22-10-2021", "Georg-August-Universität Göttingen"));
        listofItems.add(new Scholorship_model("Fulbright Scholarship","21-03-2021", "Georg-August-Universität Göttingen"));
        listofItems.add(new Scholorship_model("Chevening Scholarship","22-10-2021", "Georg-August-Universität Göttingen"));
        listofItems.add(new Scholorship_model("Chevening Scholarship","12-07-2021", "Arden University Berlin"));
        listofItems.add(new Scholorship_model("Commonwealth Scholarship","12-07-2021", "Arden University Berlin"));
        listofItems.add(new Scholorship_model("Eiffel Excellence Scholarship Programme","11-10-2021", "University of Applied Sciences Europe"));
        listofItems.add(new Scholorship_model("Eiffel Excellence Scholarship Programme","05-06-2021", "University of Applied Sciences Europe"));
        listofItems.add(new Scholorship_model("Chevening Scholarship","22-10-2021", "University of Applied Sciences Europe"));
        for (int i=0; i<listofItems.size(); i++){
            scholorship_model = listofItems.get(i);
            reference.child(scholorship_model.getUniName()).child(reference.push().getKey().toString())
                    .setValue(scholorship_model).addOnCompleteListener(new OnCompleteListener<Void>() {
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
    public void readValueFromFireBase(){
        reference.child(uni_model.getUniName()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Scholorship_model uni_model=snapshot.getValue(Scholorship_model.class);
//                officers.setUid(snapshot.getKey());
                listofItems.add(uni_model);
                settingListAdapter = new Scholorship_adapter(getActivity(), listofItems);
                listView.setAdapter(settingListAdapter);
                settingListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Scholorship_model officers=snapshot.getValue(Scholorship_model.class);
//                officers.setUid(snapshot.getKey());
                listofItems.remove(officers);
                settingListAdapter = new Scholorship_adapter(getActivity(), listofItems);
                listView.setAdapter(settingListAdapter);
                settingListAdapter.notifyDataSetChanged();
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
