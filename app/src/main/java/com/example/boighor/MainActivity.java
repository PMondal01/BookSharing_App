package com.example.boighor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boighor.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textView_bookshelf,textView_mybook,textView_profile,textView_opinion;
    Button button_bookshelf,button_mybook,button_profile,button_opinion;
    private long backPressedTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_bookshelf=findViewById(R.id.tv_bookshelf);
        textView_mybook=findViewById(R.id.tv_mybook);
        textView_profile=findViewById(R.id.tv_profile);
        textView_opinion=findViewById(R.id.tv_opinion);

        button_bookshelf=findViewById(R.id.btn_bookshelf);
        button_mybook=findViewById(R.id.btn_mybook);
        button_profile=findViewById(R.id.btn_profile);
        button_opinion=findViewById(R.id.btn_opinion);

        Typeface myCustomFont= Typeface.createFromAsset(getAssets(),"fonts/Nikosh.ttf");

        textView_bookshelf.setTypeface(myCustomFont);
        textView_profile.setTypeface(myCustomFont);
        textView_mybook.setTypeface(myCustomFont);
        textView_opinion.setTypeface(myCustomFont);


        button_bookshelf.setOnClickListener(this);
        button_mybook.setOnClickListener(this);
        button_profile.setOnClickListener(this);
        button_opinion.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent = null;
        if(view.getId()==R.id.btn_bookshelf)
        {
            intent =new Intent(MainActivity.this,BookShelfActivity.class);
        }else if(view.getId()==R.id.btn_mybook)
        {
            intent=new Intent(this,MyBookActivity.class); //error
        }else if(view.getId()==R.id.btn_profile)
        {
            intent=new Intent(this,MyProfileActivity.class); //error

        }else if(view.getId()==R.id.btn_opinion)
        {
            intent=new Intent(this,FeedbackActivity.class); //error
        }
        startActivity(intent);

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
