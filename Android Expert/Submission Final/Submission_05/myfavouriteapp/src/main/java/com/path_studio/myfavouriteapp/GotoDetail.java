package com.path_studio.myfavouriteapp;

import android.content.Context;
import android.content.Intent;

import com.path_studio.myfavouriteapp.Activities.DetailMovieActivity;
import com.path_studio.myfavouriteapp.Activities.DetailTVActivity;

public class GotoDetail {

    public void go_to_movie(Context context, int id){
        Intent i = new Intent(context, DetailMovieActivity.class);
        i.putExtra("movie_id", id);
        context.startActivity(i);
    }

    public void go_to_tv(Context context, int id){
        Intent i = new Intent(context, DetailTVActivity.class);
        i.putExtra("tv_id", id);
        context.startActivity(i);
    }

}
