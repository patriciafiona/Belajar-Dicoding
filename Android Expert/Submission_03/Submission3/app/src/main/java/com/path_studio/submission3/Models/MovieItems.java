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
    private double ratting;
    private String link_trailer;
    private String link_web;

    private String imdb_id;
    private String original_language;
    private String popularity;
    private String release_date;
    private String revenue;
    private String status;
    private int vote_count;


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
    public double getRatting() {
        return ratting;
    }
    public String getLink_trailer() {
        return link_trailer;
    }
    public String getLink_web() {
        return link_web;
    }
    public int getVote_count() {
        return vote_count;
    }
    public String getStatus() {
        return status;
    }
    public String getRevenue() {
        return revenue;
    }
    public String getRelease_date() {
        return release_date;
    }
    public String getPopularity() {
        return popularity;
    }
    public String getOriginal_language() {
        return original_language;
    }
    public String getImdb_id() {
        return imdb_id;
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
    public void setRatting(double ratting) {
        this.ratting = ratting;
    }
    public void setLink_trailer(String link_trailer) {
        this.link_trailer = link_trailer;
    }
    public void setLink_web(String link_web) {
        this.link_web = link_web;
    }
    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }
    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }
    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }
    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }
    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }
    public void setStatus(String status) {
        this.status = status;
    }


}
