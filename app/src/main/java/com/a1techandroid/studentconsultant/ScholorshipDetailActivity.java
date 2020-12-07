package com.a1techandroid.studentconsultant;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.a1techandroid.studentconsultant.Fragments.AttachmentFragment;

public class ScholorshipDetailActivity extends AppCompatActivity {
    TextView SName, UName, SType, SDate;
    CardView ApplyButton;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scholrshipview, container, false);
        initViews(view);
        setUpClick();
        return view;
    }

    public void initViews( View view){
        SName = view.findViewById(R.id.schName);
        UName = view.findViewById(R.id.uniName);
        SType = view.findViewById(R.id.schType);
        SDate = view.findViewById(R.id.lastDate);
        ApplyButton = view.findViewById(R.id.apply);
    }

    public void setUpClick(){
        ApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttachmentFragment fragment=new AttachmentFragment();
                replaceFragment(fragment);
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

}
