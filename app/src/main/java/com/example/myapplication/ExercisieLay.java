package com.example.myapplication;

import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ExercisieLay extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ExerciseAdapter exerciseAdapter;
    private List<Exercise> exerciseList;
    private List<Exercise> filteredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        recyclerView = findViewById(R.id.recyclerViewExercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        exerciseList = new ArrayList<>();
        loadExercises();

        Intent intent = getIntent();
        String searchQuery = intent.getStringExtra("search_query");

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            filterExercises(searchQuery.trim());
        } else {
            filteredList = new ArrayList<>(exerciseList);
        }

        exerciseAdapter = new ExerciseAdapter(filteredList);
        recyclerView.setAdapter(exerciseAdapter);
    }

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

    private void filterExercises(String query) {
        filteredList = new ArrayList<>();
        for (Exercise exercise : exerciseList) {
            if (exercise.getName().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))) {
                filteredList.add(exercise);
            }
        }
    }
}
