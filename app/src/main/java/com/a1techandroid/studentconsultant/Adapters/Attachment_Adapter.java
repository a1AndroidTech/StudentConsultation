package com.a1techandroid.studentconsultant.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.a1techandroid.studentconsultant.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Attachment_Adapter extends BaseAdapter {

    Context context;
    ArrayList<String> list;

    public Attachment_Adapter(Context context, ArrayList<String> list) {
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

    public class ViewHolder{
        ImageView imageView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.attachment_adapter, null);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.hssc);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String name = list.get(position);

        Glide.with(context)
                .load(name)
                .centerCrop()
                .into(holder.imageView);

        return convertView;
    }
}
