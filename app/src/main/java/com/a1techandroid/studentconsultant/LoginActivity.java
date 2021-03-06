package com.a1techandroid.studentconsultant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button login;
    TextView register, ForgotPassword;
    boolean isEmailValid, isPasswordValid;
    TextInputLayout emailError, passError;
    FirebaseAuth auth;
    DatabaseReference reference;
    DatabaseReference reference1;
    FirebaseDatabase rootNode;
    ProgressBar progressBar;
    RadioGroup rg;
    int userType = 0;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        rootNode=FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Student");
        reference1 = rootNode.getReference("Consultant");
        setContentView(R.layout.activity_login);
        mProgressDialog = new ProgressDialog(this);
        initViews();
        setUpClicks();
        radioButtonListner();
    }

    public void initViews(){
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        emailError = (TextInputLayout) findViewById(R.id.emailError);
        passError = (TextInputLayout) findViewById(R.id.passError);
        ForgotPassword= findViewById(R.id.forgetPassword);
        progressBar=findViewById(R.id.progressBar);
        rg = findViewById(R.id.radioGroup1);

    }

    public void setUpClicks(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, PWresetActivity.class));
                finish();
            }
        });
    }

    public void SetValidation() {
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

        if (isEmailValid && isPasswordValid) {
            if (userType == 0 && !email.getText().toString().equals("admin@gmail.com")){
                Toast.makeText(this, "Please Select User Type", Toast.LENGTH_SHORT).show();
            }else {
                loginUSer(email.getText().toString(), password.getText().toString());
            }
        }

    }


    public void loginUSer(String email, String password){
        mProgressDialog.setTitle("Login");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.show();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            // there was an error
                            Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        } else {

                            if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("admin@gmail.com")){
                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                if (userType == 1){
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                    Query applesQuery = reference.orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

                                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                                UserModel model = appleSnapshot.getValue(UserModel.class);
                                                SharedPrefrences.saveUSer(model, getApplicationContext());
                                                if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("admin@gmail.com")){
                                                    mProgressDialog.hide();
                                                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }else {
                                                    mProgressDialog.hide();
                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                            mProgressDialog.hide();
                                        }
                                    });
                                }else if (userType == 2){
                                    Query applesQuery = reference1.orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

                                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                                UserModel model = appleSnapshot.getValue(UserModel.class);
                                                SharedPrefrences.saveUSer(model, getApplicationContext());
                                                if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("admin@gmail.com")){
                                                    mProgressDialog.hide();
                                                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }else {
                                                    mProgressDialog.hide();
                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                            mProgressDialog.hide();
                                        }
                                    });
                                }
                                Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                mProgressDialog.hide();
                            }





                        }
//                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    public void radioButtonListner(){
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.student:
                        // do operations specific to this selection
                        userType = 1;
                        break;
                    case R.id.consultant:
                        // do operations specific to this selection
                        userType = 2;
                        break;
                }
            }
        });
    }

}

