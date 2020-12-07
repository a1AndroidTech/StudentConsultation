package com.a1techandroid.studentconsultant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        auth=FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (auth.getCurrentUser() != null) {

                    if (auth.getCurrentUser().getEmail().equals("admin@gmail.com")){
                        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                        finish();
                    }else {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }

                }else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }            }
        }, 3000);
    }
}
