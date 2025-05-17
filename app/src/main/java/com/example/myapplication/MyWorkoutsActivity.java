package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MyWorkoutsActivity extends AppCompatActivity {

    private RecyclerView workoutsRecyclerView;
    private WorkoutAdapter workoutAdapter;
    private List<String> workoutList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_workouts);
         ImageView retbtn=findViewById(R.id.returnBtn);
         retbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent;
                 intent = new Intent(MyWorkoutsActivity.this, MainActivity.class);
                 startActivity(intent);
             }
         });
        workoutsRecyclerView = findViewById(R.id.recyclerViewWorkouts);
        workoutsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        workoutAdapter = new WorkoutAdapter(workoutList, workoutName -> {
            Intent intent = new Intent(MyWorkoutsActivity.this, WorkoutDetailsActivity.class);
            intent.putExtra("workout_name", workoutName);
            startActivity(intent);

        });
        workoutsRecyclerView.setAdapter(workoutAdapter);

        loadUserWorkouts();

    }



    private void loadUserWorkouts() {
        String userId = getUserId();
        if (userId == null) {
            Toast.makeText(this, "משתמש לא מחובר", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference userRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(userId)
                .child("workouts");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                workoutList.clear();
                for (DataSnapshot workoutSnapshot : snapshot.getChildren()) {
                    String workoutName = workoutSnapshot.getKey();
                    if (workoutName != null) {
                        workoutList.add(workoutName);
                    }
                }
                workoutAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyWorkoutsActivity.this, "שגיאה בטעינת האימונים", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String getUserId() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUid(); // מזהה ייחודי לכל משתמש
        } else {
            Toast.makeText(this, "משתמש לא מחובר", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
