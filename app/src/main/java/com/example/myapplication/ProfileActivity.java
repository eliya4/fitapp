package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private TextView usernameTextView;
    private ImageView imageProfile;
    private TextView userDescriptionTextView;
    private FirebaseDatabaseService databaseService;
    private static final int REQUEST_IMAGE_PICK = 1000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        usernameTextView = findViewById(R.id.username);
        userDescriptionTextView = findViewById(R.id.userDescription);
        imageProfile=findViewById(R.id.imageView2);
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickIntent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                );
                pickIntent.setType("image/*");
                startActivityForResult(pickIntent, REQUEST_IMAGE_PICK);


            }
        });


        // Initialize Firebase service
        databaseService = FirebaseDatabaseService.getInstance();

        // Load user profile
        loadUserProfile();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK
                && resultCode == RESULT_OK
                && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                imageProfile.setImageURI(selectedImageUri);
            }
        }
    }
    //שי אני עשיתי שאפשר לערוך את התמונה תזור לי להכניס את זה לדאטה ביס כדי שזה ישר ואמ אפשר שזה י היה עגול גם:)


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