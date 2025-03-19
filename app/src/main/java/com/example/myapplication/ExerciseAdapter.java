package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private List<Exercise> exerciseList;

    // Constructor לקבלת הרשימה
    public ExerciseAdapter(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
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
        holder.exerciseName.setText(exercise.getName());
        holder.exerciseDescription.setText(exercise.getDescription());
        /*holder.exerciseImage.setImageResource(exercise.getImageResource());*/
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName, exerciseDescription;
        ImageView exerciseImage;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exercise_name);
            exerciseDescription = itemView.findViewById(R.id.exercise_description);
            exerciseImage = itemView.findViewById(R.id.exercise_image);
        }
    }
}
