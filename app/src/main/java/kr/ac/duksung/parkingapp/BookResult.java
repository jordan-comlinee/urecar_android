package kr.ac.duksung.parkingapp;

public class BookResult {
    private int plotid;
    private String slotid;
    private String userid;
    private String carnum;
    private int usagetime;
    private String parking_lot_name;
    private String parking_lot_location;

    public void sendUserid() {
        this.userid = userid;
    }
    public void sendPlotid() {
        this.plotid = plotid;
    }
    public void sendCarnum() {
        this.carnum = carnum;
    }
    public void sendUsagetime() {
        this.usagetime = usagetime;
    }

    public String getParking_lot_name() {
        return parking_lot_name;
    }
    public String getParking_lot_location() {
        return parking_lot_location;
    }
    public String getSlotid() {
        return slotid;
    }
    public int getUsagetime() {
        return usagetime;
    }
}
