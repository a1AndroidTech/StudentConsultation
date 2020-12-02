package com.a1techandroid.studentconsultant.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.a1techandroid.studentconsultant.LoginActivity;
import com.a1techandroid.studentconsultant.MainActivity;
import com.a1techandroid.studentconsultant.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
                    resetPasswordDialog();
                }else if (position == 2){
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.kiloo.subwaysurf")));

                }else if (position == 3){
                    AboutAppfragment fragment = new AboutAppfragment();
                    FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.contentFrame, fragment);
                    fragmentTransaction.addToBackStack(fragment.toString());
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.commit();

                }else if (position == 4){

                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("text/plain");
                    share.putExtra(Intent.EXTRA_TEXT, "I'm being sent!!");
                    context.startActivity(Intent.createChooser(share, "Share Text"));
                }else if (position == 5){
                    FirebaseAuth.getInstance().signOut();
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            }
        });
        return convertView;
    }

    public void resetPasswordDialog(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.forget_password_dialog);

        final EditText editText = (EditText) dialog.findViewById(R.id.email);
        editText.setText(user.getEmail());
        final EditText editText2 = (EditText) dialog.findViewById(R.id.password);
//        editText.setText(user.get);
        Button btnSave  = (Button) dialog.findViewById(R.id.sendEmail);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthCredential credential = EmailAuthProvider
                        .getCredential(editText.getText().toString(), editText2.getText().toString());

                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(editText2.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(context, "Password Updated", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(context, "Error Password not updated", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dialog.show();
    }

}
