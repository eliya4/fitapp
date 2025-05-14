package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<Food> foodList;

    // Constructor לקבלת הרשימה
    public FoodAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.foodName.setText(food.getName());
        holder.foodDescription.setText(food.getDescription());
        holder.foodCalories.setText(food.getCalories() + " Calories");
        holder.foodImage.setImageResource(food.getImageResource());

        // הוספת מאזין לחצן הוספת אוכל
        holder.addFoodButton.setOnClickListener(v -> {
            // כאן אפשר להוסיף קוד להוספת האוכל לרשימה או לחשבון
            System.out.println("Added " + food.getName() + " to the meal plan");
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView foodName, foodDescription, foodCalories;
        ImageView foodImage;
        ImageButton addFoodButton;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name);
            foodDescription = itemView.findViewById(R.id.food_description);
            foodCalories = itemView.findViewById(R.id.food_calories);
            foodImage = itemView.findViewById(R.id.food_image);
            addFoodButton = itemView.findViewById(R.id.add_food);
        }
    }
}
