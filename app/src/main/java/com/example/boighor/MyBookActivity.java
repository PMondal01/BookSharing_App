package com.example.boighor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MyBookActivity extends AppCompatActivity implements View.OnClickListener {

    Button button_chooseBook, button_displayBook, button_save;
    private ImageView imageView;
    private Uri imageuri;
    private CheckBox checkBox_box;
    private EditText bname;
    private EditText wname;
    private EditText Number_my;
    private EditText price;
    private LinearLayout linearLayout_price;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    StorageTask storageTask;
    ProgressBar progressBar;

    int image_check=0;


    private static final int IMAGE_REQUEST = 1;
    /*  private static final int RESULT_OK=1;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book);

        databaseReference = FirebaseDatabase.getInstance().getReference("Upload");
        storageReference = FirebaseStorage.getInstance().getReference("Upload");

        button_chooseBook = findViewById(R.id.btn_choose);
       /* button_displayBook = findViewById(R.id.btn_display);*/
        button_save = findViewById(R.id.btn_save);
        imageView = findViewById(R.id.image);
        checkBox_box = findViewById(R.id.checkbox);

        bname=findViewById(R.id.book_name);
        wname=findViewById(R.id.writer_name);
        Number_my =findViewById(R.id.mobile_number);
        price=findViewById(R.id.price);
        progressBar=findViewById(R.id.progress);

        button_chooseBook.setOnClickListener(this);
     /*   button_displayBook.setOnClickListener(this);*/
        button_save.setOnClickListener(this);
       /* checkBox_box.setOnClickListener(this);*/
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_choose:

                fileChooser();

                break;

            /*case R.id.btn_display:

                break;*/

            case R.id.btn_save:

                if(storageTask!=null && storageTask.isInProgress())
                {
                    Toast.makeText(getApplicationContext(),"Wait till complete",Toast.LENGTH_LONG).show();

                }

                else {
                    saveData();

                }

                break;

            case R.id.checkbox:
                checkBox_Open();
        }

    }


    private void checkBox_Open() {

        if (checkBox_box.isChecked()) {

            int check = 1;

            linearLayout_price.setVisibility(View.INVISIBLE);
        } else {

            int no_check = 0;
            linearLayout_price.setVisibility(View.VISIBLE);
        }


    }

    void fileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && requestCode != RESULT_OK && data != null && data.getData() != null) {
            imageuri = data.getData();
            Picasso.with(this).load(imageuri).into(imageView);
        }
        image_check=image_check+1;
    }




    public String getFileExtension(Uri imageUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }


    private void saveData() {

        final String book_name = bname.getText().toString().trim();
        final String writer_name = wname.getText().toString().trim();
        final String My_number = Number_my.getText().toString().trim();
        final String Price = price.getText().toString().trim();






        if (book_name.isEmpty()) {
            bname.setError("বইয়ের নাম দিন");
            bname.requestFocus();
            return;
        }

        if (writer_name.isEmpty()) {
            wname.setError("লেখকের দাম দি্ন");
            wname.requestFocus();
            return;
        }

       if   (Price.isEmpty()) {
            price.setError("বইয়ের দাম লিখুন");
            price.requestFocus();
            return;
        }

        if  (My_number.isEmpty()) {
            Number_my.setError("মোবাইল নাম্বার দিন");
            Number_my.requestFocus();
            return;
        }

        if(image_check==0)
        {


            Toast.makeText(getApplicationContext(), "বইয়ের ছবি দিন", Toast.LENGTH_LONG).show();
            return;

        }

        progressBar.setVisibility(View.VISIBLE);



        StorageReference ref = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageuri));

        ref.putFile(imageuri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "book added", Toast.LENGTH_LONG).show();


                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while ((!uriTask.isSuccessful()));
                        Uri downloadUrl=uriTask.getResult();


                        Upload upload = new Upload(book_name,writer_name,Price,downloadUrl.toString(),My_number);

                        String uploadId = databaseReference.push().getKey();
                        databaseReference.child(uploadId).setValue(upload);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...

                        Toast.makeText(getApplicationContext(), "book is not added", Toast.LENGTH_LONG).show();

                    }
                });


    }
}
