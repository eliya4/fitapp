package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton profile_button = findViewById(R.id.profile_button);
        ImageButton chat = findViewById(R.id.chat);

        ImageButton breakfast_button = findViewById(R.id.food_breakfast_button);
        ImageButton lunch_button = findViewById(R.id.food_lunch_button);
        ImageButton dinner_button = findViewById(R.id.food_dinner_button);
        EditText searchBar = findViewById(R.id.search_exercises);

        // Search bar functionality for exercise search
        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            String query = searchBar.getText().toString().trim();
            if (!query.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, ExercisieLay.class);
                intent.putExtra("search_query", query);
                startActivity(intent);
            }
            return true;
        });

        chat.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AItBot.class);
            startActivity(intent);
        });

        profile_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        breakfast_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FoodLay.class);
            intent.putExtra("food_type", "breakfast");
            startActivity(intent);
        });

        lunch_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FoodLay.class);
            intent.putExtra("food_type", "lunch");
            startActivity(intent);
        });

        dinner_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FoodLay.class);
            intent.putExtra("food_type", "dinner");
            startActivity(intent);
        });
    }
}