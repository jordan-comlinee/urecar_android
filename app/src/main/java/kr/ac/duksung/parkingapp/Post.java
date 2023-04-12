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
    private int total_space;
    @SerializedName("available_space")
    private int available_space;

    @SerializedName("userid")
    private String userid;
    @SerializedName("password")
    private String password;

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
    public String getLocation(){
        return location;
    }
    public int getTotal_space(){
        return total_space;
    }
    public int getAvailable_space(){
        return available_space;
    }

    public String getUserid() { return userid; }
    public String getPassword() { return password; }

    public void setuserid(String userid){
        this.userid = userid;
    }

    public void setloginpw(String loginpw){
        this.password = password;
    }

    public void setPlotid(int plotid) { this.plotid = plotid; }
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }
    public void setPlotname(String plotname){ this.plotname = plotname;  }
    public void setLocation(String location){ this.location=location; }
    public void setTotal_space(int total_space){ this.total_space=total_space;}
    public void setAvailable_space(int available_space){  this.available_space=available_space; }
}