package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private TextView usernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // אתחול Firebase
        mAuth = FirebaseAuth.getInstance();
        usernameTextView = findViewById(R.id.user_name);

        // טוען את שם המשתמש מה-Firebase
        loadUsername();

        // הגדרות כפתורים
        ImageButton breakfast_button = findViewById(R.id.food_breakfast_button);
        ImageButton lunch_button = findViewById(R.id.food_lunch_button);
        ImageButton dinner_button = findViewById(R.id.food_dinner_button);
        ImageButton profile_button = findViewById(R.id.profile_button);
        ImageButton chat = findViewById(R.id.chat);
        ImageButton creat = findViewById(R.id.creat_newworkout);
        TextView workoutButton = findViewById(R.id.workout);

        workoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyWorkoutsActivity.class);
            startActivity(intent);
        });

        lunch_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FoodLay.class);
            startActivity(intent);
        });

        breakfast_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FoodLay.class);
            startActivity(intent);
        });

        dinner_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FoodLay.class);
            startActivity(intent);
        });

        creat.setOnClickListener(v -> showCreateWorkoutDialog());

        chat.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AItBot.class);
            startActivity(intent);
        });

        profile_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    private void showCreateWorkoutDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_create_workout);
        dialog.setCancelable(true);

        EditText workoutNameInput = dialog.findViewById(R.id.workout_name_input);
        Button createWorkoutButton = dialog.findViewById(R.id.create_workout_button);

        createWorkoutButton.setOnClickListener(v -> {
            String workoutName = workoutNameInput.getText().toString().trim();
            if (!workoutName.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, ExercisieLay.class);
                intent.putExtra("workout_name", workoutName);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void loadUsername() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("username");

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String username = snapshot.getValue(String.class);
                        if (username != null) {
                            usernameTextView.setText(username);
                            Toast.makeText(MainActivity.this, "Welcome, " + username + "!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        usernameTextView.setText("User not found");
                        Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    usernameTextView.setText("Error loading username");
                    Toast.makeText(MainActivity.this, "Error loading username", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            usernameTextView.setText("Not logged in");
            Toast.makeText(MainActivity.this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }
}
