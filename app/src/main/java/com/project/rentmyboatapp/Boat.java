package com.project.rentmyboatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.rentmyboatapp.user.User;

import java.util.HashMap;
import java.util.UUID;

public class Boat extends AppCompatActivity {

    private EditText desc, capacity, price;
    private ImageView boatimage;
    public Uri boatimageUri;
    private Button btnCancel, btnSave, btnLogout;
    private FirebaseAuth authenticate;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseFirestore fstore;
    private User user;

    //private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boat);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        fstore = FirebaseFirestore.getInstance();


        desc = findViewById(R.id.edit_desc);
        capacity = findViewById(R.id.edit_capacity);
        price = findViewById(R.id.edit_price);
        boatimage = findViewById(R.id.boatimage);
        btnCancel = findViewById(R.id.Cancel_btn);
        btnSave = findViewById(R.id.Saved_btn);
        btnLogout = findViewById(R.id.Logout_button);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit();
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Desc = desc.getText().toString();
                String Capacity = capacity.getText().toString();
                String Price = price.getText().toString();
                String id = UUID.randomUUID().toString();


                saveToFirestore(id, desc, capacity, price);
            }
        });
    }

    private void cancel() {
    }

    private void exit() {

    }


    private void saveToFirestore(String id, EditText desc, EditText capacity, EditText price) {


        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("Description", desc);
        map.put("Capacity", capacity);
        map.put("Price", price);



        boatimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
            private void choosePicture(){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
                //boatimageUri = data.getData();
                //boatimage.setImageURI(boatimageUri);
                //uploadPicture();

                }
            });


    }
/*
    protected void onActivityResult(int requestCode; int resultCode; @Nullable Intent data; {
        Boat.super.onActivityResult(requestCode, resultCode, data);
        if (!(requestCode == 1 && resultCode == RESULT_OK && data!= null && data.getData()!= null)) {
            return;
        }
*/
    private void uploadPicture() {
        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/"+ randomKey);

        riversRef.putFile(boatimageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Unsuccessful upload
                        Toast.makeText((getApplicationContext()), "Failed", Toast.LENGTH_LONG).show();

                    }
                });
    }
}
