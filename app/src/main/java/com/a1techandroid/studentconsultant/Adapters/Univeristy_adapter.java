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

import com.a1techandroid.studentconsultant.Fragments.ScholorshipFragment;
import com.a1techandroid.studentconsultant.MainActivity;
import com.a1techandroid.studentconsultant.Models.Uni_Model;
import com.a1techandroid.studentconsultant.R;

import java.io.Serializable;
import java.util.ArrayList;

public class Univeristy_adapter extends BaseAdapter {
    Context context;
    ArrayList<Uni_Model> list = new ArrayList<>();


    public Univeristy_adapter(Context context, ArrayList<Uni_Model> list){
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
        TextView name, country, lastDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.uni_item, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.nameS);
            holder.country = convertView.findViewById(R.id.country);
            holder.lastDate = convertView.findViewById(R.id.timePerios);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Uni_Model model = list.get(position);
        holder.name.setText(model.getUniName());
        holder.country.setText(model.getCountry());
        holder.lastDate.setText(model.getDate());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScholorshipFragment fragment = new ScholorshipFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("UniKey", (Serializable) model);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentFrame, fragment);
                fragmentTransaction.addToBackStack(fragment.toString());
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();           }
        });

        return convertView;
    }
}
