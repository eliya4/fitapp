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
        //TODO: fix the id to the right one
        ImageButton list=findViewById(R.id.add_exercise_button);
        ImageButton profile_button=findViewById(R.id.profile_button);
        ImageButton breakfast_button=findViewById(R.id.food_breakfast_button);
        ImageButton lunch_button=findViewById(R.id.food_lunch_button);
        ImageButton dinner_button=findViewById(R.id.food_dinner_button);
        EditText searchBar = findViewById(R.id.search_exercises);
        //מימוש הסארצ באר ומעבר אימפרומנציה חיפוש לריסיקל
        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            String query = searchBar.getText().toString().trim();
            if (!query.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, ExercisieLay.class);
                intent.putExtra("search_query", query);
                startActivity(intent);
            }
            return true;
        });

        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);

            }
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, ExercisieLay.class);
                startActivity(intent);
            }
        });
        breakfast_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Food.class);
                startActivity(intent);
            }
        });
        lunch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Food.class);
                startActivity(intent);
            }
        });
       dinner_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Food.class);
                startActivity(intent);
            }
        });

    }
}