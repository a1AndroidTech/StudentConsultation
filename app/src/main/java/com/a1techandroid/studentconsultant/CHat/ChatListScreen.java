package com.a1techandroid.studentconsultant.CHat;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.a1techandroid.studentconsultant.Adapters.ChatListAdapter;
import com.a1techandroid.studentconsultant.Models.ChatUSer;
import com.a1techandroid.studentconsultant.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatListScreen extends AppCompatActivity {
    ListView listView;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefe;
    private DatabaseReference reference;
    ArrayList<ChatUSer> list = new ArrayList<>();
    ChatListAdapter chatListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list_screen);
        mDatabase=FirebaseDatabase.getInstance();
        reference=mDatabase.getReference("ChatUser");

        listView = findViewById(R.id.listView);


        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatUSer chatUSer = snapshot.getValue(ChatUSer.class);
                list.add(chatUSer);
                chatListAdapter = new ChatListAdapter(getApplicationContext(), list);
                listView.setAdapter(chatListAdapter);
                chatListAdapter.notifyDataSetChanged();
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
    }

}
