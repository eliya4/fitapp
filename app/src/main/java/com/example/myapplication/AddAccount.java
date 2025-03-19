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
import com.google.firebase.database.FirebaseDatabase;

public class AddAccount extends Activity {
    private Button singup;
    private EditText emailET, passwordET, nameET, usernameET;
    private FirebaseAuth mAuth;
    private FirebaseDatabaseService databaseService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        mAuth = FirebaseAuth.getInstance();
        databaseService = FirebaseDatabaseService.getInstance();
        
        emailET = findViewById(R.id.email);
        passwordET = findViewById(R.id.password);
        nameET = findViewById(R.id.full_name);
        usernameET = findViewById(R.id.username);
        singup = findViewById(R.id.create_account_button);
        
        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlesignup();
            }
        });
    }

    private void handlesignup() {
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        String name = nameET.getText().toString().trim();
        String username = usernameET.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || username.isEmpty()) {
            Toast.makeText(AddAccount.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(AddAccount.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                // Create and save user profile
                                Profile profile = new Profile(name, username, email, 0, 0.0, 0.0, "");
                                databaseService.saveUserProfile(profile, new FirebaseDatabaseService.DatabaseCallback() {
                                    @Override
                                    public void onSuccess(Object result) {
                                        Toast.makeText(AddAccount.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(AddAccount.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(String error) {
                                        Toast.makeText(AddAccount.this, "Failed to save profile: " + error, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(AddAccount.this, "Sign Up Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
