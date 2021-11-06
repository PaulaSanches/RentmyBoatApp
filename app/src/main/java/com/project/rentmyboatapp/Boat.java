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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.rentmyboatapp.user.User;

import java.util.HashMap;
import java.util.Map;
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

    private DatabaseReference mDatabase;
    String boatpic = UUID.randomUUID().toString();


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

        mDatabase = FirebaseDatabase.getInstance().getReference();

        boatimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }

        });

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

                saveToFirestore(Desc, Capacity, Price,boatpic);
            }
        });
    }

    private void choosePicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data!= null && data.getData()!= null) {
            boatimageUri = data.getData();
            boatimage.setImageURI(boatimageUri);
            uploadPicture();
        }
    }


    private void cancel() {
    }

    private void exit() {

    }


    private void saveToFirestore( String desc, String capacity, String price, String boatpic) {

        FirebaseUser userDb = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase.child("users").child(userDb.getUid()).setValue(user);
        //mDatabase.setValue(user);
        DocumentReference df = fstore.collection("Users").document(userDb.getUid());
        Map<String,Object> boatInfo = new HashMap<>();
        boatInfo.put("Description", desc);
        boatInfo.put("Capacity", capacity);
        boatInfo.put("Price", price);
        boatInfo.put("BoatPic", boatpic);

        df.update(boatInfo);
    }

    private void uploadPicture() {
        StorageReference riversRef = storageReference.child("images/"+ boatpic);

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
