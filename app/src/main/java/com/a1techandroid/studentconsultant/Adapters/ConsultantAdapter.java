package com.a1techandroid.studentconsultant.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.a1techandroid.studentconsultant.AdminActivity;
import com.a1techandroid.studentconsultant.ConsultantProfile;
import com.a1techandroid.studentconsultant.Fragments.ConsultantProfileView;
import com.a1techandroid.studentconsultant.Models.ConsultantProfileModel;
import com.a1techandroid.studentconsultant.Models.UserModel;
import com.a1techandroid.studentconsultant.R;
import com.a1techandroid.studentconsultant.SharedPrefrences;
import com.a1techandroid.studentconsultant.StudentViewFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConsultantAdapter extends BaseAdapter {

    Context context;
    ArrayList<UserModel> list= new ArrayList<>();

    public ConsultantAdapter(Context context, ArrayList<UserModel> list){
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
        TextView name, phone, email;
        Button delete, approve, View;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.admin_student_consultant_cell, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.name);
            holder.phone = convertView.findViewById(R.id.phone);
            holder.email = convertView.findViewById(R.id.email);
            holder.delete = convertView.findViewById(R.id.delate);
            holder.approve = convertView.findViewById(R.id.approve);
            holder.View = convertView.findViewById(R.id.viewProfile);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final UserModel model = list.get(position);

        holder.name.setText(model.getName());
        holder.phone.setText(model.getPhone());
        holder.email.setText(model.getEmail());
        if (model.getProfileStatus() != null){
            if (model.getProfileStatus().trim().equals("Submitted") ){
                holder.approve.setVisibility(View.VISIBLE);
                holder.delete.setVisibility(View.GONE);
            }else {
                holder.delete.setVisibility(View.VISIBLE);
                holder.approve.setVisibility(View.GONE);
            }
        }


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = null;
//                if (model.getUser_type() == 1){
//                    applesQuery = ref.child("Student").orderByChild("email").equalTo(model.getEmail());
//
//                }else if (model.getUser_type() == 2) {
                    applesQuery = ref.child("Consultant").orderByChild("email").equalTo(model.getEmail());
//                }

                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                                list.remove(model);
                            notifyDataSetChanged();
//                            ordersLists.remove(order);
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
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

        holder.View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    ConsultantProfileView fragment = new ConsultantProfileView();
                UserModel userModel = new UserModel();
                userModel.setEmail(model.getEmail());
                SharedPrefrences.saveUSer(userModel, context);
                    replaceFragment(fragment);
                }

        });
       holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = ref.child("Consultant").orderByChild("email").equalTo(model.getEmail());
                applesQuery.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        UserModel userModel = snapshot.getValue(UserModel.class);

                        if (userModel.getEmail().equals(model.getEmail())){
                            Map<String, Object> postValues = new HashMap<String,Object>();
                            postValues.put("profileStatus", "Approved");
                            model.setProfileStatus("Approved");
                            ref.child("Consultant").child(snapshot.getKey()).updateChildren(postValues);
                            SharedPrefrences.saveUSer(model, context);
                            Toast.makeText(context, "Approved", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ConsultantProfile");
                            reference.child(userModel.getEmail().replace(".","")).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    ConsultantProfileModel model1 = snapshot.getValue(ConsultantProfileModel.class);
                                    Map<String, Object> postValues = new HashMap<String,Object>();
                                    postValues.put("status", "Approved");
                                    reference.child(userModel.getEmail().replace(".",""))   .updateChildren(postValues);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
////                            appleSnapshot.getRef().removeValue();
//                            Map<String, Object> postValues = new HashMap<String,Object>();
//                            postValues.put("profileStatus", "Approved");
//                            model.setStatus("Approved");
//                            ref.child(model.getEmail().replace(".","")).updateChildren(postValues);
////                            SharedPrefrences.saveUSer(model, context);
//                            Toast.makeText(context, "Approved", Toast.LENGTH_SHORT).show();
//                            notifyDataSetChanged();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//                });


            }
        });
        return convertView;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = ((AdminActivity)context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame1, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }


}



