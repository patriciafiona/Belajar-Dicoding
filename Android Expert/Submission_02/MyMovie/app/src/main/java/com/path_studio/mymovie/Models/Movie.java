package com.path_studio.mymovie.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private int photo_index;
    private int photo;
    private String name;
    private String description;

    private String year;
    private int ratting;
    private String link_trailer;
    private String link_web;

    //setter and getter hasil generate
    public int getPhoto_index() {
        return photo_index;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getYear() {
        return year;
    }
    public int getRatting() {
        return ratting;
    }
    public String getLink_trailer() {
        return link_trailer;
    }
    public String getLink_web() {
        return link_web;
    }

    public void setPhoto_index(int photo_index) {
        this.photo_index = photo_index;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public void setRatting(int ratting) {
        this.ratting = ratting;
    }
    public void setLink_trailer(String link_trailer) {
        this.link_trailer = link_trailer;
    }
    public void setLink_web(String link_web) {
        this.link_web = link_web;
    }

    public Movie(){
        //constructor
    }

    protected Movie(Parcel in) {
        name = in.readString();
        description = in.readString();
        year = in.readString();
        ratting = in.readInt();
        link_trailer = in.readString();
        link_web = in.readString();
        photo_index= in.readInt();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(year);
        dest.writeInt(ratting);
        dest.writeString(link_trailer);
        dest.writeString(link_web);
        dest.writeInt(photo_index);
    }
    @Override
    public int describeContents() {
        return 0;
    }
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
