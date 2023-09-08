package kr.ac.duksung.parkingapp;

import com.google.gson.annotations.SerializedName;

public class crud_Post {
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
    @SerializedName("place_name")
    private String place_name;
    @SerializedName("place_address")
    private String place_address;
    @SerializedName("place_latitude")
    private double place_latitude;
    @SerializedName("place_longitude")
    private double place_longitude;
    @SerializedName("place_property")
    private String place_property;
    @SerializedName("img_path")
    private String img_path;

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
    public String getImg_Path(){
        return img_path;
    }
    public String getUserid(){
        return userid;
    }
    public String getPassword(){
        return password;
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

    public void setPlotname(String plotname){ this.plotname = plotname;  }
    public void setLocation(String location){ this.location=location; }
    public void setTotal_space(int total_space){ this.total_space=total_space;}
    public void setAvailable_space(int available_space){  this.available_space=available_space; }
    public void setUserid(String userid){ this.userid = userid;  }
    public void setPassword(String password){ this.password=password; }

    public crud_Post(String userid, String password){
        this.userid = userid;
        this.password=password;
    }

    public double getPlaceLatitude() {
        return place_latitude;
    }

    public double getPlaceLongitude() {
        return place_longitude;
    }

    public String getPlaceAddress() {
        return place_address;
    }

    public String getPlaceName() {
        return place_name;
    }
    public String getPlaceProperty(){
        return place_property;
    }
}