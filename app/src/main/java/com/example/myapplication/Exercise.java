package com.example.myapplication;

public class Exercise {

        private String name;
        private String description;
        private int imageResource;

        // Constructor
        public Exercise(String name, String description, int imageResource) {
            this.name = name;
            this.description = description;
            this.imageResource = imageResource;
        }

        // Getters
        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public int getImageResource() {
            return imageResource;
        }


}
