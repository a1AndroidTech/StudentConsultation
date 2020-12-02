package com.a1techandroid.studentconsultant.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a1techandroid.studentconsultant.Models.Uni_Model;
import com.a1techandroid.studentconsultant.R;

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
        TextView name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.uni_item, null);
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
