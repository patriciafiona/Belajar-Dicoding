package com.path_studio.submission3.ViewModels;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.path_studio.submission3.BuildConfig;
import com.path_studio.submission3.Models.HomeMovieItems;
import com.path_studio.submission3.Models.HomeTVItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class HomeViewModel extends AndroidViewModel {
    private final String linkPoster = "http://image.tmdb.org/t/p/original";
    private static final String API_KEY = BuildConfig.API_KEY;

    private MutableLiveData<ArrayList<HomeTVItems>> listTVDiscover = new MutableLiveData<>();
    private MutableLiveData<ArrayList<HomeMovieItems>> listMovieDiscover = new MutableLiveData<>();

    private ArrayList<String> discover_movie_poster = new ArrayList<>();
    private ArrayList<String> discover_tv_poster = new ArrayList<>();

    private HomeTVItems homeTVItems = new HomeTVItems();
    private HomeMovieItems homeMovieItems = new HomeMovieItems();

    private Context mContext;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.mContext = application;
    }


    public void setMovieDiscover(String language){
        //Mengambil data dari API dengan volley
        final RequestQueue queue = Volley.newRequestQueue(mContext);

        final ArrayList<HomeMovieItems> listItems = new ArrayList<>();
        final String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=" + language;

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        //now get your  json array like this
                        try {
                            JSONArray data = response.getJSONArray("results");

                            for (int i = 0; i < 5; i++) {
                                try{
                                    JSONObject jsonObject = data.getJSONObject(i);
                                    String tampung = linkPoster + jsonObject.getString("poster_path");
                                    discover_movie_poster.add(tampung);
                                }catch (JSONException e){
                                    Log.e("Error.GET.JSON", e.toString());
                                }
                            }
                            homeMovieItems.setDiscover_movie_poster(discover_movie_poster);
                            listItems.add(homeMovieItems);
                            listMovieDiscover.postValue(listItems);

                        } catch (JSONException e) {
                            Log.e("JSON Error", e.toString());
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error.Response", error.toString());
                    }
                });

        // add it to the RequestQueue
        queue.add(getRequest);
    }

    public void setTVDiscover(String language){
        //Mengambil data dari API dengan volley
        final RequestQueue queue = Volley.newRequestQueue(mContext);

        final ArrayList<HomeTVItems> listItems = new ArrayList<>();
        final String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=" + language;

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        //now get your  json array like this
                        try {
                            JSONArray data = response.getJSONArray("results");

                            for (int i = 0; i < 5; i++) {
                                try{
                                    JSONObject jsonObject = data.getJSONObject(i);
                                    String tampung = linkPoster + jsonObject.getString("poster_path");
                                    discover_tv_poster.add(tampung);
                                }catch (JSONException e){
                                    Log.e("Error.GET.JSON", e.toString());
                                }
                            }
                            homeTVItems.setDiscover_tv_poster(discover_tv_poster);
                            listItems.add(homeTVItems);
                            listTVDiscover.postValue(listItems);

                        } catch (JSONException e) {
                            Log.e("JSON Error", e.toString());
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error.Response", error.toString());
                    }
                });

        // add it to the RequestQueue
        queue.add(getRequest);
    }

    public LiveData<ArrayList<HomeTVItems>> getTVDiscover() {
        return listTVDiscover;
    }
    public LiveData<ArrayList<HomeMovieItems>> getMovieDiscover() {
        return listMovieDiscover;
    }

}
