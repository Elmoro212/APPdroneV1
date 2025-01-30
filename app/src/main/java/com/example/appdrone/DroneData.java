package com.example.appdrone;

public class DroneData {
    private Double altitude;
    private Integer battery;
    private Double direction;
    private Double latitude;
    private Double longitude;
    private Double process_load;
    private Double speed;
    private Long timestamp;
    private String search_mode;
    private String speed_unit;
    private String status;
    private Long time_boot;

    // Required empty constructor for Firebase
    public DroneData() {
    }

    public DroneData(Double altitude, Integer battery, Double direction, Double latitude, Double longitude, Double process_load, Double speed, Long timestamp, String search_mode, String speed_unit, String status, Long timeboot) {
        this.altitude = altitude;
        this.battery = battery;
        this.direction = direction;
        this.latitude = latitude;
        this.longitude = longitude;
        this.process_load = process_load;
        this.speed = speed;
        this.timestamp = timestamp;
        this.search_mode = search_mode;
        this.speed_unit = speed_unit;
        this.status = status;
        this.time_boot = time_boot;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Integer getBattery() {
        return battery;
    }

    public void setBattery(Integer battery) {
        this.battery = battery;
    }

    public Double getDirection() {
        return direction;
    }

    public void setDirection(Double direction) {
        this.direction = direction;
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

    public Double getProcess_load() {
        return process_load;
    }

    public void setProcess_load(Double process_load) {
        this.process_load = process_load;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSearch_mode() {
        return search_mode;
    }

    public void setSearch_mode(String search_mode) {
        this.search_mode = search_mode;
    }

    public String getSpeed_unit() {
        return speed_unit;
    }

    public void setSpeed_unit(String speed_unit) {
        this.speed_unit = speed_unit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTimeboot() {
        return time_boot;
    }

    public void setTimeboot(Long timeboot) {
        this.time_boot = timeboot;
    }
}