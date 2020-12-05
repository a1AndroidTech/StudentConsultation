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

public class UniversityActivity extends AppCompatActivity {
    ListView listView;
    Univeristy_adapter settingListAdapter;
    StudentListAdapter studentListAdapter;
    ArrayList<Uni_Model> listofItems;
    ArrayList<Student_Model> studentList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.university_activity);
        initView();
    }

    public void initView(){
        listView=findViewById(R.id.list_item);
        listofItems= new ArrayList<>();
        listofItems.add(new Uni_Model("Berlin School of Business and Innovation","Germany", "12-03-2021"));
        listofItems.add(new Uni_Model("IUBH University of Applied Sciences","Germany", "10-01-2021"));
        listofItems.add(new Uni_Model("Lancaster University Leipzign","Germany", "12-12-2020"));
        listofItems.add(new Uni_Model("University of Applied Sciences Europe","England", "03-03-2021"));
        listofItems.add(new Uni_Model("Arden University Berlin","Germany", "12-07-2021"));
        listofItems.add(new Uni_Model("Georg-August-Universität Göttingen","Spain", "01-03-2021"));
        listofItems.add(new Uni_Model("Berlin School of Business and Innovation","Germany", "12-03-2021"));
        listofItems.add(new Uni_Model("IUBH University of Applied Sciences","Germany", "10-01-2021"));

        settingListAdapter = new Univeristy_adapter(getApplicationContext(), listofItems);
        listView.setAdapter(settingListAdapter);
        settingListAdapter.notifyDataSetChanged();
    }

}
