package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private static final String TAG = "AddAccount";
    private Button singup;
    private EditText emailET, passwordET, nameET, usernameET;
    private FirebaseAuth mAuth;
    private FirebaseDatabaseService databaseService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        Log.d(TAG, "Initializing AddAccount activity");
        
        try {
            mAuth = FirebaseAuth.getInstance();
            databaseService = FirebaseDatabaseService.getInstance();
            Log.d(TAG, "Firebase services initialized successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error initializing Firebase services", e);
            Toast.makeText(this, "Error initializing app: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        
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

        Log.d(TAG, "Starting signup process for email: " + email);

        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || username.isEmpty()) {
            Toast.makeText(AddAccount.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verify database service is initialized
        if (databaseService == null) {
            Log.e(TAG, "Database service is not initialized");
            Toast.makeText(AddAccount.this, "Error: Database service not initialized", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(AddAccount.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                Log.d(TAG, "Creating profile for user: " + user.getUid());
                                try {
                                    // Create and save user profile
                                    Profile profile = new Profile(name, username, email, 0, 0.0, 0.0, "");
                                    Log.d(TAG, "Profile object created, attempting to save to database");
                                    
                                    databaseService.saveUserProfile(profile, new FirebaseDatabaseService.DatabaseCallback() {
                                        @Override
                                        public void onSuccess(Object result) {
                                            Log.d(TAG, "Profile saved successfully: " + result);
                                            runOnUiThread(() -> {
                                                Toast.makeText(AddAccount.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(AddAccount.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            });
                                        }

                                        @Override
                                        public void onFailure(String error) {
                                            Log.e(TAG, "Failed to save profile: " + error);
                                            runOnUiThread(() -> {
                                                Toast.makeText(AddAccount.this, "Failed to save profile: " + error, Toast.LENGTH_LONG).show();
                                            });
                                        }
                                    });
                                } catch (Exception e) {
                                    Log.e(TAG, "Error while saving profile", e);
                                    Toast.makeText(AddAccount.this, "Error saving profile: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(AddAccount.this, "Sign Up Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
