package com.path_studio.submission3.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.path_studio.submission3.Models.MovieItems;
import com.path_studio.submission3.R;
import com.path_studio.submission3.Views.MovieViewModel;

import java.util.ArrayList;

public class DetailMovieActivity extends AppCompatActivity implements View.OnClickListener{

    private int id_movie;

    private ProgressBar progressBar;
    private MovieViewModel movieViewModel;
    private ArrayList<MovieItems> mData = new ArrayList<>();

    private static final String TAG = "DetailMovieActivity";

    private TextView mJudul, mTahun, mOverview, mRattingText_AT, mLinkAT;
    private ImageView mPoster, mBackMain;
    private RatingBar mRating;
    private TypedArray posters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Intent mIntent = getIntent();
        id_movie = mIntent.getIntExtra("movie_id", 0);

        progressBar = findViewById(R.id.progressBar_detail_movie);
        mLinkAT = findViewById(R.id.link_web_movie);
        mJudul = findViewById(R.id.detail_judul);
        mPoster = findViewById(R.id.detail_poster);
        mTahun = findViewById(R.id.detail_tahun);
        mOverview = findViewById(R.id.detail_overview_movie);
        mRattingText_AT = findViewById(R.id.ratting_text_movie);
        mRating = findViewById(R.id.ratingBar_movie);
        mBackMain = findViewById(R.id.btn_back_main_01);
        mBackMain.setOnClickListener(this);

        //get data detail from API
        String language = getResources().getString(R.string.language_code);

        movieViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        movieViewModel.setMovie(language, id_movie, this);
        showLoading(true);

        movieViewModel.getMovies().observe(this, new Observer<ArrayList<MovieItems>>() {
            @Override
            public void onChanged(ArrayList<MovieItems> movieItems) {
                if (movieItems != null) {
                    setDataUI(movieItems);
                    showLoading(false);
                }
            }
        });

    }

    private void setDataUI(ArrayList<MovieItems> items){
        mData.clear();
        mData.addAll(items);

        MovieItems movieItems = mData.get(0);

        mJudul.setText(movieItems.getName());
        mTahun.setText(movieItems.getYear());

        //hitung ratting
        double tampung = movieItems.getRatting();
        float hasil_ratting = ((float)tampung / 2);

        mRattingText_AT.setText(String.valueOf(hasil_ratting));

        mRating.setRating(hasil_ratting);
        mOverview.setText(movieItems.getDescription());
        mLinkAT.setText(movieItems.getLink_web());
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        //
    }
}
