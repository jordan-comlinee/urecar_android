package kr.ac.duksung.parkingapp;

public class slotResult {
    private int plotId;
    private String slotId;
    private String available;

    public void sendPlotId() {
        this.plotId = plotId;
    }


    public String getSlotId() {
        return slotId;
    }

    public String getAvailable() {
        return available;
    }

}
