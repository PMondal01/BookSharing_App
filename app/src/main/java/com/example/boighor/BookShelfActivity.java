package com.example.boighor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.boighor.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class BookShelfActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<Upload> uploadList;
    private ProgressBar progressBar;
    DatabaseReference databaseReference;

    FirebaseStorage firebaseStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_shelf);

        progressBar=findViewById(R.id.progress_bookshelf);
        recyclerView=findViewById(R.id.recyclerviewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        uploadList=new ArrayList<>();

        firebaseStorage=FirebaseStorage.getInstance();

        databaseReference= FirebaseDatabase.getInstance().getReference("Upload");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                uploadList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Upload upload=snapshot.getValue(Upload.class);
                    upload.setKey(snapshot.getKey());
                    uploadList.add(upload);



                }
                myAdapter=new MyAdapter(BookShelfActivity.this,uploadList);
                recyclerView.setAdapter(myAdapter);

             /*   delete*/

                myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        String text=uploadList.get(position).getBookName();
                        Toast.makeText(getApplicationContext(),text+" is selected "+position,Toast.LENGTH_LONG).show();



                    }

                    @Override
                    public void doAnyTask(int position) {

                        Toast.makeText(getApplicationContext()," do any task "+position,Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void delete(int position) {

                        Upload selectedItem=uploadList.get(position);
                        final String key=selectedItem.getKey();


                        StorageReference storageReference=firebaseStorage.getReferenceFromUrl(selectedItem.getImageUrl());
                        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                databaseReference.child(key).removeValue();
                            }
                        });



                    }
                });





                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"Error " +databaseError.getMessage(),Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);


            }
        });





    }
}
