package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    private List<String> workoutNames;
    private OnWorkoutClickListener listener;

    public WorkoutAdapter(List<String> workoutNames, OnWorkoutClickListener listener) {
        this.workoutNames = workoutNames;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        String workoutName = workoutNames.get(position);
        holder.workoutNameTextView.setText(workoutName);
        holder.itemView.setOnClickListener(v -> listener.onWorkoutClick(workoutName));
    }

    @Override
    public int getItemCount() {
        return workoutNames.size();
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        TextView workoutNameTextView;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutNameTextView = itemView.findViewById(R.id.workout_name);
        }
    }

    public interface OnWorkoutClickListener {
        void onWorkoutClick(String workoutName);
    }
}
