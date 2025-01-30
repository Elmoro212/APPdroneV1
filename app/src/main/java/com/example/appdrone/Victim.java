package com.example.appdrone;

import android.os.Parcel;
import android.os.Parcelable;

public class Victim implements Parcelable {
    private Double latitude;
    private Double longitude;
    private Integer altitude;
    private String status;
    private Long time_of_discovery;
    private String id;
    private boolean découvert;

    public Victim() {
    }

    public Victim(Double latitude, Double longitude, Integer altitude, String status, Long time_of_discovery) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.status = status;
        this.time_of_discovery = time_of_discovery;
    }

    protected Victim(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        altitude = in.readInt();
        status = in.readString();
        time_of_discovery = in.readLong();

    }

    public static final Creator<Victim> CREATOR = new Creator<Victim>() {
        @Override
        public Victim createFromParcel(Parcel in) {
            return new Victim(in);
        }

        @Override
        public Victim[] newArray(int size) {
            return new Victim[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeInt(altitude);
        dest.writeString(status);
        dest.writeLong(time_of_discovery);
        // ... other code ...
    }
    public String getId() {
        return id;
    }

    public void setId(int id) {
        this.id = String.valueOf(id);
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTime_of_discovery() {
        return time_of_discovery;
    }

    public void setTime_of_discovery(Long time_of_discovery) {
        this.time_of_discovery = time_of_discovery;
    }

    public String toString(){
        return "Latitude: " + latitude + " Longitude: " + longitude + " Altitude: " + altitude + " Status: " + status + " Time of discovery: " + time_of_discovery;
    }
    public boolean isDecouvert() {
        return découvert;
    }

    public void setDecouvert(boolean decouverte) {
        this.découvert = decouverte;
    }
}