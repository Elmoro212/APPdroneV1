package com.example.appdrone; // Replace with your package name

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends BaseActivity {

    static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private Button mapButton;
    private Button droneInfoButton;
    private Button victimButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showBackButton = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();

        // Find the buttons by their IDs
        mapButton = findViewById(R.id.mapButton);
        droneInfoButton = findViewById(R.id.droneInfoButton);
        victimButton = findViewById(R.id.victimListButton);

        // Set an onClickListener for the map button
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check location permission before starting MapActivity
                checkLocationPermission();
            }
        });
        // Set an onClickListener for the drone info button
        droneInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle drone info button click
                // Create an Intent to start the DroneInfoActivity
                Intent intent = new Intent(MainActivity.this, drone_activity.class);
                startActivity(intent);
            }
        });

        // Set an onClickListener for the victim button
        victimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle victim button click
                // Create an Intent to start the VictimActivity
                Intent intent = new Intent(MainActivity.this, VictimListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted, proceed to MapActivity
            startMapActivity();
        } else {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to MapActivity
                startMapActivity();
            } else {
                // Permission denied, handle accordingly (e.g., show a message)
                //Toast.makeText(this, "Location permission is required to open the map.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startMapActivity() {
        // Create an Intent to start the MapActivity
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        startActivity(intent);
    }
}