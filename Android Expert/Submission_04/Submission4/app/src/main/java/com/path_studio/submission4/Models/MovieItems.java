package com.path_studio.submission4.Models;

import java.util.ArrayList;

public class MovieItems {

    public MovieItems(){
        //constructor
    }

    private String backdrop;
    private String poster;
    private boolean adult;
    private int id;
    private String name;
    private String description;
    private double ratting;

    private String imdb_id;
    private String original_language;
    private String popularity;
    private String release_date;
    private String revenue;
    private String status;
    private int vote_count;

    private ArrayList<String> genre;


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
    public double getRatting() {
        return ratting;
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
    public ArrayList<String> getGenre() {
        return genre;
    }
    public String getBackdrop() {
        return backdrop;
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
    public void setRatting(double ratting) {
        this.ratting = ratting;
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
    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }
    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }
}
