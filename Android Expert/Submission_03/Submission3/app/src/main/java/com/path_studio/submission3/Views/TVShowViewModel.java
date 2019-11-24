package com.path_studio.submission3.Views;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.path_studio.submission3.Models.TVItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TVShowViewModel extends ViewModel {
    private final String linkPoster = "http://image.tmdb.org/t/p/original";
    private static final String API_KEY = "59ade0f2f439410860ac45335e2e539d";
    private MutableLiveData<ArrayList<TVItems>> listTVShow = new MutableLiveData<>();
    private ArrayList<String> genre = new ArrayList<>();
    private ArrayList<String>created_by = new ArrayList<>();
    private ArrayList<String> networks = new ArrayList<>();

    //seasson detail
    private ArrayList<String> seasson_name = new ArrayList<>();
    private ArrayList<String> seasson_poster = new ArrayList<>();
    private ArrayList<String> seasson_overview = new ArrayList<>();
    private ArrayList<Integer> seasson_number = new ArrayList<>();
    private ArrayList<String> seasson_airDate = new ArrayList<>();
    private ArrayList<Integer> seasson_episodeCount = new ArrayList<>();

    public void setTVShow(String language, Context mContext){
        //Mengambil data dari API dengan volley
        final RequestQueue queue = Volley.newRequestQueue(mContext);

        final ArrayList<TVItems> listItems = new ArrayList<>();
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

                            for (int i = 0; i < data.length(); i++) {
                                try{
                                    JSONObject jsonObject = data.getJSONObject(i);

                                    TVItems tvItems = new TVItems();
                                    tvItems.setId_TV(jsonObject.getInt("id"));
                                    tvItems.setTitle(jsonObject.getString("original_name"));
                                    tvItems.setRatting(jsonObject.getDouble("vote_average"));
                                    tvItems.setPoster(linkPoster + jsonObject.getString("poster_path"));
                                    tvItems.setDescription(jsonObject.getString("overview"));
                                    listItems.add(tvItems);

                                }catch (JSONException e){
                                    Log.e("Error.GET.JSON", e.toString());
                                }
                            }
                            listTVShow.postValue(listItems);

                        } catch (JSONException e) {
                            e.printStackTrace();
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

    public void setTVShow(String language, int show_id, Context mContext){
        //get detail data movie
        final RequestQueue queue = Volley.newRequestQueue(mContext);

        final ArrayList<TVItems> listItems = new ArrayList<>();
        final String url = "https://api.themoviedb.org/3/tv/"+show_id+"?api_key=" + API_KEY + "&language=" + language;
        final TVItems tvItems = new TVItems();
        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        //now get your  json array like this
                        try {
                            tvItems.setId_TV(response.getInt("id"));
                            tvItems.setTitle(response.getString("name"));
                            tvItems.setRatting(response.getDouble("vote_average"));
                            tvItems.setPoster(linkPoster + response.getString("poster_path"));
                            tvItems.setDescription(response.getString("overview"));
                            tvItems.setBackdrop(linkPoster+response.getString("backdrop_path"));
                            tvItems.setLanguage(response.getString("languages"));
                            tvItems.setPopularity(response.getString("popularity"));
                            tvItems.setFirst_air_date(response.getString("first_air_date"));
                            tvItems.setLast_air_date(response.getString("last_air_date"));
                            tvItems.setProduction_status(response.getString("status"));
                            tvItems.setLink_homepage(response.getString("homepage"));
                            tvItems.setVote_count(response.getInt("vote_count"));
                            tvItems.setOrigial_language(response.getString("original_language"));
                            listItems.add(tvItems);

                            listTVShow.postValue(listItems);

                        } catch (JSONException e) {
                            e.printStackTrace();
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
        //set genre
        setArray(language, show_id, mContext, tvItems);
        setSeassonDetail(language, show_id, mContext, tvItems);

        // add it to the RequestQueue
        queue.add(getRequest);
    }

    public void setArray(String language, int show_id, Context mContext, final TVItems tvItems){
        //get detail data movie
        final RequestQueue queue = Volley.newRequestQueue(mContext);

        final ArrayList<TVItems> listItems = new ArrayList<>();
        final String url = "https://api.themoviedb.org/3/tv/"+show_id+"?api_key=" + API_KEY + "&language=" + language;

        genre.clear();
        created_by.clear();
        networks.clear();

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("genres");
                            for (int i = 0; i < data.length(); i++) {
                                try {
                                    JSONObject jsonObject = data.getJSONObject(i);
                                    genre.add( jsonObject.getString("name"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            tvItems.setGenre(genre);

                            //untuk created by created_by
                            JSONArray data_2 = response.getJSONArray("created_by");
                            for (int i = 0; i < data_2.length(); i++) {
                                try {
                                    JSONObject jsonObject = data_2.getJSONObject(i);
                                    created_by.add( jsonObject.getString("name"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            tvItems.setCreated_by(created_by);

                            //untuk networks
                            JSONArray data_3 = response.getJSONArray("networks");
                            for (int i = 0; i < data_3.length(); i++) {
                                try {
                                    JSONObject jsonObject = data_3.getJSONObject(i);
                                    networks.add( jsonObject.getString("name"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            tvItems.setNetwork(networks);

                            listItems.add(tvItems);
                            listTVShow.postValue(listItems);
                        }catch (JSONException e){
                            e.printStackTrace();
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

    public void setSeassonDetail(String language, int show_id, Context mContext, final TVItems tvItems){
        final RequestQueue queue = Volley.newRequestQueue(mContext);

        final ArrayList<TVItems> listItems = new ArrayList<>();
        final String url = "https://api.themoviedb.org/3/tv/"+show_id+"?api_key=" + API_KEY + "&language=" + language;

        seasson_name.clear();
        seasson_poster.clear();
        seasson_overview.clear();
        seasson_number.clear();
        seasson_airDate.clear();
        seasson_episodeCount.clear();

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("seasons");
                            for (int i = 0; i < data.length(); i++) {
                                try {
                                    JSONObject jsonObject = data.getJSONObject(i);
                                    seasson_name.add( jsonObject.getString("name"));
                                    seasson_poster.add( linkPoster + jsonObject.getString("poster_path"));
                                    seasson_overview.add( jsonObject.getString("overview"));
                                    seasson_number.add( jsonObject.getInt("season_number"));
                                    seasson_airDate.add( jsonObject.getString("air_date"));
                                    seasson_episodeCount.add( jsonObject.getInt("episode_count"));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            tvItems.setSeasson_airDate(seasson_airDate);
                            tvItems.setSeasson_poster(seasson_poster);
                            tvItems.setSeasson_overview(seasson_overview);
                            tvItems.setSeasson_name(seasson_name);
                            tvItems.setSeasson_number(seasson_number);
                            tvItems.setSeasson_episodeCount(seasson_episodeCount);

                            listItems.add(tvItems);
                            listTVShow.postValue(listItems);
                        }catch (JSONException e){
                            e.printStackTrace();
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

    public LiveData<ArrayList<TVItems>> getTVShow() {
        return listTVShow;
    }

}
