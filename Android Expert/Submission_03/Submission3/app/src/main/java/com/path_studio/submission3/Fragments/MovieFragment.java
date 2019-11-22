package com.path_studio.submission3.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.path_studio.submission3.Activities.DetailMovieActivity;
import com.path_studio.submission3.Adapters.MovieAdapter;
import com.path_studio.submission3.Models.MovieItems;
import com.path_studio.submission3.R;
import com.path_studio.submission3.Views.MovieViewModel;

import java.util.ArrayList;

public class MovieFragment extends Fragment {

    private MovieAdapter adapter;
    private ProgressBar progressBar;
    private MovieViewModel movieViewModel;

    private static MovieFragment instance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        instance = this;

        progressBar = getActivity().findViewById(R.id.progressBar);
        RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new MovieAdapter(getActivity());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MovieItems data) {
                showSelectedMovie(data);
            }
        });

        //Mendapatkan bahasa sesuai pengaturan
        String language = getResources().getString(R.string.language_code);

        movieViewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        movieViewModel.setMovie(language, getActivity());
        showLoading(true);

        movieViewModel.getMovies().observe(getActivity(), new Observer<ArrayList<MovieItems>>() {
            @Override
            public void onChanged(ArrayList<MovieItems> movieItems) {
                if (movieItems != null) {
                    adapter.setData(movieItems);
                    showLoading(false);
                }
            }
        });

    }

    public static MovieFragment getInstance() {
        return instance;
    }

    public void go_to_detail(int id){
        Intent i = new Intent(getActivity(), DetailMovieActivity.class);
        i.putExtra("movie_id", id);
        startActivity(i);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showSelectedMovie(MovieItems movie) {
        String pilih = getResources().getString(R.string.your_choose);
        Toast.makeText(getActivity(), pilih + movie.getName(), Toast.LENGTH_SHORT).show();
    }

}
