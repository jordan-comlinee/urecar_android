package kr.ac.duksung.parkingapp;

public class crud_myPageResult {
    private int userId;
    private String username;
    private String carnum;
    private String phone;
    private String address;

    public void senduserId() {
        this.userId = userId;
    }


    public String getCarNum() {
        return carnum;
    }

    public String getUserName() {
        return username;
    }

    public String getPhoneNum() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

}
