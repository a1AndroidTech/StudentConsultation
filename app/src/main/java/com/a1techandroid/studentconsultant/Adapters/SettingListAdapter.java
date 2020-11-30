package com.a1techandroid.studentconsultant.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.a1techandroid.studentconsultant.LoginActivity;
import com.a1techandroid.studentconsultant.MainActivity;
import com.a1techandroid.studentconsultant.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class SettingListAdapter  extends BaseAdapter {
    Context context;
    ArrayList<String> list= new ArrayList<>();
    @Override
    public int getCount() {
        return list.size();
    }

    public SettingListAdapter(Context context, ArrayList<String> list1){
        this.context = context;
        this.list = list1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        TextView name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.setting_cell, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String name = list.get(position);

        holder.name.setText(name);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0){

                }else if (position == 1){

                }else if (position == 2){

                }else if (position == 3){

                }else if (position == 4){

                }else if (position == 5){
                    FirebaseAuth.getInstance().signOut();
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            }
        });
        return convertView;
    }
}
