package com.a1techandroid.studentconsultant.Models;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.a1techandroid.studentconsultant.R;

public class FragmentUniveristies extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_univeristy, container, false);
//        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
