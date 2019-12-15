package com.path_studio.submission4.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Favourite implements Parcelable {
    private String backdrop;
    private String poster;
    private int id;
    private int type;  /* 1 = Movie; 2 = TV Show*/
    private String title;
    private String description;
    private double ratting;

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRatting() {
        return ratting;
    }

    public void setRatting(double ratting) {
        this.ratting = ratting;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeDouble(this.ratting);
        dest.writeString(this.poster);
        dest.writeString(this.backdrop);
    }

    //constructor
    public Favourite(int id, String title, String description, String poster, double ratting) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.poster = poster;
        this.ratting = ratting;
    }

    private Favourite(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.ratting = in.readDouble();

        this.poster = in.readString();
        this.backdrop = in.readString();
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
