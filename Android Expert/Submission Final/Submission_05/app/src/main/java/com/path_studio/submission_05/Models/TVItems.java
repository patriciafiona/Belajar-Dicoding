package com.path_studio.submission_05.Models;

import java.util.ArrayList;

public class TVItems {
    private int id_TV;
    private String poster;
    private String title;
    private String description;
    private double ratting;

    private int vote_count;
    private String backdrop;
    private String language;
    private String popularity;
    private String first_air_date;
    private String last_air_date;
    private String production_status;
    private String link_homepage;
    private String origial_language;

    private ArrayList<String> created_by;
    private ArrayList<String> genre;
    private ArrayList<String> network;

    //seasson detail
    private ArrayList<String> seasson_name;
    private ArrayList<String> seasson_poster;
    private ArrayList<String> seasson_overview;
    private ArrayList<Integer> seasson_number;
    private ArrayList<String> seasson_airDate;
    private ArrayList<Integer> seasson_episodeCount;

    private String sTitle;
    private String sPoster;
    private String sOverview;
    private int sNumber;
    private String sAirDate;
    private int sEpsCount;


    public int getId_TV() {
        return id_TV;
    }

    public void setId_TV(int id) {
        this.id_TV = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
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

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public void setLast_air_date(String last_air_date) {
        this.last_air_date = last_air_date;
    }

    public String getProduction_status() {
        return production_status;
    }

    public void setProduction_status(String production_status) {
        this.production_status = production_status;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }

    public ArrayList<String> getCreated_by() {
        return created_by;
    }

    public void setCreated_by(ArrayList<String> created_by) {
        this.created_by = created_by;
    }

    public String getLink_homepage() {
        return link_homepage;
    }

    public void setLink_homepage(String link_homepage) {
        this.link_homepage = link_homepage;
    }

    public String getOrigial_language() {
        return origial_language;
    }

    public void setOrigial_language(String origial_language) {
        this.origial_language = origial_language;
    }

    public ArrayList<String> getNetwork() {
        return network;
    }

    public void setNetwork(ArrayList<String> network) {
        this.network = network;
    }

    public ArrayList<String> getSeasson_name() {
        return seasson_name;
    }

    public void setSeasson_name(ArrayList<String> seasson_name) {
        this.seasson_name = seasson_name;
    }

    public ArrayList<String> getSeasson_poster() {
        return seasson_poster;
    }

    public void setSeasson_poster(ArrayList<String> seasson_poster) {
        this.seasson_poster = seasson_poster;
    }

    public ArrayList<String> getSeasson_overview() {
        return seasson_overview;
    }

    public void setSeasson_overview(ArrayList<String> seasson_overview) {
        this.seasson_overview = seasson_overview;
    }

    public ArrayList<Integer> getSeasson_number() {
        return seasson_number;
    }

    public void setSeasson_number(ArrayList<Integer> seasson_number) {
        this.seasson_number = seasson_number;
    }

    public ArrayList<String> getSeasson_airDate() {
        return seasson_airDate;
    }

    public void setSeasson_airDate(ArrayList<String> seasson_airDate) {
        this.seasson_airDate = seasson_airDate;
    }

    public ArrayList<Integer> getSeasson_episodeCount() {
        return seasson_episodeCount;
    }

    public void setSeasson_episodeCount(ArrayList<Integer> seasson_episodeCount) {
        this.seasson_episodeCount = seasson_episodeCount;
    }

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public String getsPoster() {
        return sPoster;
    }

    public void setsPoster(String sPoster) {
        this.sPoster = sPoster;
    }

    public String getsOverview() {
        return sOverview;
    }

    public void setsOverview(String sOverview) {
        this.sOverview = sOverview;
    }

    public int getsNumber() {
        return sNumber;
    }

    public void setsNumber(int sNumber) {
        this.sNumber = sNumber;
    }

    public String getsAirDate() {
        return sAirDate;
    }

    public void setsAirDate(String sAirDate) {
        this.sAirDate = sAirDate;
    }

    public int getsEpsCount() {
        return sEpsCount;
    }

    public void setsEpsCount(int sEpsCount) {
        this.sEpsCount = sEpsCount;
    }
}
