package com.path_studio.submission3.Models;

public class MovieItems {

    public MovieItems(){
        //constructor
    }

    private String poster;
    private boolean adult;
    private int id;
    private String name;
    private String description;

    private String year;
    private int ratting;
    private String link_trailer;
    private String link_web;

    //setter and getter hasil generate
    public int getId(){ return id;}
    public boolean isAdult() {
        return adult;
    }
    public String getPoster() {
        return poster;
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

    public void setId(int id){ this.id = id;}
    public void setAdult(boolean adult) {
        this.adult = adult;
    }
    public void setPoster(String poster) {
        this.poster = poster;
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
}
