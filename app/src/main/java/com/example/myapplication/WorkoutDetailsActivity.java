package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class WorkoutDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WorkoutExerciseAdapter exerciseAdapter;
    private List<Exercise> exerciseList;
    private DatabaseReference workoutRef;
    private TextView workoutTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);

        // קבלת שם האימון
        String workoutName = getIntent().getStringExtra("workout_name");
        if (workoutName == null || workoutName.isEmpty()) {
            Toast.makeText(this, "אימון לא נמצא", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // קבלת מזהה המשתמש
        String userId = getUserId();
        if (userId == null) {
            Toast.makeText(this, "משתמש לא מחובר", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // הגדרת ה-Database Reference לאימון הנבחר
        workoutRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(userId)
                .child("workouts")
                .child(workoutName)
                .child("exercises");

        // אתחול רכיבים
        workoutTitle = findViewById(R.id.workout_title);
        workoutTitle.setText("Exercisie in the workout: " + workoutName);

        recyclerView = findViewById(R.id.recyclerViewExercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        exerciseList = new ArrayList<>();
        exerciseAdapter = new WorkoutExerciseAdapter(exerciseList);
        recyclerView.setAdapter(exerciseAdapter);

        // טעינת תרגילים
        loadExercises();
    }

    // קבלת מזהה המשתמש המחובר
    private String getUserId() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUid(); // מזהה ייחודי לכל משתמש
        } else {
            Toast.makeText(this, "משתמש לא מחובר", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    // טעינת רשימת התרגילים לאימון
    private void loadExercises() {
        workoutRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exerciseList.clear();
                for (DataSnapshot exerciseSnapshot : snapshot.getChildren()) {
                    // מתעלמים משדה ה-name
                    if (!exerciseSnapshot.getKey().equals("name")) {
                        String exerciseName = exerciseSnapshot.getKey();
                        String description = exerciseSnapshot.getValue(String.class);
                        if (exerciseName != null && description != null) {
                            exerciseList.add(new Exercise(exerciseName, description, android.R.drawable.ic_menu_info_details));
                        }
                    }
                }
                exerciseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WorkoutDetailsActivity.this, "שגיאה בטעינת התרגילים", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
