package com.example.project;

public class ListCar {
    private String typec, id, fuel, tran;
    private int mile, ingredients, desc, image;

    public ListCar(String name, String id, int ingredients, int desc, int image, String fuel, int mile, String tran) {
        this.typec = name;
        this.id = id;
        this.ingredients = ingredients;
        this.desc = desc;
        this.image = image;
        this.fuel = fuel;
        this.mile = mile;
        this.tran = tran;
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

    public int getIngredients() {
        return ingredients;
    }

    public void setIngredients(int ingredients) {
        this.ingredients = ingredients;
    }

    public int getDesc() {
        return desc;
    }

    public void setDesc(int desc) {
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public int getMile() {
        return mile;
    }

    public void setMile(int mile) {
        this.mile = mile;
    }

    public String getTran() {
        return tran;
    }

    public void setTran(String tran) {
        this.tran = tran;
    }
}

