package com.example.appdrone;



public class DroneInfo {
    public DroneInfo() {
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.dronAutonomie = dronAutonomie;
        this.research_state = research_state;
    }

    private Double dronAutonomie = 10.0;
    private Double longitude = 0.0;
    private Double lattitude = 0.0;
    private Double vitesse = 10.0;
    private boolean research_state = false;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLattitude() {
        return lattitude;
    }

    public void setLattitude(Double lattitude) {
        this.lattitude = lattitude;
    }



    public Double getVitesse() {
        return vitesse;
    }

    public void setVitesse(Double vitesse) {
        this.vitesse = vitesse;
    }



    public Double getDronAutonomie() {
        return dronAutonomie;
    }

    public void setDronAutonomie(Double dronAutonomie) {
        this.dronAutonomie = dronAutonomie;
    }

    public boolean isResearch_state() {
        return research_state;
    }

    public void setResearch_state(boolean research_state) {
        this.research_state = research_state;
    }
}
