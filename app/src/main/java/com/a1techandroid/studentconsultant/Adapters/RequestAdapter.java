package com.a1techandroid.studentconsultant.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.a1techandroid.studentconsultant.MainActivity;
import com.a1techandroid.studentconsultant.Models.RequestModel;
import com.a1techandroid.studentconsultant.Models.Scholorship_model;
import com.a1techandroid.studentconsultant.R;
import com.a1techandroid.studentconsultant.ScholorshipDetailActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class RequestAdapter  extends BaseAdapter {
    Context context;
    ArrayList<RequestModel> list= new ArrayList<RequestModel>();

    public RequestAdapter(Context context, ArrayList<RequestModel> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
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
        TextView name, sName, uniName, date;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.req_item, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.nameS);
            holder.sName = convertView.findViewById(R.id.nameSS);
            holder.uniName = convertView.findViewById(R.id.unName);
            holder.date = convertView.findViewById(R.id.date);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final RequestModel model = list.get(position);

        holder.name.setText("test");
        holder.sName.setText(model.getScName());
        holder.uniName.setText(model.getUniName());
        holder.date.setText(model.getSDate());


//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                ScholorshipDetailActivity fragment = new ScholorshipDetailActivity();
////                Bundle bundle = new Bundle();
////                bundle.putSerializable("UniKey", (Serializable) model);
////                fragment.setArguments(bundle);
////                FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
////                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////                fragmentTransaction.replace(R.id.contentFrame, fragment);
////                fragmentTransaction.addToBackStack(fragment.toString());
////                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
////                fragmentTransaction.commit();
//
//
//            }
//        });
        return convertView;
    }

}
