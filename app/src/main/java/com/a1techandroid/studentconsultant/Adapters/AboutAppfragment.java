package com.a1techandroid.studentconsultant.Adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.a1techandroid.studentconsultant.R;


public class AboutAppfragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container, false);
//        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
