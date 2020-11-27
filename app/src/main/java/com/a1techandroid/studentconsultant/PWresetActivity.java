package com.a1techandroid.studentconsultant;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;


public class PWresetActivity extends AppCompatActivity {

    private Button btnReset;
    public EditText email;
    public TextInputLayout emailError;
    TextView BackToSignIn;
    private FirebaseAuth auth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_pwreset);
        initializeGUI();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailTxt = email.getText().toString();
resetPassword(emailTxt);
            }
        });


        BackToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PWresetActivity.this,LoginActivity.class));
                finish();
            }
        });


    }


    private void initializeGUI(){

        email = (EditText) findViewById(R.id.email);
        emailError = (TextInputLayout) findViewById(R.id.emailError);
        btnReset = findViewById(R.id.btnReset);
        BackToSignIn= findViewById(R.id.login);
        progressBar=findViewById(R.id.progressBar);

//        firebaseAuth = FirebaseAuth.getInstance();

    }
    public void resetPassword(String email){
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(PWresetActivity.this, "We have sent you instructions on your email to reset your password!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PWresetActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }

                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

}
