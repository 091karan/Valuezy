package com.aakib78.hospiton;

public class StoreListModel {

    private String storeName;
    private String discount;
    private String address;
    private String latitude;
    private String longitude;


    public StoreListModel(String address, String discount, String latitude, String longitude, String storeName) {
        this.storeName = storeName;
        this.discount = discount;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public StoreListModel() {
    }

    String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
