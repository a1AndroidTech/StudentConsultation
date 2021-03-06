package com.a1techandroid.studentconsultant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.a1techandroid.studentconsultant.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText name, email, phone, password;
    Button register;
    TextView login;
    boolean isNameValid, isEmailValid, isPhoneValid, isPasswordValid;
    TextInputLayout nameError, emailError, phoneError, passError;
    FirebaseAuth auth;
    DatabaseReference reference;
    FirebaseDatabase rootNode;
    ProgressBar progressBar;
    RadioGroup rg;
    int userType = 0;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth=FirebaseAuth.getInstance();
        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference();
        mProgressDialog = new ProgressDialog(this);

        initViews();
        setUpClicks();
        radioButnListner();
    }

    public void initViews(){
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        login = (TextView) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        nameError = (TextInputLayout) findViewById(R.id.nameError);
        emailError = (TextInputLayout) findViewById(R.id.emailError);
        phoneError = (TextInputLayout) findViewById(R.id.phoneError);
        passError = (TextInputLayout) findViewById(R.id.passError);
        progressBar=findViewById(R.id.progressBar);
        rg = findViewById(R.id.radioGroup1);
    }

    public void setUpClicks(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to LoginActivity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void SetValidation() {
        // Check for a valid name.
        if (name.getText().toString().isEmpty()) {
            nameError.setError(getResources().getString(R.string.name_error));
            isNameValid = false;
        } else  {
            isNameValid = true;
            nameError.setErrorEnabled(false);
        }

        // Check for a valid email address.
        if (email.getText().toString().isEmpty()) {
            emailError.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            emailError.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
        } else  {
            isEmailValid = true;
            emailError.setErrorEnabled(false);
        }

        // Check for a valid phone number.
        if (phone.getText().toString().isEmpty()  ) {
            phoneError.setError(getResources().getString(R.string.phone_error));
            isPhoneValid = false;
        }else if (phone.getText().toString().length() < 11 ){
            phoneError.setError("Please put 11 digit phone number");
            isPhoneValid = false;
        }
        else  {
            isPhoneValid = true;
            phoneError.setErrorEnabled(false);
        }

        // Check for a valid password.
        if (password.getText().toString().isEmpty()) {
            passError.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else if (password.getText().length() < 6) {
            passError.setError(getResources().getString(R.string.error_invalid_password));
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
            passError.setErrorEnabled(false);
        }

        if (isNameValid && isEmailValid && isPhoneValid && isPasswordValid) {

            if (userType == 0) {
                Toast.makeText(this, "Please Select User Type", Toast.LENGTH_SHORT).show();
            } else {
                createUserOnServer(email.getText().toString(), password.getText().toString(), phone.getText().toString(), name.getText().toString());
            }
        }
    }

    public void createUserOnServer(String email, String password, String phone, String name){
        mProgressDialog.setTitle("Creating User");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.show();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            mProgressDialog.hide();
                            Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            mProgressDialog.hide();
                            progressBar.setVisibility(View.GONE);
                             UserModel user = new UserModel(name, phone, email, password, userType, "pending");
                            String uId = auth.getCurrentUser().getUid();
                            user.setUser_id(uId);
                            if (userType ==1){
                                reference.child("Student").child(reference.push().getKey()).setValue(user);
                                SharedPrefrences.saveUSer(user, getApplicationContext());
                            }else {
                                reference.child("Consultant").child(reference.push().getKey()).setValue(user);
                                SharedPrefrences.saveUSer(user, getApplicationContext());
                            }
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }


    public void radioButnListner()
    {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.student:
                        userType = 1;
                        break;

                    case R.id.consultant:
                        userType = 2;
                        break;
                }
            }
        });
    }

}
