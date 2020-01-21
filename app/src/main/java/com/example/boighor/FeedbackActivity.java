package com.example.boighor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.boighor.R;

public class FeedbackActivity extends AppCompatActivity {

    private EditText editText_name, editText_feedback;
    private Button button_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        editText_name=findViewById(R.id.name);
        editText_feedback=findViewById(R.id.feedback);
        button_send=findViewById(R.id.send);


        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_SENDTO);
                /* intent.setType("message/rfc822");*/
                intent.setType("message/html");
                intent.setData(Uri.parse("mailto:developer@hotmail.com"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String("pmondal2424@hotmail.com"));
                intent.putExtra(intent.EXTRA_SUBJECT,"User Feedback");
                intent.putExtra(intent.EXTRA_TEXT,"Name :  " +editText_name.getText()+"\n"+"\n Message:  "+"\n"+"\n"+editText_feedback.getText());
                try{
                    startActivity(Intent.createChooser(intent,"Please select Email"));
                }
                catch(android.content.ActivityNotFoundException ex)
                {
                    Toast.makeText(FeedbackActivity.this,"Error",Toast.LENGTH_LONG).show();
                }




            }
        });


    }
    }

