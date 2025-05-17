package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class FoodLay extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<Food> foodList;
    private List<Food> filteredFoodList;
    private EditText searchFoodEditText;
    private ImageView searchIcon;
    private ImageView bake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        searchFoodEditText = findViewById(R.id.search_food);
        searchIcon = findViewById(R.id.search_icon);
        recyclerView = findViewById(R.id.recyclerViewFood);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        foodList = new ArrayList<>();
        filteredFoodList = new ArrayList<>();
        loadFoodItems();

        // Use filtered list for adapter
        filteredFoodList.addAll(foodList);
        foodAdapter = new FoodAdapter(filteredFoodList);
        recyclerView.setAdapter(foodAdapter);
        bake=findViewById(R.id.bac);
        bake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodLay.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener on search icon
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchFoodEditText.getText().toString().trim();
                filterFoodItems(query);
            }
        });
    }

    private void loadFoodItems() {
        foodList.add(new Food("Fried Egg", "Protein-rich breakfast", 90, "main"));
        foodList.add(new Food("Salad", "Fresh vegetable mix", 150, "side"));
        foodList.add(new Food("Steak", "Grilled beef steak", 600, "main"));
        foodList.add(new Food("Pasta", "Italian pasta with sauce", 350, "main"));
        foodList.add(new Food("Fruit Bowl", "Mixed fresh fruits", 200, "dessert"));
        foodList.add(new Food("Soup", "Warm and comforting", 250, "starter"));
        foodList.add(new Food("Pizza Slice", "Cheesy and delicious", 300, "main"));
        foodList.add(new Food("Chicken Breast", "Lean protein option", 200, "main"));
        foodList.add(new Food("Sandwich", "Quick and easy meal", 400, "main"));
        foodList.add(new Food("Ice Cream", "Sweet frozen dessert", 250, "dessert"));
        foodList.add(new Food("Smoothie", "Blended fruit drink", 180, "drink"));
        foodList.add(new Food("Yogurt Bowl", "Healthy probiotic snack", 120, "snack"));
        foodList.add(new Food("Grilled Vegetables", "Healthy side dish", 100, "side"));
        foodList.add(new Food("Rice Bowl", "Simple and filling", 220, "main"));
        foodList.add(new Food("Avocado Toast", "Trendy breakfast option", 280, "main"));

        // Add all items to filtered list initially
        filteredFoodList.addAll(foodList);
    }

    private void filterFoodItems(String query) {
        filteredFoodList.clear();
        for (Food food : foodList) {
            if (food.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredFoodList.add(food);
            }
        }
        foodAdapter.notifyDataSetChanged();
    }
}
