package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

public class Food extends AppCompatActivity {
    private String id;
    private String name;
    private String description;
    private int calories; // in calories
    private String type; // main, side, drink, dessert, etc.
    private int imageResource; // for backward compatibility

    // Required empty constructor for Firebase
    public Food(String riceBowl, String simpleAndFilling, int i, String main, int rice_bowl) {
    }

    // Constructor for Firebase
    public Food(String name, String description, int calories, String type) {
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.type = type;
    }

    // Constructor for backward compatibility
    public Food(String name, String description, int imageResource) {
        this.name = name;
        this.description = description;
        this.imageResource = imageResource;
        this.calories = 0; // default value
        this.type = "main"; // default value
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}