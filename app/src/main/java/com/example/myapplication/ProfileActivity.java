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
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private TextView usernameTextView;
    private ImageButton home_button, AiBtn;
    private ImageView imageProfile;
    private ImageView imageGrid1, imageGrid2, imageGrid3, imageGrid4;
    private ImageView currentImageView;
    private static final int REQUEST_IMAGE_PICK = 1000;
    private static final int REQUEST_PERMISSION = 2000;
    private FirebaseUser currentUser;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        checkStoragePermission();
        loadUsername();


        // Initialize Firebase
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            finish(); // If not logged in, exit the activity
            return;
        }
        userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid()).child("gallery_images");

        // Initialize views
        home_button = findViewById(R.id.home_button);
        AiBtn = findViewById(R.id.ai_button);
        usernameTextView = findViewById(R.id.username);
        imageProfile = findViewById(R.id.imageView2);
        imageGrid1 = findViewById(R.id.imageView11);
        imageGrid2 = findViewById(R.id.imageView10);
        imageGrid3 = findViewById(R.id.imageView4);
        imageGrid4 = findViewById(R.id.imageView);

        // Set click listeners for gallery images
        setGalleryClickListeners();

        // Load saved images
        loadImagesFromFirebase();

        // Set navigation buttons
        home_button.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
        AiBtn.setOnClickListener(v -> startActivity(new Intent(this, AItBot.class)));
    }


    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "הרשאה אושרה", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "אין הרשאה לגשת לתמונות", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setGalleryClickListeners() {
        imageProfile.setTag("profile");
        imageGrid1.setTag("grid1");
        imageGrid2.setTag("grid2");
        imageGrid3.setTag("grid3");
        imageGrid4.setTag("grid4");

        View.OnClickListener gridClickListener = v -> {
            currentImageView = (ImageView) v;
            pickImageFromGallery();
        };

        imageProfile.setOnClickListener(gridClickListener);
        imageGrid1.setOnClickListener(gridClickListener);
        imageGrid2.setOnClickListener(gridClickListener);
        imageGrid3.setOnClickListener(gridClickListener);
        imageGrid4.setOnClickListener(gridClickListener);
    }

    private void pickImageFromGallery() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        startActivityForResult(pickIntent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null && currentImageView != null) {
                currentImageView.setImageURI(selectedImageUri);
                String key = (String) currentImageView.getTag();
                saveImageUriToFirebase(key, selectedImageUri.toString());
            }
        }
    }


    private void saveImageUriToFirebase(String key, String uri) {
        Map<String, Object> imageData = new HashMap<>();
        imageData.put("uri", uri);

        userRef.child(key).setValue(imageData).addOnSuccessListener(aVoid -> {
            Toast.makeText(ProfileActivity.this, "תמונה נשמרה בהצלחה", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(ProfileActivity.this, "שגיאה בשמירת התמונה: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
    private void loadUsername() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(userId)
                    .child("username");

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String username = snapshot.getValue(String.class);
                        if (username != null) {
                            usernameTextView.setText(username);
                            Toast.makeText(ProfileActivity.this, "Welcome, " + username + "!", Toast.LENGTH_SHORT).show();
                        } else {
                            usernameTextView.setText("User not found");
                            Toast.makeText(ProfileActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        usernameTextView.setText("User not found");
                        Toast.makeText(ProfileActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    usernameTextView.setText("Error loading username");
                    Toast.makeText(ProfileActivity.this, "Error loading username", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            usernameTextView.setText("Not logged in");
            Toast.makeText(ProfileActivity.this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }


    private void loadImagesFromFirebase() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot imageSnapshot : snapshot.getChildren()) {
                    String key = imageSnapshot.getKey();
                    String uri = imageSnapshot.child("uri").getValue(String.class);
                    if (uri != null) {
                        int resID = getResources().getIdentifier(key, "id", getPackageName());
                        ImageView targetView = findViewById(resID);
                        if (targetView != null) {
                            targetView.setImageURI(Uri.parse(uri));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "שגיאה בטעינת התמונות: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
