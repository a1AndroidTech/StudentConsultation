package com.a1techandroid.studentconsultant.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.a1techandroid.studentconsultant.MainActivity;
import com.a1techandroid.studentconsultant.Models.RequestModel;
import com.a1techandroid.studentconsultant.Models.Scholorship_model;
import com.a1techandroid.studentconsultant.Models.UserModel;
import com.a1techandroid.studentconsultant.R;
import com.a1techandroid.studentconsultant.ScholorshipDetailActivity;
import com.a1techandroid.studentconsultant.SharedPrefrences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestAdapter  extends BaseAdapter {
    Context context;
    ArrayList<RequestModel> list= new ArrayList<RequestModel>();
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefe;

    public RequestAdapter(Context context, ArrayList<RequestModel> list){
        mDatabase = FirebaseDatabase.getInstance();
        mRefe = mDatabase.getReference("requests");
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
        Button accept;
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
            holder.accept = convertView.findViewById(R.id.accept);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final RequestModel model = list.get(position);

        holder.name.setText(model.getUserName());
        holder.sName.setText(model.getScName());
        holder.uniName.setText(model.getUniName());
        holder.date.setText(model.getSDate());
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = null;
//                if (model.getUser_type() == 1){
//                    applesQuery = ref.child("Student").orderByChild("email").equalTo(model.getEmail());
//
//                }else if (model.getUser_type() == 2) {
                applesQuery = ref.child("requests").child(model.getUserID());
//                }

                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            Map<String, Object> postValues = new HashMap<String,Object>();
                            String key = appleSnapshot.getRef().getKey();
                            postValues.put("status", "Approved");
                            RequestModel model = appleSnapshot.getValue(RequestModel.class);
                            model.setStatus("Approved");
                            ref.child(key).updateChildren(postValues);
                            SharedPrefrences.saveApprovedREquest(model, context);
                            holder.accept.setVisibility(View.GONE);
//                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ScholorshipDetailActivity fragment = new ScholorshipDetailActivity();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("UniKey", (Serializable) model);
//                fragment.setArguments(bundle);
//                FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.contentFrame, fragment);
//                fragmentTransaction.addToBackStack(fragment.toString());
//                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                fragmentTransaction.commit();


            }
        });
        return convertView;
    }

}
