package com.project.rentmyboatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.project.rentmyboatapp.user.User;


import settings.FirebaseSettings;

public class Login extends AppCompatActivity {


        private EditText email, password;
        private Button buttonGo;
        private FirebaseAuth authenticate;
        private User user = new User();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

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
                        Intent intent = new Intent(Login.this, AdminActivity.class);
                        startActivity(intent);

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
    }



