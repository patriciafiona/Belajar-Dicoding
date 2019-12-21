package com.path_studio.submission_05.Models;

import java.util.ArrayList;

public class SearchItems {

    private ArrayList<String> searchTitle = new ArrayList<>();
    private ArrayList<String> mediaType = new ArrayList<>();
    private ArrayList<Integer> id = new ArrayList<>();

    public ArrayList<String> getSearchTitle() {
        return searchTitle;
    }

    public void setSearchTitle(ArrayList<String> searchTitle) {
        this.searchTitle = searchTitle;
    }

    public ArrayList<String> getMediaType() {
        return mediaType;
    }

    public void setMediaType(ArrayList<String> mediaType) {
        this.mediaType = mediaType;
    }

    public java.util.ArrayList<Integer> get_result_Id() {
        return id;
    }

    public void setId(java.util.ArrayList<Integer> id) {
        this.id = id;
    }
}
