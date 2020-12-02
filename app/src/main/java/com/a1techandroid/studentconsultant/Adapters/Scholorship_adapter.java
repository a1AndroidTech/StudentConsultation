package com.a1techandroid.studentconsultant.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a1techandroid.studentconsultant.Models.Scholorship_model;
import com.a1techandroid.studentconsultant.Models.Student_Model;
import com.a1techandroid.studentconsultant.R;

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
        TextView name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.scholorship_item, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        final String name = list.get(position);

        return convertView;
    }

}
