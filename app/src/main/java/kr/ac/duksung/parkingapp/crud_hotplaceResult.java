package kr.ac.duksung.parkingapp;

public class crud_hotplaceResult {
    private int plotid;
    private String place_name;
    private double place_latitude;
    private double place_longitude;
    private String place_address;
    private String place_property;

    public void setplotId() {
        this.plotid = plotid;
    }


    public String getPlaceName() {
        return place_name;
    }

    public String getPlaceAddress() {
        return place_address;
    }

    public String getPlaceProperty() {
        return place_property;
    }

    public double getPlaceLatitude() {return place_latitude; }

    public double getPlaceLongitude() {return place_longitude; }


}
