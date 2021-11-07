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

public class Boatowner extends AppCompatActivity {

    private EditText name, address, bankdetails;
    private ImageView profilePic;
    public Uri imageUri;
    private Button btnMyboats, btnEdit, btnSave, btnLogout;
    private FirebaseAuth authenticate;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseFirestore fstore;
    private User user;

    //private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boatowner);

       storage = FirebaseStorage.getInstance();
       storageReference = storage.getReference();
       fstore = FirebaseFirestore.getInstance();


        name = findViewById(R.id.edit_Name);
        address = findViewById(R.id.edit_Address);
        bankdetails = findViewById(R.id.edit_Bank);
        profilePic = findViewById(R.id.profilePic);
        btnMyboats = findViewById(R.id.Myboats_btn);
        btnEdit = findViewById(R.id.Edit_btn);
        btnSave = findViewById(R.id.Save_btn);
        btnLogout = findViewById(R.id.Logout_btn);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit();
            }
        });

        btnMyboats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(Boatowner.this, Boat.class));
            }

        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name = name.getText().toString();
                String Address = address.getText().toString();
                String Bank = bankdetails.getText().toString();
                String id = UUID.randomUUID().toString();
                saveToFirestore(id, name, address, bankdetails);
            }
        });
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data!= null && data.getData()!= null) {
            imageUri = data.getData();
            profilePic.setImageURI(imageUri);
            uploadPicture();
        }
    }


    private void exit() {
    }

    private void update() {
        String Name = name.getText().toString().trim();
        String Address = address.getText().toString().trim();
        String Bank = bankdetails.getText().toString().trim();
    }

    private void saveToFirestore(String id, EditText name, EditText address, EditText bankdetails) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("Name", name);
        map.put("Address", address);
        map.put("Bank Details", bankdetails);

    }

    private void uploadPicture() {
        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/"+ randomKey);
        riversRef.putFile(imageUri)
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

















