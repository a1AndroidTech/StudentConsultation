package com.a1techandroid.studentconsultant.CHat;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a1techandroid.studentconsultant.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<FriendlyMessage> {
    public static final int FIRST_TYPE = 1, SECOND_TYPE = 2;
    public MessageAdapter(Context context, int resource, List<FriendlyMessage> objects) {
        super(context, resource, objects);
    }

    @Override
    public int getItemViewType(int position) {
//        final ChatMessageModel chatHistory = chatList.get(position);
        if (getItem(position).getType().equals("1")) {
            return FIRST_TYPE;
        } else {
            return SECOND_TYPE;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            if (getItemViewType(position) == FIRST_TYPE){
                convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
            }else if (getItemViewType(position) == SECOND_TYPE){
                convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.receiver_type_message, parent, false);
            }
        }

        ImageView photoImageView = convertView.findViewById(R.id.photoImageView);
        TextView messageTextView =  convertView.findViewById(R.id.messageTextView);
        TextView authorTextView =  convertView.findViewById(R.id.nameTextView);

        FriendlyMessage message = getItem(position);

        boolean isPhoto = message.getType() != null;
        if (isPhoto) {
            messageTextView.setVisibility(View.GONE);
            photoImageView.setVisibility(View.VISIBLE);
            Glide.with(photoImageView.getContext())
                    .load(message.getText())
                    .into(photoImageView);
        } else {
            messageTextView.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.GONE);

        }
        messageTextView.setText(message.getName());

        authorTextView.setText(message.getText());

        return convertView;
    }

}
