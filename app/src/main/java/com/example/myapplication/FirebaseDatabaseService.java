package com.example.myapplication;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseDatabaseService {
    private static FirebaseDatabaseService instance;
    private final DatabaseReference databaseReference;
    private final FirebaseAuth firebaseAuth;

    private FirebaseDatabaseService() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public static FirebaseDatabaseService getInstance() {
        if (instance == null) {
            instance = new FirebaseDatabaseService();
        }
        return instance;
    }

    // User Profile Operations
    public void saveUserProfile(Profile profile, DatabaseCallback callback) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            databaseReference.child("users").child(userId).setValue(profile)
                    .addOnSuccessListener(aVoid -> callback.onSuccess("Profile saved successfully"))
                    .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
        }
    }

    public void getUserProfile(DatabaseCallback callback) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            databaseReference.child("users").child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Profile profile = dataSnapshot.getValue(Profile.class);
                    if (profile != null) {
                        callback.onSuccess(profile);
                    } else {
                        callback.onFailure("Profile not found");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callback.onFailure(databaseError.getMessage());
                }
            });
        }
    }

    // Exercise Operations
    public void saveExercise(Exercise exercise, DatabaseCallback callback) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            String exerciseId = databaseReference.child("exercises").child(userId).push().getKey();
            if (exerciseId != null) {
                databaseReference.child("exercises").child(userId).child(exerciseId).setValue(exercise)
                        .addOnSuccessListener(aVoid -> callback.onSuccess("Exercise saved successfully"))
                        .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
            }
        }
    }

    public void getExercises(DatabaseCallback callback) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            databaseReference.child("exercises").child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    callback.onSuccess(dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callback.onFailure(databaseError.getMessage());
                }
            });
        }
    }

    // Food Operations
    public void saveFood(Food food, DatabaseCallback callback) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            String foodId = databaseReference.child("foods").child(userId).push().getKey();
            if (foodId != null) {
                databaseReference.child("foods").child(userId).child(foodId).setValue(food)
                        .addOnSuccessListener(aVoid -> callback.onSuccess("Food saved successfully"))
                        .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
            }
        }
    }

    public void getFoods(DatabaseCallback callback) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            databaseReference.child("foods").child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    callback.onSuccess(dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callback.onFailure(databaseError.getMessage());
                }
            });
        }
    }

    // Callback interface for database operations
    public interface DatabaseCallback {
        void onSuccess(Object result);
        void onFailure(String error);
    }
} 