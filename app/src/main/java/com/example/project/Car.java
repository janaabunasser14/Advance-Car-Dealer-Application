package com.example.project;

public class Car {
    private int id;
    private String name;
    private int number;
    private double rentalPrice;
    private String model;
    private String fuelType;
    private String transmission;
    private int mileage;

    // Constructor
    public Car(int id, String name, int number, double rentalPrice, String model, String fuelType, String transmission, int mileage) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.rentalPrice = rentalPrice;
        this.model = model;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.mileage = mileage;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public String getModel() {
        return model;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getTransmission() {
        return transmission;
    }

    public int getMileage() {
        return mileage;
    }

    // Add any other necessary methods
}

