package com.example.myapplication;

public class Food {
    private String id;
    private String name;
    private String description;
    private int calories;
    private String type;
    private int imageResource;

    // Constructor המלא
    public Food(String name, String description, int calories, String type) {
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.type = type;
        this.imageResource = imageResource;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public String getType() {
        return type;
    }

    public int getImageResource() {
        return imageResource;
    }
}
