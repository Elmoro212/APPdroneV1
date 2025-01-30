package com.example.appdrone;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseManager {
    private DatabaseReference mDatabase;
    private ValueEventListener mValueEventListener;
    private FirebaseDataListener mListener;



    public interface FirebaseDataListener {
        void onDataChanged(DroneData data);
        void onDataError(String errorMessage);
    }

    public FirebaseManager(FirebaseDataListener listener) {
        // Initialize Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mListener = listener;
    }

    public void addFirebaseListener() {
        // Get a reference to the data you want to read (now "DroneData")
        DatabaseReference dataRef = mDatabase.child("Drone");

        // Create a ValueEventListener
        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("FirebaseData", "onDataChange: " + dataSnapshot.toString());

                if (dataSnapshot.exists()) {
                    try {
                        // Extract the data from the snapshot
                        DroneData droneData = dataSnapshot.getValue(DroneData.class);

                        // Check if the value is not null
                        if (droneData != null) {
                            // Notify the listener
                            if (mListener != null) {
                                mListener.onDataChanged(droneData);
                            }
                        } else {
                            Log.w("FirebaseData", "droneData value is null");
                            if (mListener != null) {
                                mListener.onDataError("droneData value is null");
                            }
                        }
                    } catch (Exception e) {
                        Log.e("FirebaseData", "Error extracting data: " + e.getMessage());
                        if (mListener != null) {
                            mListener.onDataError("Error extracting data: " + e.getMessage());
                        }
                    }
                } else {
                    Log.w("FirebaseData", "No data found at the specified path.");
                    if (mListener != null) {
                        mListener.onDataError("No data found at the specified path.");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // This method is called when there is an error
                Log.e("FirebaseData", "onCancelled: " + databaseError.getMessage());
                if (mListener != null) {
                    mListener.onDataError("onCancelled: " + databaseError.getMessage());
                }
            }
        };

        // Add the listener to the dataRef
        dataRef.addValueEventListener(mValueEventListener);
    }

    public void removeFirebaseListener() {
        // Remove the listener if it's not null
        if (mValueEventListener != null) {
            mDatabase.removeEventListener(mValueEventListener);
            mValueEventListener = null; // Set to null to avoid memory leaks
        }
    }
}