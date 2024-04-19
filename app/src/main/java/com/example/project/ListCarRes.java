package com.example.project;

import java.util.Date; // Import Date class for handling dates

public class ListCarRes {
    private String typec, id, fuel, tran;
    private double cost;
    private String startDay, endDate;

    public ListCarRes(String typec, String id, double cost, String startDay, String endDate) {
        this.typec = typec;
        this.id = id;
        this.cost = cost;
        this.startDay = startDay;
        this.endDate = endDate;
    }

    public String getTypec() {
        return typec;
    }

    public void setTypec(String typec) {
        this.typec = typec;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
