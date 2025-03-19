package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FoodLay extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ExerciseAdapter exerciseAdapter;
    private List<Exercise> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // קישור ה-RecyclerView ל-ID ב-XML
        recyclerView = findViewById(R.id.recyclerViewExercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // סידור אנכי

        // אתחול רשימת תרגילים
        exerciseList = new ArrayList<>();
        loadExercises();  // פונקציה שמוסיפה תרגילים לדוגמה

        // יצירת ה-Adapter וחיבורו ל-RecyclerView
        exerciseAdapter = new ExerciseAdapter(exerciseList);
        recyclerView.setAdapter(exerciseAdapter);
    }


    private void loadExercises() {/*
        exerciseList.add(new Exercise("Push Up", "Upper body workout", R.drawable.push_up));
        exerciseList.add(new Exercise("Squat", "Lower body workout", R.drawable.squat));


        // עדכון הרשימה כדי להציג את השינויים
        exerciseAdapter.notifyDataSetChanged();*/
    }

}
