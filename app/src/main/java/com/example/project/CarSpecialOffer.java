package com.example.project;

public class CarSpecialOffer {
    private int carId;
    private String carName;
    private double priceBefore;
    private double priceAfter;

    // Constructor
    public CarSpecialOffer(int carId, String carName, double priceBefore, double priceAfter) {
        this.carId = carId;
        this.carName = carName;
        this.priceBefore = priceBefore;
        this.priceAfter = priceAfter;
    }

    // Getters and Setters
    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
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

    // Optional: Override toString method for easy printing of car details
    @Override
    public String toString() {
        return "CarSpecialOffer{" +
                "carId=" + carId +
                ", carName='" + carName + '\'' +
                ", priceBefore=" + priceBefore +
                ", priceAfter=" + priceAfter +
                '}';
    }
}
