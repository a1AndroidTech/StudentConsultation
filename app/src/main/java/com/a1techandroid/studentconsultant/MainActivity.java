package com.a1techandroid.studentconsultant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a1techandroid.studentconsultant.Fragments.FragmentHome;
import com.a1techandroid.studentconsultant.Fragments.FragmentUniveristies;
import com.a1techandroid.studentconsultant.Fragments.SettingFragment;
import com.a1techandroid.studentconsultant.Models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {
    AnimatedBottomBar animatedBottomBar;
    FirebaseAuth auth;
    DatabaseReference reference;
    ImageView moreOption;
    public static TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference("SC");
        DatabaseReference ref = reference.child("User");
        setContentView(R.layout.activity_main);
        initViews();
        setUpAnimatedBar();
        FragmentHome home= new FragmentHome();
        replaceFragment(home);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                for(DataSnapshot ds: snapshot.getChildren()){
                    UserModel userModal = ds.getValue(UserModel.class);
                    SharedPrefrences.saveUSer(userModal, getApplicationContext());
                    Log.i("userModel", ""+userModal.getUser_type());
//                    if(userModal.getUser_id().equals(auth.getCurrentUser().getUid())) {
//                        if (userModal.getUser_type() == 1){
//                            Toast.makeText(MainActivity.this, "student", Toast.LENGTH_SHORT).show();
//                            reference.removeEventListener(valueEventListener);
//                        }else {
//                            Toast.makeText(MainActivity.this, "Consultant", Toast.LENGTH_SHORT).show();
//                        }
//
//                        reference.removeEventListener(valueEventListener);
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "canceled", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void initViews(){

        animatedBottomBar = findViewById(R.id.animatedBottomBar);
        moreOption = findViewById(R.id.moreOption);
        title = findViewById(R.id.title);
    }

    public void setUpAnimatedBar(){
        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NotNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch (newTab.getId()) {
                    case R.id.home:
                        FragmentHome home= new FragmentHome();
                        replaceFragment(home);
                        title.setText("Home");
                        break;
                    case R.id.book:
                        FragmentUniveristies fragmentUniveristies= new FragmentUniveristies();
                        replaceFragment(fragmentUniveristies);
                        title.setText("Upload Documents");
                        break;
                    case R.id.account:
                        SettingFragment settingFragment = new SettingFragment();
                        replaceFragment(settingFragment);
                        title.setText("Setting");
                        break;
                }
            }
        });

        moreOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StudentProfileActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
//        switch (item.getItemId()) {
//            case R.id.new_game:
//                //your code
//                // EX : call intent if you want to swich to other activity
//                return true;
//            case R.id.help:
//                //your code
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange( DataSnapshot dataSnapshot) {
            for(DataSnapshot ds: dataSnapshot.getChildren()){
                UserModel userModal = ds.getValue(UserModel.class);
                Log.i("userModel", ""+userModal.getUser_type());
                if(userModal.getUser_id().equals(auth.getCurrentUser().getUid())) {
                    if (userModal.getUser_type() == 1){
                        Toast.makeText(MainActivity.this, "student", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this, "Consultant", Toast.LENGTH_SHORT).show();
                    }

                    reference.removeEventListener(valueEventListener);
                }
                else{
                    auth.signOut();
                    reference.removeEventListener(valueEventListener);
                }
            }
        }


        @Override
        public void onCancelled( DatabaseError databaseError) {

        }
    };

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

}