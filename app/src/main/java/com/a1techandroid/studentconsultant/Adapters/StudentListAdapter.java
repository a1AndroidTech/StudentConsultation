package com.a1techandroid.studentconsultant.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a1techandroid.studentconsultant.Models.Student_Model;
import com.a1techandroid.studentconsultant.R;

import java.util.ArrayList;

public class StudentListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Student_Model> studentList= new ArrayList();

    public StudentListAdapter(Context context, ArrayList<Student_Model> list){
        this.context = context;
        this.studentList = list;
    }
    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder{
        TextView name, applyFor, uni, ielts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.student_item, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.nameS);
            holder.applyFor = convertView.findViewById(R.id.program);
            holder.uni = convertView.findViewById(R.id.timePerios);
            holder.ielts = convertView.findViewById(R.id.ielts);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Student_Model model = studentList.get(position);

        holder.name.setText(model.getName());
        holder.applyFor.setText(model.getApplyFor());
        holder.uni.setText(model.getUniName());
        holder.ielts.setText(model.getIeltsScore());

        return convertView;
    }
}
