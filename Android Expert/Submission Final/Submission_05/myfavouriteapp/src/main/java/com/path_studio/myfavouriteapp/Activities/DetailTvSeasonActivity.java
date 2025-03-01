package com.path_studio.myfavouriteapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.path_studio.myfavouriteapp.Adapters.SeasonAdapter;
import com.path_studio.myfavouriteapp.Models.TVItems;
import com.path_studio.myfavouriteapp.R;
import com.path_studio.myfavouriteapp.ViewModels.TVShowViewModel;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetailTvSeasonActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private SeasonAdapter adapter;
    private TVShowViewModel tvShowViewModel;
    private int id_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_season);

        initiate(savedInstanceState);
    }

    private void initiate(Bundle savedInstanceState){
        //inisiasi
        progressBar = findViewById(R.id.progressBar_season_detail);

        final RecyclerView recyclerView = findViewById(R.id.recyclerView_season_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SeasonAdapter(this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        String language = getResources().getString(R.string.language_code);

        Intent mIntent = getIntent();
        id_show = mIntent.getIntExtra("tv_id", 0);

        tvShowViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(TVShowViewModel.class);

        // recovering the instance state
        if (savedInstanceState == null) {
            tvShowViewModel.setSeassonDetail(language, id_show);
        }

        recyclerView.setVisibility(View.GONE);
        showLoading(true);

        tvShowViewModel.getTVShow().observe(this, new Observer<ArrayList<TVItems>>() {
            @Override
            public void onChanged(ArrayList<TVItems> tvItems) {
                if (tvItems != null) {
                    adapter.setData(tvItems);
                    recyclerView.setVisibility(View.VISIBLE);
                    showLoading(false);
                }
            }
        });

    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
