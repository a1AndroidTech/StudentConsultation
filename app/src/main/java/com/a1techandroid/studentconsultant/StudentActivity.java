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

public class StudentActivity  extends Fragment {
    ListView listView;
    Univeristy_adapter settingListAdapter;
    StudentListAdapter studentListAdapter;
    ArrayList<Student_Model> listofItems;
    ArrayList<Student_Model> studentList;
    DatabaseReference reference;
    FirebaseDatabase rootNode;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_activity, container, false);
        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("Univeristies");
        initViews(view);
        return  view;

    }

    @Override
    public void onResume() {
        super.onResume();
        readValueFromFireBase();
    }

    public void initViews(View view){
      listView=view.findViewById(R.id.list_item);
  }

    public void studentList(){
        studentList= new ArrayList<>();
        studentList.add(new Student_Model("Anum","Graduate Program","Berufsakademie Ravensburg","7 bands"));
        studentList.add(new Student_Model("Ali","Doctrate Program","GISMA Business School","5 bands"));
        studentList.add(new Student_Model("Nouman","Undergraduate Program","IUBH University of Applied Sciences","6.5 bands"));
        studentList.add(new Student_Model("Mehmood","Graduate Program","Lancaster University Leipzig","6 bands"));
        studentList.add(new Student_Model("Rabia","Short Courses Program","Arden University Berlin","7 bands"));
        studentList.add(new Student_Model("Qasim","Graduate Program","Freie Universität Berlin","7.5 bands"));


    }

    public void readValueFromFireBase(){
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Student_Model uni_model=snapshot.getValue(Student_Model.class);
//                officers.setUid(snapshot.getKey());
                listofItems.add(uni_model);
                studentListAdapter = new StudentListAdapter(getActivity(), listofItems);
                listView.setAdapter(studentListAdapter);
                studentListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Student_Model officers=snapshot.getValue(Student_Model.class);
//                officers.setUid(snapshot.getKey());
                listofItems.remove(officers);
                studentListAdapter = new StudentListAdapter(getActivity(), listofItems);
                listView.setAdapter(studentListAdapter);
                studentListAdapter.notifyDataSetChanged();
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
