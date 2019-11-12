package com.path_studio.mymovie.Fragments;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.path_studio.mymovie.Adapters.ListMovieAdapter;
import com.path_studio.mymovie.Models.Movie;
import com.path_studio.mymovie.R;

import java.util.ArrayList;

public class MovieFragment extends Fragment {

    private RecyclerView rvMovies;
    private ArrayList<Movie> list = new ArrayList<>();

    public TypedArray posters;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        posters = getResources().obtainTypedArray(R.array.data_poster_movie);

        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovies = getActivity().findViewById(R.id.rv_heroes);
        rvMovies.setHasFixedSize(true);

        list.addAll(getListMovie());
        showRecyclerList();

    }

    public ArrayList<Movie> getListMovie() {
        String[] dataName = getResources().getStringArray(R.array.data_judul_movie);
        String[] dataDescription = getResources().getStringArray(R.array.data_desc_movie);
        ArrayList<Movie> listHero = new ArrayList<>();
        for (int i = 0; i < dataName.length; i++) {
            Movie movie = new Movie();
            movie.setName(dataName[i]);
            movie.setDescription(dataDescription[i]);
            movie.setPhoto_index(i);
            listHero.add(movie);
        }
        return listHero;
    }

    private void showRecyclerList(){
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        ListMovieAdapter listMovieAdapter = new ListMovieAdapter(list);
        rvMovies.setAdapter(listMovieAdapter);
    }

    public TypedArray getPosterList(){
        return posters;
    }

}
