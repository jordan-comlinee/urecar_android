package kr.ac.duksung.parkingapp;

import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("plotid")
    private int plotid;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitude;

    public int getPlotid() {
        return plotid;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public void setPlotid(int plotid) {
        this.plotid = plotid;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }
}