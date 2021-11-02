package com.project.rentmyboatapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Main extends AppCompatActivity {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth user = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        /* user login verification*/
        if(user.getCurrentUser() !=null) {
            Log.i("CurrentUser", "Logged User!");
        } else {
            Log.i("CurrentUser", "User not logged!");
        }

        /*user login*/
        user.signInWithEmailAndPassword("paula@gmail.com", "1234")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if( task.isSuccessful() ){
                            Log.i("SignIn", "Successfully user login");
                        } else {
                            Log.i("SignIn", "Error user login" );
                        }
                    }
                });

        /*User create*/
        user.createUserWithEmailAndPassword("paula@gmail.com", "1234")
                .addOnCompleteListener(Main.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if( task.isSuccessful()) {
                            Log.i("CreateUser", "Successfully user create");
                        } else {
                            Log.i("CreateUser", "Error user create");
                        }
                    }
                });

        /*Un login User*/
        user.signOut();

        /*User Login*/
        user.signInWithEmailAndPassword("paula@gmail.com", "1234")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("signIn", "Successfully user sign");
                        } else {
                            Log.i("signIn", "Error user sign!");
                        }
                    }
                });



        Toast.makeText(this,"onCreate", Toast.LENGTH_SHORT).show();


    }
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this,"onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this,"onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this,"onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this,"onRestart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"onDestroy", Toast.LENGTH_SHORT).show();
    }

    public void btnLogin(View view){

        startActivity(new Intent(this, Login.class));
    }

    public void btnSign(View view) {

        startActivity(new Intent(this, Register.class));
    }
}