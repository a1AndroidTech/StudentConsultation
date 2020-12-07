package com.a1techandroid.studentconsultant.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.a1techandroid.studentconsultant.Fragments.AttachmentFragment;
import com.a1techandroid.studentconsultant.MainActivity;
import com.a1techandroid.studentconsultant.Models.Scholorship_model;
import com.a1techandroid.studentconsultant.Models.Student_Model;
import com.a1techandroid.studentconsultant.R;
import com.a1techandroid.studentconsultant.ScholorshipDetailActivity;

import java.util.ArrayList;

public class Scholorship_adapter extends BaseAdapter {

    Context context;
    ArrayList<Scholorship_model> list= new ArrayList<Scholorship_model>();

    public Scholorship_adapter(Context context, ArrayList<Scholorship_model> list){
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
        TextView name, time, uni;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.scholorship_item, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.nameS);
            holder.time = convertView.findViewById(R.id.program);
            holder.uni = convertView.findViewById(R.id.timePerios);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Scholorship_model model = list.get(position);

        holder.name.setText(model.getSchName());
        holder.time.setText(model.getEndDate());
        holder.uni.setText(model.getUniName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AttachmentFragment fragment = new AttachmentFragment();
//                FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.contentFrame, fragment);
//                fragmentTransaction.addToBackStack(fragment.toString());
//                fragmentTransacti1on.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                fragmentTransaction.commit();
            v.getContext().startActivity(new Intent(v.getContext(), ScholorshipDetailActivity.class));

            }
        });
        return convertView;
    }

}
