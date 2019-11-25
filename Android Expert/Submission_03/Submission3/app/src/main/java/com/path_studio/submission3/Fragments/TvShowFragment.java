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
import android.widget.TextView;
import android.widget.Toast;

import com.path_studio.submission3.Activities.DetailTVActivity;
import com.path_studio.submission3.Adapters.TVAdapter;
import com.path_studio.submission3.InternetConnectionCheck;
import com.path_studio.submission3.Models.TVItems;
import com.path_studio.submission3.R;
import com.path_studio.submission3.Views.TVShowViewModel;

import java.util.ArrayList;

public class TvShowFragment extends Fragment {

    private TVAdapter adapter;
    private ProgressBar progressBar;
    private TVShowViewModel tvShowViewModel;

    private static TvShowFragment instance;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //check internet connection
        InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck();
        if(!internetConnectionCheck.isNetworkConnected(getActivity())){
            //show popup
            internetConnectionCheck.showAlertDialog(getActivity());
        }

        instance = this;

        progressBar = getActivity().findViewById(R.id.progressBar_2);
        RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerView_2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new TVAdapter(getActivity());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickCallback(new TVAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TVItems data) {
                showSelectedTVShow(data);
            }
        });

        //Mendapatkan bahasa sesuai pengaturan
        String language = getResources().getString(R.string.language_code);

        tvShowViewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.NewInstanceFactory()).get(TVShowViewModel.class);
        tvShowViewModel.setTVShow(language, getActivity());
        showLoading(true);

        tvShowViewModel.getTVShow().observe(getActivity(), new Observer<ArrayList<TVItems>>() {
            @Override
            public void onChanged(ArrayList<TVItems> tvItems) {
                if (tvItems != null) {
                    adapter.setData(tvItems);
                    showLoading(false);
                }
            }
        });

    }

    public static TvShowFragment getInstance() {
        return instance;
    }

    public void go_to_detail(int id){
        Intent i = new Intent(getActivity(), DetailTVActivity.class);
        i.putExtra("tv_id", id);
        startActivity(i);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showSelectedTVShow(TVItems tvShow) {
        String pilih = getResources().getString(R.string.your_choose);
        Toast.makeText(getActivity(), pilih + " " + tvShow.getTitle(), Toast.LENGTH_SHORT).show();
    }

}
