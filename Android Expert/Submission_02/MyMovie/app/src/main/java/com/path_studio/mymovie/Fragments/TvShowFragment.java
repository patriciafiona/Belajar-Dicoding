package com.path_studio.mymovie.Fragments;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.path_studio.mymovie.Activities.DetailTVActivity;
import com.path_studio.mymovie.Adapters.ListTVAdapter;
import com.path_studio.mymovie.Models.Movie;
import com.path_studio.mymovie.R;

import java.util.ArrayList;

public class TvShowFragment extends Fragment {

    private RecyclerView rvTV;
    private ArrayList<Movie> list = new ArrayList<>();
    private long Index = 0;

    private ListTVAdapter listTVAdapter;

    private String[] juduls;
    private String[] descs;
    private String[] years;
    private String[] ratings;
    private String[] urls;

    private TypedArray posters;

    private static TvShowFragment instance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        posters = getResources().obtainTypedArray(R.array.data_poster_tv);

        //inisiasi list
        juduls = getResources().getStringArray(R.array.data_judul_tv);
        descs = getResources().getStringArray(R.array.data_desc_tv);
        years = getResources().getStringArray(R.array.data_year_tv);
        ratings = getResources().getStringArray(R.array.data_ratting_tv);
        urls = getResources().getStringArray(R.array.link_web_tv);

        instance = this;

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTV = getActivity().findViewById(R.id.rv_tvs);
        rvTV.setHasFixedSize(true);

        list.addAll(getListTV());
        showRecyclerList();
    }

    public static TvShowFragment getInstance() {
        return instance;
    }

    public ArrayList<Movie> getListTV() {
        String[] dataName = getResources().getStringArray(R.array.data_judul_tv);
        String[] dataDescription = getResources().getStringArray(R.array.data_desc_tv);
        ArrayList<Movie> listTV = new ArrayList<>();
        for (int i = 0; i < dataName.length; i++) {
            Movie movie = new Movie();
            movie.setName(dataName[i]);
            movie.setDescription(dataDescription[i]);
            movie.setPhoto_index(i);
            listTV.add(movie);
        }
        return listTV;
    }

    private void showRecyclerList(){
        rvTV.setLayoutManager(new LinearLayoutManager(getActivity()));
        listTVAdapter = new ListTVAdapter(list);
        rvTV.setAdapter(listTVAdapter);

        listTVAdapter.setOnItemClickCallback(new ListTVAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie data) {
                showSelectedTV(data);
            }
        });
    }

    public void go_to_detail(long tampung){
        //get Index-nya
        int IndexMovie = (int) tampung;
        Log.e("Item Selected in TV Fragment",String.valueOf(IndexMovie));


        //kirim datanya dengan PARCETABLE
        Movie movie = new Movie();
        movie.setName(juduls[IndexMovie]);
        movie.setYear(years[IndexMovie]);
        movie.setRatting(Integer.parseInt(ratings[IndexMovie]));
        movie.setDescription(descs[IndexMovie]);
        movie.setLink_web(urls[IndexMovie]);
        movie.setPhoto_index(IndexMovie); //nanti baca datanya di halaman detail

        //direct ke halaman detail mobil
        Intent i = new Intent(getActivity(), DetailTVActivity.class);
        i.putExtra(DetailTVActivity.EXTRA_TV, movie);
        startActivity(i);
    }

    private void showSelectedTV(Movie movie) {
        Toast.makeText(getActivity(), "Kamu memilih " + movie.getName(), Toast.LENGTH_SHORT).show();
    }

}
