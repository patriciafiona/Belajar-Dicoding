package com.path_studio.submission4.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.path_studio.submission4.Adapters.FavouriteAdapter;
import com.path_studio.submission4.R;

public class FavTVShowFragment extends Fragment {

    private ProgressBar progressBar;
    private RecyclerView rvFavTVShow;
    private FavouriteAdapter adapter;

    public FavTVShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_tvshow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        progressBar = view.findViewById(R.id.progressbar);
        rvFavTVShow = view.findViewById(R.id.rv_fav_tvshow);
        rvFavTVShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavTVShow.setHasFixedSize(true);
        adapter = new FavouriteAdapter(getActivity());
        rvFavTVShow.setAdapter(adapter);
    }

}
