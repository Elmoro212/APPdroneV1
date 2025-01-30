package com.example.appdrone;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DataViewModel extends ViewModel implements FirebaseManager.FirebaseDataListener {
    private MutableLiveData<DroneData> droneData = new MutableLiveData<>();

    // This is the getDroneData() method!
    public LiveData<DroneData> getDroneData() {
        return droneData;
    }

    @Override
    public void onDataChanged(DroneData newData) {
        droneData.setValue(newData);
    }

    @Override
    public void onDataError(String errorMessage) {
        Log.e("DataViewModel", "Firebase error: " + errorMessage);
    }
}