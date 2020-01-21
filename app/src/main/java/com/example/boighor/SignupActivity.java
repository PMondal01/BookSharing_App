package com.example.boighor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText user_name, user_mobile, user_address, user_email, user_pass,user_fburl;
    private Button signup, login;
    private TextView tvSignIn;
    private ProgressBar progressBar;
    LinearLayout facebook_layout;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        user_name = findViewById(R.id.name);
        user_address = findViewById(R.id.address);
        user_mobile = findViewById(R.id.mobile);
        user_email = findViewById(R.id.email_signup);
        user_pass = findViewById(R.id.pass_signup);
        /* user_fburl=findViewById(R.id.fb_url);*/
        progressBar=findViewById(R.id.progress);

        signup = findViewById(R.id.btn_signup);
        /*  login = findViewById(R.id.login_signup);*/
        tvSignIn=findViewById(R.id.tvSignIn);

        signup.setOnClickListener(this);
        tvSignIn.setOnClickListener(this);
        /* login.setOnClickListener(this);*/

        /*facebook_layout=findViewById(R.id.fb_layout);*/
    }

    @Override
    public void onClick(View view) {
        switch ((view.getId())) {
            case R.id.btn_signup:
                userRegister();
                break;

            case R.id.tvSignIn:
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void userRegister() {


        String name = user_name.getText().toString();
        String address = user_address.getText().toString();
        String mobile = user_mobile.getText().toString().trim();
        String email = user_email.getText().toString().trim();
        String password = user_pass.getText().toString().trim();
        /* String faceBookurl=user_fburl.getText().toString().trim();*/

        if (name.isEmpty()) {
            user_name.setError("Enter your name ");
            user_name.requestFocus();
            return;
        }

        if (address.isEmpty()) {
            user_address.setError("Enter your address ");
            user_address.requestFocus();
            return;
        }

      /*  if (mobile.isEmpty() && faceBookurl.isEmpty()) {
            facebook_layout.setVisibility(View.VISIBLE);
            user_fburl.setError("Mobile No or FB url is required ");
            user_fburl.requestFocus();
            return;
        }*/


      /*  if(mobile.isEmpty() && faceBookurl.isEmpty())
        {
            facebook_layout.setVisibility(View.VISIBLE);
            Toast.makeText(SignupActivity.this, "Mobile no or FB url is required",
                    Toast.LENGTH_LONG).show();
        }*/

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            user_email.setError("Enter Valid email");
            user_email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            user_pass.setError("Enter password ");
            user_pass.requestFocus();
            return;
        }

      /*  if (password.length() < 6) {
            user_pass.setError("Enter 6 digit password");
            user_pass.uidrequestFocus();
            return;
        }*/

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()) {

                    Toast.makeText(getApplicationContext(),"Signup Successfully Completed",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(intent);



                } else {

                    if(task.getException()instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplicationContext(),"User already Registered",Toast.LENGTH_LONG).show();

                    }else
                    {
                        Toast.makeText(getApplicationContext(),"Error :" +task.getException().getMessage(),Toast.LENGTH_LONG).show();

                    }

                }

            }
        });
    }
}
