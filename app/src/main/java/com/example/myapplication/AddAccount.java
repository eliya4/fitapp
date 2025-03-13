package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddAccount extends Activity {
private Button singup;
private EditText emailET, passwordET, nameET, usernameET;
private FirebaseAuth mAuth;

 protected  void  onCreate(Bundle savedInstanceState)
 {

     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_add_account);
     mAuth= FirebaseAuth.getInstance();
     emailET=findViewById(R.id.email);
     passwordET =findViewById(R.id.password);
     nameET =findViewById(R.id.full_name);
     usernameET =findViewById(R.id.username);
     singup=findViewById(R.id.create_account_button);
     singup.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             handlesignup();
         }
     });




 }
 private void handlesignup()
 {
     String email=emailET.getText().toString().trim();
     String password=passwordET.getText().toString().trim();
     String name=nameET.getText().toString().trim();
     String username=usernameET.getText().toString().trim();
     //shi how do i use the name an user name?
     Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(AddAccount.this, new OnCompleteListener<AuthResult>() {
         @Override
         public void onComplete(@NonNull Task<AuthResult> task) {
             if (task.isSuccessful()) {
                 // Sign-up successful, get the newly created user
                 FirebaseUser user = mAuth.getCurrentUser();
                 Intent intent = new Intent(AddAccount.this, MainActivity.class);
                 startActivity(intent);
                 finish(); // Close the login activity
             } else {
                 Toast.makeText(AddAccount.this, "Sign Up Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
             }
         }




     };
 }



}
