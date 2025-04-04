package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private TextView usernameTextView;
    private TextView userDescriptionTextView;
    private FirebaseDatabaseService databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        usernameTextView = findViewById(R.id.username);
        userDescriptionTextView = findViewById(R.id.userDescription);

        // Initialize Firebase service
        databaseService = FirebaseDatabaseService.getInstance();

        // Load user profile
        loadUserProfile();
    }

    private void loadUserProfile() {
        databaseService.getUserProfile(new FirebaseDatabaseService.DatabaseCallback() {
            @Override
            public void onSuccess(Object result) {
                Profile profile = (Profile) result;
                updateUI(profile);
            }

            @Override
            public void onFailure(String error) {
                // Handle error
            }
        });
    }

    private void updateUI(Profile profile) {
        usernameTextView.setText(profile.getName());
        userDescriptionTextView.setText(profile.getUsername());
    }
} 