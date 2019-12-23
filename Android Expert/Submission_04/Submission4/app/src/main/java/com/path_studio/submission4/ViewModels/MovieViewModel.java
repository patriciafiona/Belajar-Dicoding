package com.path_studio.submission4.ViewModels;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.path_studio.submission4.BuildConfig;
import com.path_studio.submission4.Models.MovieItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MovieViewModel extends AndroidViewModel {

    private final String linkPoster = "http://image.tmdb.org/t/p/original";
    private static final String API_KEY = BuildConfig.API_KEY;
    private MutableLiveData<ArrayList<MovieItems>> listMovies = new MutableLiveData<>();
    private ArrayList<String> genre = new ArrayList<>();
    private Context mContext;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        this.mContext = application;
    }

    //for list movie
    public void setMovie(String language) {
        //Mengambil data dari API dengan volley
        final RequestQueue queue = Volley.newRequestQueue(mContext);

        final ArrayList<MovieItems> listItems = new ArrayList<>();
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

                            for (int i = 0; i < data.length(); i++) {
                                try{
                                    JSONObject jsonObject = data.getJSONObject(i);

                                    MovieItems movieItems = new MovieItems();
                                    movieItems.setId(jsonObject.getInt("id"));
                                    movieItems.setName(jsonObject.getString("title"));
                                    movieItems.setAdult(jsonObject.getBoolean("adult"));
                                    movieItems.setRatting(jsonObject.getDouble("vote_average"));
                                    movieItems.setPoster(linkPoster + jsonObject.getString("poster_path"));
                                    movieItems.setDescription(jsonObject.getString("overview"));
                                    listItems.add(movieItems);

                                }catch (JSONException e){
                                    Log.e("Error.GET.JSON", e.toString());
                                }
                            }
                            listMovies.postValue(listItems);

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

    //for detail movie page
    public void setMovie(String language, int movie_id) {
        //get detail data movie
        final RequestQueue queue = Volley.newRequestQueue(mContext);

        final ArrayList<MovieItems> listItems = new ArrayList<>();
        final String url = "https://api.themoviedb.org/3/movie/"+movie_id+"?api_key=" + API_KEY + "&language=" + language;
        final MovieItems movieItems = new MovieItems();
        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        //now get your  json array like this
                        try {
                            movieItems.setId(response.getInt("id"));

                            movieItems.setName(response.getString("title"));
                            movieItems.setRatting(response.getDouble("vote_average"));
                            movieItems.setPoster(linkPoster + response.getString("poster_path"));
                            movieItems.setDescription(response.getString("overview"));
                            movieItems.setBackdrop(linkPoster+response.getString("backdrop_path"));
                            movieItems.setImdb_id(response.getString("imdb_id"));
                            movieItems.setOriginal_language(response.getString("original_language"));
                            movieItems.setPopularity(response.getString("popularity"));
                            movieItems.setRelease_date(response.getString("release_date"));
                            movieItems.setRevenue(response.getString("revenue"));
                            movieItems.setStatus(response.getString("status"));
                            movieItems.setVote_count(response.getInt("vote_count"));
                            listItems.add(movieItems);

                            listMovies.postValue(listItems);

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

        setMovieArray(language, movie_id, movieItems);
    }

    public void setMovieArray(String language, int movie_id, final MovieItems movieItems){
        //get detail data movie
        final RequestQueue queue = Volley.newRequestQueue(mContext);

        final ArrayList<MovieItems> listItems = new ArrayList<>();
        final String url = "https://api.themoviedb.org/3/movie/"+movie_id+"?api_key=" + API_KEY + "&language=" + language;

        genre.clear();

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //untuk genre
                            JSONArray data = response.getJSONArray("genres");
                            for (int i = 0; i < data.length(); i++) {
                                try {
                                    JSONObject jsonObject = data.getJSONObject(i);
                                    genre.add( jsonObject.getString("name"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            movieItems.setGenre(genre);
                            listItems.add(movieItems);

                            listMovies.postValue(listItems);
                        }catch (JSONException e){
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

    public LiveData<ArrayList<MovieItems>> getMovies() {
        return listMovies;
    }

}
