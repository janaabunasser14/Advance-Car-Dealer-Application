package com.example.project;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CarType implements Parcelable {
    private String type;
    private String id;

    public CarType() {

    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = String.valueOf(id);
    }

    // Parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.id);
    }

    protected CarType(Parcel in) {
        this.type = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<CarType> CREATOR = new Parcelable.Creator<CarType>() {
        @Override
        public CarType createFromParcel(Parcel source) {
            return new CarType(source);
        }

        @Override
        public CarType[] newArray(int size) {
            return new CarType[size];
        }
    };


    public String toString() {
        return "Student{" +
                "\nID= " + id +
                "\nname= " + type +
                +'\n'+'}'+'\n';
    }
}
