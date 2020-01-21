package com.example.boighor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boighor.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    EditText login_email, login_pass;
    Button button_signUp,button_LOGin;
    TextView tvSignIn;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private long backPressedTime = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_email =findViewById(R.id.email_log);
        login_pass =findViewById(R.id.pass_log);
        /* button_signUp=findViewById(R.id.signup);*/
        button_LOGin=findViewById(R.id.btn_login);
        tvSignIn=findViewById(R.id.tvSignIn);

        /* button_signUp.setOnClickListener(this);*/
        button_LOGin.setOnClickListener(this);
        tvSignIn.setOnClickListener(this);
        progressBar=findViewById(R.id.progress);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {


        switch ((view.getId()))
        {
            case R.id.btn_login:
                userLogin();
                break;

            case R.id.tvSignIn:
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                break;
        }


    }

    private void userLogin() {

        String email= login_email.getText().toString().trim();
        String password=login_pass.getText().toString().trim();

        if(email.isEmpty())
        {
            login_email.setError("Enter email ");
            login_email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            login_email.setError("Enter Valid email");
            login_email.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            login_pass.setError("Enter password ");
            login_pass.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            login_pass.setError("Enter 6 digit password");
            login_pass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);


        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);

                if(task.isSuccessful())
                {

                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }else
                {
                    /* Toast.makeText(getApplicationContext(),"Error :" +task.getException().getMessage(),Toast.LENGTH_LONG).show();*/

                    Toast.makeText(getApplicationContext(),"Email or Password doesn't match",Toast.LENGTH_LONG).show();


                }

            }
        });

    }


    @Override
    public void onBackPressed() {        // to prevent irritating accidental logouts
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 1000) {    // 2 secs
            backPressedTime = t;
            Toast.makeText(this, "Press again to exit",
                    Toast.LENGTH_SHORT).show();
        } else {    // this guy is serious
            // clean up
            super.onBackPressed();       // bye
        }
    }
}

