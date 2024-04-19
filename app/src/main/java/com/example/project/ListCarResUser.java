package com.example.project;

import android.graphics.ColorSpace;

public class ListCarResUser {
    private String user, id, fuel, tran,model;
    private double cost;
    private String startDay, endDate;

    public ListCarResUser(String user, String id, String  model, String startDay, String endDate) {
        this.user = user;
        this.id = id;
        this.model = model;
        this.startDay = startDay;
        this.endDate = endDate;
    }

    public String getuser() {
        return user;
    }
    public String getModel(){
        return model;
    }

    public void setuser(String typec) {
        this.user = typec;
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
