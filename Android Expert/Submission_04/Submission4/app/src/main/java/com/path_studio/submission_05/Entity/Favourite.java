package com.path_studio.Submission_05.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Favourite implements Parcelable {
    private String poster;
    private int id;
    private int data_id;
    private String type;
    private String title;
    private double ratting;

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRatting() {
        return ratting;
    }

    public void setRatting(double ratting) {
        this.ratting = ratting;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getData_id() {
        return data_id;
    }

    public void setData_id(int data_id) {
        this.data_id = data_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.data_id);
        dest.writeString(this.type);
        dest.writeString(this.title);
        dest.writeDouble(this.ratting);
        dest.writeString(this.poster);
    }

    //constructor
    public Favourite(int id, int data_id, String type, String title, String poster, double ratting) {
        this.id = id;
        this.type = type;
        this.data_id = data_id;
        this.title = title;
        this.poster = poster;
        this.ratting = ratting;
    }

    public Favourite() {}

    private Favourite(Parcel in) {
        this.id = in.readInt();
        this.data_id = in.readInt();
        this.type = in.readString();
        this.title = in.readString();
        this.ratting = in.readDouble();

        this.poster = in.readString();
    }
    public static final Parcelable.Creator<Favourite> CREATOR = new Parcelable.Creator<Favourite>() {
        @Override
        public Favourite createFromParcel(Parcel source) {
            return new Favourite(source);
        }
        @Override
        public Favourite[] newArray(int size) {
            return new Favourite[size];
        }
    };

}
