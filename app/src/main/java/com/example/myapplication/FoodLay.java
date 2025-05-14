package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class FoodLay extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        recyclerView = findViewById(R.id.recyclerViewFood);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        foodList = new ArrayList<>();
        loadFoodItems();

        foodAdapter = new FoodAdapter(foodList);
        recyclerView.setAdapter(foodAdapter);
    }

    private void loadFoodItems() {
        foodList.add(new Food("Fried Egg", "Protein-rich breakfast", 90, "main", R.drawable.fried_egg));
        foodList.add(new Food("Salad", "Fresh vegetable mix", 150, "side", R.drawable.salad));
        foodList.add(new Food("Steak", "Grilled beef steak", 600, "main", R.drawable.steak));
        foodList.add(new Food("Pasta", "Italian pasta with sauce", 350, "main", R.drawable.pasta));
        foodList.add(new Food("Fruit Bowl", "Mixed fresh fruits", 200, "dessert", R.drawable.fruit_bowl));
        foodList.add(new Food("Soup", "Warm and comforting", 250, "starter", R.drawable.soup));
        foodList.add(new Food("Pizza Slice", "Cheesy and delicious", 300, "main", R.drawable.pizza));
        foodList.add(new Food("Chicken Breast", "Lean protein option", 200, "main", R.drawable.chicken_breast));
        foodList.add(new Food("Sandwich", "Quick and easy meal", 400, "main", R.drawable.sandwich));
        foodList.add(new Food("Ice Cream", "Sweet frozen dessert", 250, "dessert", R.drawable.ice_cream));
        foodList.add(new Food("Smoothie", "Blended fruit drink", 180, "drink", R.drawable.smoothie));
        foodList.add(new Food("Yogurt Bowl", "Healthy probiotic snack", 120, "snack", R.drawable.yogurt_bowl));
        foodList.add(new Food("Grilled Vegetables", "Healthy side dish", 100, "side", R.drawable.grilled_vegetables));
        foodList.add(new Food("Rice Bowl", "Simple and filling", 220, "main", R.drawable.rice_bowl));
        foodList.add(new Food("Avocado Toast", "Trendy breakfast option", 280, "main", R.drawable.avocado_toast));
    }
}
