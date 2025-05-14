package com.example.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private List<Exercise> exerciseList;

    public ExerciseAdapter(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
        Log.d("Exercise Adapter", "Adapter created with " + exerciseList.size() + " exercises.");
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        Log.d("Exercise Adapter", "Binding: " + exercise.getName());
        holder.imgBtn.setOnClickListener(v -> {
            Log.d("Exercise Adapter", "Pressed Exercise: " + exercise.getName());
        });
        holder.exerciseName.setText(exercise.getName());
        holder.exerciseDescription.setText(exercise.getDescription());
        holder.exerciseImage.setImageResource(exercise.getImageResource());
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName, exerciseDescription;
        ImageView exerciseImage;
        ImageButton imgBtn;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBtn = itemView.findViewById(R.id.addexercise);
            exerciseName = itemView.findViewById(R.id.exercise_name);
            exerciseDescription = itemView.findViewById(R.id.exercise_description);
            exerciseImage = itemView.findViewById(R.id.exercise_image);
        }
    }
}
