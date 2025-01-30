package com.example.appdrone;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import android.location.Location; // Import the Location class
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.appcompat.widget.Toolbar;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

   private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

   private GoogleMap map;
   private FusedLocationProviderClient fusedLocationClient;
   private LocationRequest locationRequest;
   private LocationCallback locationCallback;
   private Marker userMarker;
   private Marker droneMarker; // Add a drone marker
   // Firebase
   private FirebaseDatabase database;
   private DatabaseReference droneRef;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_map);

      Toolbar toolbar = findViewById(R.id.toolbar);

      // Set the toolbar as the action bar
      setSupportActionBar(toolbar);

      // Set the title of the toolbar
      getSupportActionBar().setTitle("Map Activity");

      // Enable the back button
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
      createLocationRequest();
      createLocationCallback();
      initializeMap();
      database = FirebaseDatabase.getInstance();
      droneRef = database.getReference("Drone");// Change "Drone" to your drone's name
      listenToDroneLocation(); // Start listening to drone location changes
   }

   private void initializeMap() {
      SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
              .findFragmentById(R.id.map);
      if (mapFragment != null) {
         mapFragment.getMapAsync(this);
      }
   }

   @Override
   public void onMapReady(@NonNull GoogleMap googleMap) {
      map = googleMap;
      // Configuration de la carte
      map.getUiSettings().setZoomControlsEnabled(true);
      map.getUiSettings().setCompassEnabled(true);
      checkLocationPermission();
   }

   private void checkLocationPermission() {
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
              != PackageManager.PERMISSION_GRANTED) {
         ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
      } else {
         enableMyLocation();
      }
   }

   private void enableMyLocation() {
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
         if (map != null) {
            try {
               map.setMyLocationEnabled(true);
               startLocationUpdates();
            } catch (SecurityException e) {
               Log.e("MapActivity", "Error enabling location", e);
            }
         }
      }
   }

   private void createLocationRequest() {
      locationRequest = LocationRequest.create();
      locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
      locationRequest.setInterval(10000);
      locationRequest.setFastestInterval(5000);
      locationRequest.setSmallestDisplacement(10f);
   }

   private void createLocationCallback() {
      locationCallback = new LocationCallback() {
         @Override
         public void onLocationResult(@NonNull LocationResult locationResult) {
            // Use android.location.Location here
            Location location = locationResult.getLastLocation();
            if (location != null) {
               LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
               updateUserMarker(userLocation);
               if (map != null) {
                  map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f));
               }
            }
         }
      };
   }

   private void startLocationUpdates() {
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
         fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
      }
   }

   private void updateUserMarker(LatLng userLocation) {
      if (map != null) {
         if (userMarker != null) {
            userMarker.remove();
         }
         userMarker = map.addMarker(new MarkerOptions()
                 .position(userLocation)
                 .title("My Location")
                 .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
      }
   }

   @Override
   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
      if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
         if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation();
         } else {
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
         }
      }
   }

   @Override
   protected void onPause() {
      super.onPause();
      if (fusedLocationClient != null) {
         fusedLocationClient.removeLocationUpdates(locationCallback);
      }
   }

   @Override
   protected void onResume() {
      super.onResume();
      startLocationUpdates();
   }

   // Add this method to listen for drone location changes
   private void listenToDroneLocation() {
      droneRef.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
               Double latitude = snapshot.child("latitude").getValue(Double.class);
               Double longitude = snapshot.child("longitude").getValue(Double.class);

               if (latitude != null && longitude != null) {
                  updateDroneMarker(new LatLng(latitude, longitude));
               }
            }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {
            Log.e("Firebase", "Error retrieving data", error.toException());
         }
      });
   }
   // Add this method to update the drone's marker
   private void updateDroneMarker(LatLng droneLocation) {
      runOnUiThread(() -> {
         if (map != null) {
            if (droneMarker != null) {
               droneMarker.remove();
            }
            droneMarker = map.addMarker(new MarkerOptions()
                    .position(droneLocation)
                    .title("Drone")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.drone)) // Replace with your drone icon
                    .anchor(0.5f, 0.5f)); // Center the icon
         }
      });
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
         case android.R.id.home:
            // Handle the back button click
            onBackPressed();
            return true;
         default:
            return super.onOptionsItemSelected(item);
      }
   }
}