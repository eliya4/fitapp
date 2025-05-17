package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ExercisieLay extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExerciseAdapter exerciseAdapter;
    private List<Exercise> exerciseList;
    private List<Exercise> filteredList;
    private EditText searchEditText;
    private ImageView searchIcon;
    private ImageView back;
    private TextView workoutTitle;
    private Button saveWorkoutButton;
    private String workoutName;
    private DatabaseReference userRef;
    private Map<String, Exercise> selectedExercises = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        // קבלת מזהה המשתמש
        String userId = getUserId();
        if (userId == null) {
            // אם המשתמש לא מחובר, מחזירים למסך ההתחברות
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // קבלת שם האימון
        workoutName = getIntent().getStringExtra("workout_name");
        if (workoutName == null || workoutName.isEmpty()) {
            showCreateWorkoutDialog(); // אם אין שם אימון, פותחים חלון לשם אימון
            return;
        }

        // הגדרת ה-Database Reference
        userRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("workouts").child(workoutName);

        // אתחול הרכיבים
        recyclerView = findViewById(R.id.recyclerViewExercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchEditText = findViewById(R.id.search_exercises);
        searchIcon = findViewById(R.id.search_icon);
        back = findViewById(R.id.ReturnBtn);
        workoutTitle = findViewById(R.id.workout_title);
        saveWorkoutButton = findViewById(R.id.save_workout);

        // הצגת שם האימון
        workoutTitle.setText(workoutName);

        // טעינת רשימת התרגילים
        exerciseList = new ArrayList<>();
        loadExercises();
        filteredList = new ArrayList<>(exerciseList);

        // יצירת האדפטר
        exerciseAdapter = new ExerciseAdapter(filteredList, exercise -> {
            selectedExercises.put(exercise.getName(), exercise);
            Toast.makeText(ExercisieLay.this, exercise.getName() + " נוסף לאימון", Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(exerciseAdapter);

        // כפתור שמירה
        saveWorkoutButton.setOnClickListener(v -> saveWorkoutToFirebase());

        // כפתור חזרה
        back.setOnClickListener(v -> {
            Intent intent = new Intent(ExercisieLay.this, MainActivity.class);
            startActivity(intent);
        });

        // חיפוש תרגילים
        searchIcon.setOnClickListener(v -> {
            String query = searchEditText.getText().toString().trim();
            filterExercises(query);
        });
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

    // טעינת רשימת התרגילים (קבועה)
    private void loadExercises() {
        exerciseList.add(new Exercise("Push Up", "Upper body workout", R.drawable.push_up));
        exerciseList.add(new Exercise("Squat", "Lower body workout", R.drawable.squat));
        exerciseList.add(new Exercise("Plank", "Core workout", R.drawable.plank));
        exerciseList.add(new Exercise("Lunges", "Leg workout", R.drawable.lunges));
        exerciseList.add(new Exercise("Burpees", "Full body workout", R.drawable.burpees));
        exerciseList.add(new Exercise("Crunches", "Ab workout", R.drawable.crunches));
        exerciseList.add(new Exercise("Mountain Climbers", "Cardio and core workout", R.drawable.mountain_climbers));
        exerciseList.add(new Exercise("Bicep Curls", "Arm workout", R.drawable.bicep_curls));
        exerciseList.add(new Exercise("Tricep Dips", "Arm workout", R.drawable.tricep_dips));
        exerciseList.add(new Exercise("Pull Up", "Upper body workout", R.drawable.pull_up));
        exerciseList.add(new Exercise("Deadlift", "Lower back and leg workout", R.drawable.deadlift));
        exerciseList.add(new Exercise("Bench Press", "Chest workout", R.drawable.bench_press));
        exerciseList.add(new Exercise("Leg Press", "Leg workout", R.drawable.leg_press));
        exerciseList.add(new Exercise("Calf Raise", "Calf workout", R.drawable.calf_raise));
        exerciseList.add(new Exercise("Russian Twist", "Core workout", R.drawable.russian_twist));
        exerciseList.add(new Exercise("Side Plank", "Core and oblique workout", R.drawable.side_plank));
        exerciseList.add(new Exercise("High Knees", "Cardio workout", R.drawable.high_knees));
        exerciseList.add(new Exercise("Jumping Jacks", "Full body cardio workout", R.drawable.jumping_jacks));
        exerciseList.add(new Exercise("Dumbbell Fly", "Chest workout", R.drawable.dumbbell_fly));
    }

    // שמירת אימון ב-Firebase
    private void saveWorkoutToFirebase() {
        if (selectedExercises.isEmpty()) {
            Toast.makeText(this, "אין תרגילים באימון", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> workoutData = new HashMap<>();
        workoutData.put("name", workoutName);

        Map<String, Object> exercisesData = new HashMap<>();
        for (Exercise exercise : selectedExercises.values()) {
            exercisesData.put(exercise.getName(), exercise.getDescription());
        }

        workoutData.put("exercises", exercisesData);

        userRef.setValue(workoutData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(ExercisieLay.this, "האימון '" + workoutName + "' נשמר בהצלחה!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ExercisieLay.this, "שגיאה בשמירת האימון: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // סינון תרגילים לפי חיפוש
    private void filterExercises(String query) {
        filteredList.clear();
        for (Exercise exercise : exerciseList) {
            if (exercise.getName().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))) {
                filteredList.add(exercise);
            }
        }
        exerciseAdapter.notifyDataSetChanged();
    }

    // יצירת דיאלוג לשם אימון
    private void showCreateWorkoutDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_create_workout);
        dialog.setCancelable(true);

        EditText workoutNameInput = dialog.findViewById(R.id.workout_name_input);
        Button createWorkoutButton = dialog.findViewById(R.id.create_workout_button);

        createWorkoutButton.setOnClickListener(v -> {
            String workoutName = workoutNameInput.getText().toString().trim();
            if (!workoutName.isEmpty()) {
                this.workoutName = workoutName;
                workoutTitle.setText(workoutName);
                dialog.dismiss();
            } else {
                Toast.makeText(this, "אנא הזן שם לאימון", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}
