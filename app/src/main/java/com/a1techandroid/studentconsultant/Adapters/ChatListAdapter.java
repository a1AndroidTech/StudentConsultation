package com.a1techandroid.studentconsultant.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.a1techandroid.studentconsultant.CHat.MainActivityMessage;
import com.a1techandroid.studentconsultant.Models.ChatUSer;
import com.a1techandroid.studentconsultant.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ChatListAdapter extends BaseAdapter {

    Context context;
    ArrayList<ChatUSer> chatUSerList;

    public ChatListAdapter(Context context, ArrayList<ChatUSer> chatUSerList) {
        this.context = context;
        this.chatUSerList = chatUSerList;
    }

    @Override
    public int getCount() {
        return chatUSerList.size();
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
        TextView imageView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.chat_user, null);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.nameUSEr);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ChatUSer name = chatUSerList.get(position);

        holder.imageView.setText(name.getUserName().replace("@gmail.com",""));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, MainActivityMessage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("receiverID", name.getUserId());
                context.startActivity(i);
            }
        });
        return convertView;
    }
}
