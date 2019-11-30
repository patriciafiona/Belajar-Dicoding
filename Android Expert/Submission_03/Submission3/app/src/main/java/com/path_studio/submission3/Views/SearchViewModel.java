package com.path_studio.submission3.Views;

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
import com.path_studio.submission3.ErrorHandling;
import com.path_studio.submission3.Models.HomeItems;
import com.path_studio.submission3.Models.SearchItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends AndroidViewModel {
    private final String linkPoster = "http://image.tmdb.org/t/p/original";
    private static final String API_KEY = BuildConfig.API_KEY;
    private MutableLiveData<ArrayList<SearchItems>> listSearchResult = new MutableLiveData<>();
    private SearchItems searchItems = new SearchItems();

    private ArrayList<String> searchTitle = new ArrayList<>();
    private ArrayList<String> mediaType = new ArrayList<>();
    private ArrayList<Integer> result_id = new ArrayList<>();

    private Context mContext;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        this.mContext = application;
    }

    public void setResult(String language, String key){
        result_id.clear();
        searchTitle.clear();
        mediaType.clear();

        //Mengambil data dari API dengan volley
        final RequestQueue queue = Volley.newRequestQueue(mContext);

        final ArrayList<SearchItems> listItems = new ArrayList<>();
        final String url = "https://api.themoviedb.org/3/search/multi?api_key=" + API_KEY +
                "&language=" + language + "&query=" + key +"&page=1&include_adult=false";

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray data = response.getJSONArray("results");

                            for (int i = 0; i < data.length(); i++) {
                                try{
                                    JSONObject jsonObject = data.getJSONObject(i);
                                    if(!jsonObject.getString("media_type").equals("person")) {
                                        result_id.add(jsonObject.getInt("id"));

                                        if(jsonObject.getString("media_type").equals("movie"))
                                            searchTitle.add(jsonObject.getString("title"));
                                        else if(jsonObject.getString("media_type").equals("tv"))
                                            searchTitle.add(jsonObject.getString("original_name"));

                                        mediaType.add(jsonObject.getString("media_type"));
                                    }
                                }catch (JSONException e){
                                    Log.e("Error.GET.JSON", e.toString());
                                }
                            }
                            searchItems.setId(result_id);
                            searchItems.setSearchTitle(searchTitle);
                            searchItems.setMediaType(mediaType);

                            listItems.add(searchItems);
                            listSearchResult.postValue(listItems);

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

    public LiveData<ArrayList<SearchItems>> getSearchResult() {
        return listSearchResult;
    }

}
