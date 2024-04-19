package com.example.project;

public class Rental {
    private int rentalId;
    private String userEmailRental;
    private String carIdRental;
    private String rentalStartDate;
    private String rentalEndDate;
    private double paymentAmount;

    public Rental() {
        // Default constructor
    }

    public Rental(int rentalId, String userEmailRental, String carIdRental, String rentalStartDate,
                  String rentalEndDate, double paymentAmount) {
        this.rentalId = rentalId;
        this.userEmailRental = userEmailRental;
        this.carIdRental = carIdRental;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
        this.paymentAmount = paymentAmount;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public String getUserEmailRental() {
        return userEmailRental;
    }

    public void setUserEmailRental(String userEmailRental) {
        this.userEmailRental = userEmailRental;
    }

    public String getCarIdRental() {
        return carIdRental;
    }

    public void setCarIdRental(String carIdRental) {
        this.carIdRental = carIdRental;
    }

    public String getRentalStartDate() {
        return rentalStartDate;
    }

    public void setRentalStartDate(String rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public String getRentalEndDate() {
        return rentalEndDate;
    }

    public void setRentalEndDate(String rentalEndDate) {
        this.rentalEndDate = rentalEndDate;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
