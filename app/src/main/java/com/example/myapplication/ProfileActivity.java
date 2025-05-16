package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private TextView usernameTextView;
    private ImageButton home_button,AiBtn;
    private ImageView imageProfile;
    private TextView userDescriptionTextView;
    // ADDED: four grid ImageViews to allow picks from gallery
    private ImageView imageGrid1, imageGrid2, imageGrid3, imageGrid4;
    // ADDED: tracks which ImageView was clicked to update in onActivityResult
    private ImageView currentImageView;

    private FirebaseDatabaseService databaseService;
    private static final int REQUEST_IMAGE_PICK = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        home_button=findViewById(R.id.home_button);
        AiBtn= findViewById(R.id.ai_button);
        usernameTextView = findViewById(R.id.username);
        userDescriptionTextView = findViewById(R.id.userDescription);
        imageProfile = findViewById(R.id.imageView2);
        // ADDED: initialize the grid ImageViews
        imageGrid1 = findViewById(R.id.imageView11);
        imageGrid2 = findViewById(R.id.imageView10);
        imageGrid3 = findViewById(R.id.imageView4);
        imageGrid4 = findViewById(R.id.imageView);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        AiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, AItBot.class);
                startActivity(intent);
            }
        });


        // Profile picture click
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ADDED: set currentImageView to profile so we know which to update
                currentImageView = imageProfile;
                pickImageFromGallery();  // ADDED: common picker method
            }
        });

        // ADDED: shared click listener for grid images
        View.OnClickListener gridClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentImageView = (ImageView) v;  // ADDED: track which grid image was tapped
                pickImageFromGallery();            // ADDED: reuse picker
            }
        };
        imageGrid1.setOnClickListener(gridClickListener);
        imageGrid2.setOnClickListener(gridClickListener);
        imageGrid3.setOnClickListener(gridClickListener);
        imageGrid4.setOnClickListener(gridClickListener);

        // Initialize Firebase service
        databaseService = FirebaseDatabaseService.getInstance();

        // Load user profile
        loadUserProfile();
    }

    // ADDED: helper method to launch gallery picker
    private void pickImageFromGallery() {
        Intent pickIntent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        pickIntent.setType("image/*");
        startActivityForResult(pickIntent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // ADDED: handle the picked image for any ImageView
        if (requestCode == REQUEST_IMAGE_PICK
                && resultCode == RESULT_OK
                && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null && currentImageView != null) {
                currentImageView.setImageURI(selectedImageUri);  // ADDED: update the clicked view

                // TODO: Save the new image URI to Firebase or your database
                // e.g., databaseService.saveImageUri(userId, selectedImageUri, new Callback() { ... });
            }
        }
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
