package com.project.rentmyboatapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.rentmyboatapp.user.User;


import settings.FirebaseSettings;

public class Login extends AppCompatActivity {


        private EditText email, password;
        private Button buttonGo;
        private FirebaseAuth authenticate;
        private FirebaseFirestore fStore;
        private com.project.rentmyboatapp.user.User user = new com.project.rentmyboatapp.user.User();
        boolean valid = true;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login);

            fStore = FirebaseFirestore.getInstance();

            email = findViewById(R.id.editEmail);
            password = findViewById(R.id.editPassword);
            buttonGo = findViewById(R.id.buttonGo);
            buttonGo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String textEmail = email.getText().toString();
                        String textPassword = password.getText().toString();

                        if (!textEmail.isEmpty()) {
                            if (!textPassword.isEmpty()) {
                                user.setEmail(textEmail);
                                user.setPassword(textPassword);
                                validateLogin();
                            } else {
                                Toast.makeText(Login.this, "Fill password!",
                                        Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(Login.this, "Fill email!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            );
        }

        public void validateLogin() {

            authenticate = FirebaseSettings.getFirebaseAuthenticate();
            authenticate.signInWithEmailAndPassword(
                    user.getEmail(),
                    user.getPassword()
            ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        checkUserAccessLevel(authenticate.getCurrentUser().getUid());
                    } else {
                        String exception = "";
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            exception = "Unregistered User";
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            exception = "Email and password invalid";
                        } catch (Exception e) {
                            exception = "Error: " + e.getMessage();
                            e.printStackTrace();
                        }
                        Toast.makeText(Login.this,
                                exception,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    private void checkUserAccessLevel(String uid) {

        DocumentReference df = fStore.collection("Users").document(uid);
        //extract the data
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: " + documentSnapshot. getData());

                if(documentSnapshot.getString("isAdmin")!= null){
                        //user is Admin
                   startActivity(new Intent(getApplicationContext(), Boatowner.class));
                }

                if(documentSnapshot.getString("isUser")!= null){
                    //user is normal User
                    startActivity(new Intent(getApplicationContext(), Renter.class));
                }

            }
        });

    }
}



