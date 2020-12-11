package com.a1techandroid.studentconsultant;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.a1techandroid.studentconsultant.Adapters.StudentListAdapter;
import com.a1techandroid.studentconsultant.Adapters.Univeristy_adapter;
import com.a1techandroid.studentconsultant.Fragments.FragmentConsultants;
import com.a1techandroid.studentconsultant.Fragments.FragmentHome;
import com.a1techandroid.studentconsultant.Fragments.FragmentStudents;
import com.a1techandroid.studentconsultant.Fragments.FragmentUniveristies;
import com.a1techandroid.studentconsultant.Fragments.SettingFragment;
import com.a1techandroid.studentconsultant.Models.Student_Model;
import com.a1techandroid.studentconsultant.Models.Uni_Model;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class AdminActivity extends AppCompatActivity {
    CardView studetBtn, UniverButton;
    AnimatedBottomBar animatedBottomBar;
    TextView titleMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity);
        studetBtn = findViewById(R.id.studet);
        titleMain = findViewById(R.id.title);
        UniverButton = findViewById(R.id.uni);
        studetBtn.setVisibility(View.VISIBLE);
        UniverButton.setVisibility(View.VISIBLE);
        animatedBottomBar = findViewById(R.id.animatedBottomBar);
        FragmentStudents home= new FragmentStudents();
        replaceFragment(home);
        click();
    }




    public void click(){
        studetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studetBtn.setVisibility(View.GONE);
                UniverButton.setVisibility(View.GONE);
            StudentActivity activity = new StudentActivity();
            replaceFragment(activity);
            }
        });

        UniverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studetBtn.setVisibility(View.GONE);
                UniverButton.setVisibility(View.GONE);
                UniversityActivity activity = new UniversityActivity();
                replaceFragment(activity);
            }
        });

        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NotNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch (newTab.getId()) {
                    case R.id.stud:
                        FragmentStudents home= new FragmentStudents();
                        replaceFragment(home);
                        titleMain.setText("Students");
                        break;
                    case R.id.uni:
                        FragmentConsultants fragmentUniveristies= new FragmentConsultants();
                        replaceFragment(fragmentUniveristies);
                        titleMain.setText("Consultant");
                        break;
                    case R.id.setting:
                        SettingFragment settingFragment = new SettingFragment();
                        replaceFragment(settingFragment);
                        titleMain.setText("Setting");
                        break;
                }
            }
        });
    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame1, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }






}
