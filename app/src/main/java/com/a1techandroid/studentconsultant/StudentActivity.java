package com.a1techandroid.studentconsultant;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.a1techandroid.studentconsultant.Adapters.StudentListAdapter;
import com.a1techandroid.studentconsultant.Adapters.Univeristy_adapter;
import com.a1techandroid.studentconsultant.Models.Student_Model;
import com.a1techandroid.studentconsultant.Models.Uni_Model;

import java.util.ArrayList;

public class StudentActivity  extends AppCompatActivity {
    ListView listView;
    Univeristy_adapter settingListAdapter;
    StudentListAdapter studentListAdapter;
    ArrayList<Uni_Model> listofItems;
    ArrayList<Student_Model> studentList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity);
        studentList();
    }

    public void studentList(){
        listView=findViewById(R.id.list_item);
        studentList= new ArrayList<>();
        studentList.add(new Student_Model("Anum","Graduate Program","Berufsakademie Ravensburg","7 bands"));
        studentList.add(new Student_Model("Ali","Doctrate Program","GISMA Business School","5 bands"));
        studentList.add(new Student_Model("Nouman","Undergraduate Program","IUBH University of Applied Sciences","6.5 bands"));
        studentList.add(new Student_Model("Mehmood","Graduate Program","Lancaster University Leipzig","6 bands"));
        studentList.add(new Student_Model("Rabia","Short Courses Program","Arden University Berlin","7 bands"));
        studentList.add(new Student_Model("Qasim","Graduate Program","Freie Universität Berlin","7.5 bands"));

        studentListAdapter = new StudentListAdapter(getApplicationContext(), studentList);
        listView.setAdapter(studentListAdapter);
        studentListAdapter.notifyDataSetChanged();
    }
}
