package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

public class Exercise extends AppCompatActivity {
    private String id;
    private String name;
    private String description;
    private int duration; // in minutes
    private int caloriesBurned;
    private String type; // cardio, strength, flexibility, etc.
    private int imageResource; // for backward compatibility

    // Required empty constructor for Firebase
    public Exercise() {
    }

    // Constructor for Firebase
    public Exercise(String name, String description, int duration, int caloriesBurned, String type) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.type = type;
    }

    // Constructor for backward compatibility
    public Exercise(String name, String description, int imageResource) {
        this.name = name;
        this.description = description;
        this.imageResource = imageResource;
        this.duration = 0; // default value
        this.caloriesBurned = 0; // default value
        this.type = "strength"; // default value
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
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
