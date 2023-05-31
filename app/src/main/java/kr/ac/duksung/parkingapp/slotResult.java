package kr.ac.duksung.parkingapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class slotResult {
    @SerializedName("plotid")
    @Expose
    private int plotid;
    @SerializedName("slotid")
    @Expose
    private String slotid;
    @SerializedName("available")
    @Expose
    private String available;

    public void sendPlotId() {
        this.plotid = plotid;
    }


    public String getSlotId() {
        return slotid;
    }

    public String getAvailable() {
        return available;
    }

}
