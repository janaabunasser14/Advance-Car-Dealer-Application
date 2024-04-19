package com.example.project;

public class ListCarSp {
    private int specialOfferId;
    private String carId;
    private double priceBefore;
    private double priceAfter;
    private String typec;
    public ListCarSp(String typec, int specialOfferId, String carId, double priceBefore, double priceAfter) {
        this.specialOfferId = specialOfferId;
        this.carId = carId;
        this.priceBefore = priceBefore;
        this.priceAfter = priceAfter;
        this.typec = typec;
    }
    public String getTypec() {
        return typec;
    }
    public int getSpecialOfferId() {
        return specialOfferId;
    }

    public void setSpecialOfferId(int specialOfferId) {
        this.specialOfferId = specialOfferId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public double getPriceBefore() {
        return priceBefore;
    }

    public void setPriceBefore(double priceBefore) {
        this.priceBefore = priceBefore;
    }

    public double getPriceAfter() {
        return priceAfter;
    }

    public void setPriceAfter(double priceAfter) {
        this.priceAfter = priceAfter;
    }
}
