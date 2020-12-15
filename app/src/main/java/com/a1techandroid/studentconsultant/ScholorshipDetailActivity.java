package com.a1techandroid.studentconsultant;

import android.content.Context;
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
import com.a1techandroid.studentconsultant.Models.RequestModel;
import com.a1techandroid.studentconsultant.Models.Scholorship_model;
import com.a1techandroid.studentconsultant.Models.Uni_Model;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

public class ScholorshipDetailActivity extends Fragment {
    TextView SName, UName, SType, SDate;
    CardView ApplyButton;
    Scholorship_model model;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scholrshipview, container, false);
        Bundle bundle = getArguments();
        model= (Scholorship_model) bundle.getSerializable("UniKey");
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


        SName.setText(model.getSchName());
        UName.setText(model.getUniName());
        SDate.setText(model.getEndDate());
        SType.setText("This is fully funded scholorship");
    }

    public void setUpClick(){
        ApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestModel requestModel = new RequestModel(SName.getText().toString(), UName.getText().toString(), SType.getText().toString(), SDate.getText().toString(),"", "", "", "", "", "", "", FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".",""), FirebaseAuth.getInstance().getCurrentUser().getUid());
                AttachmentFragment fragment=new AttachmentFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("key", (Serializable) requestModel);
                fragment.setArguments(bundle);
                replaceFragment(fragment, getContext());
            }
        });
    }



    public void replaceFragment(Fragment fragment, Context context) {
        FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

}
