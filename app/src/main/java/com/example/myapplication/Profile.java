package com.example.myapplication;

public class Profile {
    private String name;
    private String username;
    private String email;
    private int age;
    private double weight;
    private double height;
    private String gender;

    // Required empty constructor for Firebase
    public Profile() {
    }

    public Profile(String name, String username, String email, int age, double weight, double height, String gender) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
