package com.example.appdrone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.MaterialToolbar;

public class drone_activity extends BaseActivity {
    private TextView altitudeTextView;
    private TextView batteryTextView;
    private TextView directionTextView;
    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private TextView processLoadTextView;
    private TextView speedTextView;
    private TextView timestampTextView;
    private TextView search_modeTextView;
    private TextView speed_unitTextView;
    private TextView statusTextView;
    private TextView timebootTextView;
    private FirebaseManager mFirebaseManager;
    private DataViewModel mDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drone_activity);
        setupToolbar();
        // Find the toolbar


        // Initialize the TextViews
        altitudeTextView = findViewById(R.id.altitudeTextView);
        batteryTextView = findViewById(R.id.batteryTextView);
        directionTextView = findViewById(R.id.directionTextView);
        latitudeTextView = findViewById(R.id.latitudeTextView);
        longitudeTextView = findViewById(R.id.longitudeTextView);
        processLoadTextView = findViewById(R.id.processLoadTextView);
        speedTextView = findViewById(R.id.speedTextView);
        timestampTextView = findViewById(R.id.timebootTextView);
        search_modeTextView = findViewById(R.id.search_modeTextView);
        speed_unitTextView = findViewById(R.id.speed_unitTextView);
        statusTextView = findViewById(R.id.statusTextView);
        timebootTextView = findViewById(R.id.timebootTextView);

        // Create a DataViewModel instance
        mDataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        // Create a FirebaseManager instance
        mFirebaseManager = new FirebaseManager(mDataViewModel);

        // Observe the data
        mDataViewModel.getDroneData().observe(this, new Observer<DroneData>() {
            @Override
            public void onChanged(DroneData droneData) {
                if (droneData != null) {
                    updateUI(droneData);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drone_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            // Handle settings click
            return true;
        } else if (item.getItemId() == R.id.main_activity) {
            // Create an Intent to start MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            // Start the activity
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Add the listener
        mFirebaseManager.addFirebaseListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Remove the listener
        mFirebaseManager.removeFirebaseListener();
    }

    private void updateUI(DroneData droneData) {
        altitudeTextView.setText("Altitude: " + String.valueOf(droneData.getAltitude()));
        batteryTextView.setText("Battery: " + String.valueOf(droneData.getBattery()));
        directionTextView.setText("Direction: " + String.valueOf(droneData.getDirection()));
        latitudeTextView.setText("Latitude: " + String.valueOf(droneData.getLatitude()));
        longitudeTextView.setText("Longitude: " + String.valueOf(droneData.getLongitude()));
        processLoadTextView.setText("Process Load: " + String.valueOf(droneData.getProcess_load()));
        speedTextView.setText("Speed: " + String.valueOf(droneData.getSpeed()));
        timestampTextView.setText("Timestamp: " + String.valueOf(droneData.getTimestamp()));
        search_modeTextView.setText("Search Mode: " + droneData.getSearch_mode());
        speed_unitTextView.setText("Speed Unit: " + droneData.getSpeed_unit());
        statusTextView.setText("Status: " + droneData.getStatus());
        timebootTextView.setText("Timeboot: " + String.valueOf(droneData.getTimeboot()));
    }
}