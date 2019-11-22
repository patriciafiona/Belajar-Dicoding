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
import android.widget.TableLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.path_studio.submission3.Models.MovieItems;
import com.path_studio.submission3.R;
import com.path_studio.submission3.Views.MovieViewModel;

import java.text.DateFormat;
import java.util.ArrayList;

public class DetailMovieActivity extends AppCompatActivity implements View.OnClickListener{

    private int id_movie;
    private String hasil_genre;

    private ProgressBar progressBar;
    private MovieViewModel movieViewModel;
    private ArrayList<MovieItems> mData = new ArrayList<>();

    private static final String TAG = "DetailMovieActivity";

    private TextView mJudul, mTahun, mOverview, mRattingText;
    private TextView mGenre, mStatus, mAgeRating, mRelease, mVoteCount, mRevenue, mPopularity, mOriLanguage, mIMDB;
    private ImageView mPoster, mBackMain;
    private RatingBar mRating;

    private TableLayout mDetail01, mDetail02;
    private TextView mT01, mT02, mT03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Intent mIntent = getIntent();
        id_movie = mIntent.getIntExtra("movie_id", 0);

        progressBar = findViewById(R.id.progressBar_detail_movie);
        mJudul = findViewById(R.id.detail_judul);
        mPoster = findViewById(R.id.detail_poster);
        mTahun = findViewById(R.id.detail_tahun);
        mOverview = findViewById(R.id.detail_overview_movie);
        mRattingText = findViewById(R.id.ratting_text_movie);
        mRating = findViewById(R.id.ratingBar_movie);
        mRelease = findViewById(R.id.detail_release);

        mGenre = findViewById(R.id.detail_genre);
        mStatus = findViewById(R.id.detail_status);
        mAgeRating = findViewById(R.id.detail_age_rating);
        mVoteCount = findViewById(R.id.detail_voteCount);
        mRevenue = findViewById(R.id.detail_revenue);
        mPopularity = findViewById(R.id.detail_popularity);
        mOriLanguage = findViewById(R.id.detail_ori_language);
        mIMDB = findViewById(R.id.detail_imdb);

        mDetail01 = findViewById(R.id.detail_table01);
        mDetail02 = findViewById(R.id.detail_table02);

        mT01 = findViewById(R.id.textView3);
        mT02 = findViewById(R.id.textView2);
        mT03 = findViewById(R.id.textView4);

        mBackMain = findViewById(R.id.btn_back_main_01);
        mBackMain.setOnClickListener(this);

        //get data detail from API
        String language = getResources().getString(R.string.language_code);

        movieViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        movieViewModel.setMovie(language, id_movie, this);
        showLoading(true);
        hideAll();

        movieViewModel.getMovies().observe(this, new Observer<ArrayList<MovieItems>>() {
            @Override
            public void onChanged(ArrayList<MovieItems> movieItems) {
                if (movieItems != null) {
                    setDataUI(movieItems);
                    showLoading(false);
                    seeAll();
                }
            }
        });

    }

    private void setDataUI(ArrayList<MovieItems> items){
        mData.clear();
        mData.addAll(items);

        MovieItems movieItems = mData.get(0);

        //menampilkan genre
        String[] genres = movieItems.getGenre();
        if(genres != null && genres.length>0){
            for(int i=0; i< genres.length;i++){
                hasil_genre = hasil_genre + genres[i];

                if(i < genres.length-1){
                    hasil_genre = hasil_genre + ", ";
                }else{
                    hasil_genre = hasil_genre + ".";
                }
            }
            mGenre.setText(hasil_genre);
        }

        //menamilkan tahun film
        mTahun.setText(movieItems.getRelease_date());

        //menampilkan age rating
        if(movieItems.isAdult())
            mAgeRating.setText(getResources().getString(R.string.age_category_17));
        else
            mAgeRating.setText(getResources().getString(R.string.age_category_all));

        //hitung ratting
        double tampung = movieItems.getRatting();
        float hasil_ratting = ((float)tampung / 2);
        mRattingText.setText(String.valueOf(hasil_ratting));

        mJudul.setText(movieItems.getName());
        mRating.setRating(hasil_ratting);
        mOverview.setText(movieItems.getDescription());
        mStatus.setText(movieItems.getStatus());
        mRelease.setText(movieItems.getRelease_date());
        mRevenue.setText(movieItems.getRevenue());
        mPopularity.setText(movieItems.getPopularity());
        mIMDB.setText(movieItems.getImdb_id());
        mOriLanguage.setText(movieItems.getOriginal_language());

        String vc = movieItems.getVote_count()+" "+ getResources().getString(R.string.vote_count_text);
        mVoteCount.setText(vc);

        Glide.with(this)
                .load(movieItems.getPoster())
                .apply(new RequestOptions().override(200, 300))
                .into(mPoster);
    }

    private void hideAll(){
        //set visible = false when still loading
        mBackMain.setVisibility(View.GONE);
        mJudul.setVisibility(View.GONE);
        mPoster.setVisibility(View.GONE);
        mTahun.setVisibility(View.GONE);
        mOverview.setVisibility(View.GONE);
        mRattingText.setVisibility(View.GONE);
        mRating.setVisibility(View.GONE);
        mRelease.setVisibility(View.GONE);
        mVoteCount.setVisibility(View.GONE);

        mDetail01.setVisibility(View.GONE);
        mDetail02.setVisibility(View.GONE);

        mT01.setVisibility(View.GONE);
        mT02.setVisibility(View.GONE);
        mT03.setVisibility(View.GONE);
    }

    private void seeAll(){
        //set visible = true when loading finish
        mBackMain.setVisibility(View.VISIBLE);
        mJudul.setVisibility(View.VISIBLE);
        mPoster.setVisibility(View.VISIBLE);
        mTahun.setVisibility(View.VISIBLE);
        mOverview.setVisibility(View.VISIBLE);
        mRattingText.setVisibility(View.VISIBLE);
        mRating.setVisibility(View.VISIBLE);
        mRelease.setVisibility(View.VISIBLE);
        mVoteCount.setVisibility(View.VISIBLE);

        mDetail01.setVisibility(View.VISIBLE);
        mDetail02.setVisibility(View.VISIBLE);

        mT01.setVisibility(View.VISIBLE);
        mT02.setVisibility(View.VISIBLE);
        mT03.setVisibility(View.VISIBLE);
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
