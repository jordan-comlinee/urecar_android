package kr.ac.duksung.parkingapp;

import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("plotid")
    private int plotid;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitude;

    @SerializedName("plotname")
    private String plotname;

    @SerializedName("location")
    private String location;
    @SerializedName("total_space")
    private String total_space;
    @SerializedName("available_space")
    private String available_space;

    public int getPlotid() {
        return plotid;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }
    public String getPlotname(){
        return plotname;
    }
    public String getTotal_space(){
        return total_space;
    }
    public String getAvailable_space(){
        return available_space;
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