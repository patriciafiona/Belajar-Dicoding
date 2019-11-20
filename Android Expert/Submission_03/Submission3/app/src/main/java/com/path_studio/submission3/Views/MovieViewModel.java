package com.path_studio.submission3.Views;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.path_studio.submission3.Models.MovieItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MovieViewModel extends ViewModel {

    private final String linkPoster = "http://image.tmdb.org/t/p/original";
    private static final String API_KEY = "59ade0f2f439410860ac45335e2e539d";
    private MutableLiveData<ArrayList<MovieItems>> listMovies = new MutableLiveData<>();

    public void setMovie(String language, Context mContext) {
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
                            Log.d("Response", response.toString());
                            JSONArray data = response.getJSONArray("results");

                            for (int i = 0; i < data.length(); i++) {
                                try{
                                    JSONObject jsonObject = data.getJSONObject(i);

                                    MovieItems movieItems = new MovieItems();
                                    movieItems.setId(jsonObject.getInt("id"));
                                    movieItems.setName(jsonObject.getString("title"));
                                    movieItems.setAdult(jsonObject.getBoolean("adult"));
                                    movieItems.setPoster(linkPoster + jsonObject.getString("poster_path"));
                                    movieItems.setDescription(jsonObject.getString("overview"));
                                    listItems.add(movieItems);

                                }catch (JSONException e){
                                    Log.d("Error.GET.JSON", e.toString());
                                }
                            }
                            listMovies.postValue(listItems);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                });

        // add it to the RequestQueue
        queue.add(getRequest);

    }

    public LiveData<ArrayList<MovieItems>> getMovies() {
        return listMovies;
    }

}
