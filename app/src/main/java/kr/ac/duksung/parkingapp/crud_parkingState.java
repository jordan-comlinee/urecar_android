package kr.ac.duksung.parkingapp;

public class crud_parkingState {
    private String time;
    private Float stats;
    private int plotid;

    public void sendPlotId() {
        this.plotid = plotid;
    }


    public String getTime() {
        return time;
    }

    public Float getStats() {
        return stats;
    }
}
