package com.a1techandroid.studentconsultant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.a1techandroid.studentconsultant.Adapters.StudentListAdapter;
import com.a1techandroid.studentconsultant.Adapters.Univeristy_adapter;
import com.a1techandroid.studentconsultant.Models.Student_Model;
import com.a1techandroid.studentconsultant.Models.Uni_Model;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UniversityActivity extends Fragment {
    ListView listView;
    Univeristy_adapter settingListAdapter;
    ArrayList<Uni_Model> listofItems;
    DatabaseReference reference;
    FirebaseDatabase rootNode;
    Uni_Model uni_model;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.university_activity, container, false);
        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("Univeristies");
        initView(view);
        readValueFromFireBase();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        readValueFromFireBase();
    }

    public void initView(View view){
        listView=view.findViewById(R.id.list_item);
        listofItems= new ArrayList<>();
//        listofItems.add(new Uni_Model("Berlin School of Business and Innovation","Germany", "12-03-2021"));
//        listofItems.add(new Uni_Model("IUBH University of Applied Sciences","Germany", "10-01-2021"));
//        listofItems.add(new Uni_Model("Lancaster University Leipzign","Germany", "12-12-2020"));
//        listofItems.add(new Uni_Model("University of Applied Sciences Europe","England", "03-03-2021"));
//        listofItems.add(new Uni_Model("Arden University Berlin","Germany", "12-07-2021"));
//        listofItems.add(new Uni_Model("Georg-August-Universität Göttingen","Spain", "01-03-2021"));
//        listofItems.add(new Uni_Model("Berlin School of Business and Innovation","Germany", "12-03-2021"));
//        listofItems.add(new Uni_Model("IUBH University of Applied Sciences","Germany", "10-01-2021"));

//        settingListAdapter = new Univeristy_adapter(getActivity(), listofItems);
//        listView.setAdapter(settingListAdapter);
//        settingListAdapter.notifyDataSetChanged();
    }

    public void readValueFromFireBase(){
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Uni_Model uni_model=snapshot.getValue(Uni_Model.class);
//                officers.setUid(snapshot.getKey());
                listofItems.add(uni_model);
                settingListAdapter = new Univeristy_adapter(getActivity(), listofItems);
                listView.setAdapter(settingListAdapter);
                settingListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Uni_Model officers=snapshot.getValue(Uni_Model.class);
//                officers.setUid(snapshot.getKey());
                listofItems.remove(officers);
                settingListAdapter = new Univeristy_adapter(getActivity(), listofItems);
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
