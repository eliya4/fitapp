package com.example.myapplication;

import android.util.Log;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.annotation.NonNull;

public class FirebaseDatabaseService {
    private static final String TAG = "FirebaseDatabaseService";
    private static FirebaseDatabaseService instance;
    private final DatabaseReference databaseReference;
    private final FirebaseAuth firebaseAuth;

    private FirebaseDatabaseService() {
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            // Remove persistence as it might be causing issues
            databaseReference = database.getReference();
            firebaseAuth = FirebaseAuth.getInstance();
            
            // Test database connection
            databaseReference.child(".info/connected").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean connected = snapshot.getValue(Boolean.class);
                    Log.d(TAG, "Database connection state: " + (connected ? "connected" : "disconnected"));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "Database connection check failed: " + error.getMessage());
                }
            });
            
            Log.d(TAG, "FirebaseDatabaseService initialized successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error initializing FirebaseDatabaseService", e);
            throw e;
        }
    }

    public static FirebaseDatabaseService getInstance() {
        if (instance == null) {
            try {
                instance = new FirebaseDatabaseService();
            } catch (Exception e) {
                Log.e(TAG, "Error creating FirebaseDatabaseService instance", e);
                throw e;
            }
        }
        return instance;
    }

    // User Profile Operations
    public void saveUserProfile(Profile profile, DatabaseCallback callback) {
        try {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                String userId = user.getUid();
                Log.d(TAG, "Attempting to save profile for user: " + userId);
                
                // Get direct reference to user's profile and save immediately
                DatabaseReference userRef = databaseReference.child("users").child(userId);
                Log.d(TAG, "Database reference path: " + userRef.toString());

                userRef.setValue(profile)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "Profile saved successfully for user: " + userId);
                        callback.onSuccess("Profile saved successfully");
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Error saving profile for user: " + userId, e);
                        callback.onFailure(e.getMessage());
                    })
                    .addOnCompleteListener(task -> {
                        Log.d(TAG, "Save profile operation completed. Success: " + task.isSuccessful());
                        if (!task.isSuccessful() && task.getException() != null) {
                            Log.e(TAG, "Error details: ", task.getException());
                        }
                    });
            } else {
                String error = "Cannot save profile - no authenticated user";
                Log.e(TAG, error);
                callback.onFailure(error);
            }
        } catch (Exception e) {
            String error = "Unexpected error while saving profile";
            Log.e(TAG, error, e);
            callback.onFailure(error + ": " + e.getMessage());
        }
    }

    public void getUserProfile(DatabaseCallback callback) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            Log.d(TAG, "Fetching profile for user: " + userId);
            
            databaseReference.child("users").child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Profile profile = dataSnapshot.getValue(Profile.class);
                    if (profile != null) {
                        Log.d(TAG, "Profile retrieved successfully for user: " + userId);
                        callback.onSuccess(profile);
                    } else {
                        Log.d(TAG, "No profile found for user: " + userId);
                        callback.onFailure("Profile not found");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "Error fetching profile: " + databaseError.getMessage());
                    callback.onFailure(databaseError.getMessage());
                }
            });
        } else {
            Log.e(TAG, "Cannot fetch profile - no authenticated user");
            callback.onFailure("No authenticated user found");
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