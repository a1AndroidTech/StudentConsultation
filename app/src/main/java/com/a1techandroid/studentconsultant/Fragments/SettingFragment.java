package com.a1techandroid.studentconsultant.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.a1techandroid.studentconsultant.Adapters.SettingListAdapter;
import com.a1techandroid.studentconsultant.LoginActivity;
import com.a1techandroid.studentconsultant.MainActivity;
import com.a1techandroid.studentconsultant.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class SettingFragment extends Fragment {

    ListView listView;
    SettingListAdapter settingListAdapter;
    ArrayList<String> listofItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void initView(View view){
        listView=view.findViewById(R.id.listView);
        listofItems= new ArrayList<>();

        if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("admin@gmail.com")){
            listofItems.add("Add New Admin");
            listofItems.add("About");
            listofItems.add("Share");
            listofItems.add("Logout");
            settingListAdapter = new SettingListAdapter(getActivity(), listofItems);
            listView.setAdapter(settingListAdapter);
            settingListAdapter.notifyDataSetChanged();
        }else {
            listofItems.add("Update Profile");
            listofItems.add("Reset Password");
            listofItems.add("Rate this App");
            listofItems.add("About");
            listofItems.add("Share");
            listofItems.add("Logout");
            settingListAdapter = new SettingListAdapter(getActivity(), listofItems);
            listView.setAdapter(settingListAdapter);
            settingListAdapter.notifyDataSetChanged();
        }

    }
}
