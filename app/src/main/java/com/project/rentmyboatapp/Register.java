package com.project.rentmyboatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.rentmyboatapp.user.User;


import java.util.HashMap;
import java.util.Map;

import settings.FirebaseSettings;

public class Register extends AppCompatActivity {

    private EditText fullName, email, password, phoneNumber;
    private Button btnCreate;
    private CheckBox chkRegisterOwner,chkRegisterRenter;
    private FirebaseAuth authenticate;
    private FirebaseFirestore fstore;
    private User user;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        fstore = FirebaseFirestore.getInstance();


        fullName = findViewById(R.id.editName);
        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPassword);
        phoneNumber = findViewById(R.id.editPhoneNumber);
        btnCreate = findViewById(R.id.btnCreate);
        chkRegisterOwner = findViewById(R.id.RegisterOwner);
        chkRegisterRenter = findViewById(R.id.RegisterRenter);

       btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textName = fullName.getText().toString();
                String textEmail = email.getText().toString();
                String textPassword = password.getText().toString();
                String textPhoneNumber = phoneNumber.getText().toString();

                // making sure the blanks are filled
                if( !textName.isEmpty() ){
                    if(!textPhoneNumber.isEmpty()){
                        if( !textEmail.isEmpty() ) {
                            if ( !textPassword.isEmpty() ) {
                                user = new User();
                                user.setName(textName);
                                user.setEmail(textEmail);
                                user.setPassword(textPassword);
                                user.setPhonenumber(textPhoneNumber);
                                userCreate();
                            } else {
                                Toast.makeText(Register.this, "Fill password!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Register.this, "Fill email!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Register.this, "Fill Phone Number!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Register.this, "Fill name!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void userCreate () {

        authenticate = FirebaseSettings.getFirebaseAuthenticate();
        authenticate.createUserWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() ) {
                    FirebaseUser userDb = FirebaseAuth.getInstance().getCurrentUser();
                    mDatabase.child("users").child(userDb.getUid()).setValue(user);
                    //mDatabase.setValue(user);
                    DocumentReference df = fstore.collection("Users").document(userDb.getUid());
                    Map<String,Object> userInfo = new HashMap<>();
                    userInfo.put("FullName", fullName.getText().toString());
                    userInfo.put("Email", email.getText().toString());
                    userInfo.put("PhoneNumber", phoneNumber.getText().toString());

                    Intent intent;
                    //if User is Owner
                    if (chkRegisterOwner.isChecked()) {
                        userInfo.put("isAdmin", "1");
                        df.set(userInfo);
                        intent = new Intent(Register.this, Boatowner.class);
                        startActivity(intent);
                    }
                    else if (chkRegisterRenter.isChecked()) {
                        userInfo.put("isUser", "1");
                        df.set(userInfo);
                        intent = new Intent(Register.this, User.class);
                        startActivity(intent);
                    }
                } else {
                    String exception = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        exception = "Choose a strong password";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exception = "Please, type a valid email";
                    } catch (FirebaseAuthUserCollisionException e) {
                        exception = "This account already exits";
                    } catch (Exception e) {
                        exception = "Error: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(Register.this,
                            exception,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}