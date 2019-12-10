package com.path_studio.submission3.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.path_studio.submission3.Models.MovieItems;
import com.path_studio.submission3.R;
import com.path_studio.submission3.ViewModels.MovieViewModel;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.StringJoiner;

public class DetailMovieActivity extends AppCompatActivity implements View.OnClickListener{

    private int id_movie;

    private ProgressBar progressBar;
    private MovieViewModel movieViewModel;
    private ArrayList<MovieItems> mData = new ArrayList<>();

    private static final String TAG = "DetailMovieActivity";

    private TextView mJudul, mOverview, mRattingText;
    private TextView mGenre, mStatus, mAgeRating, mRelease, mVoteCount, mRevenue, mPopularity, mOriLanguage, mIMDB;
    private ImageView mPoster, mDetailBgTop;
    private RatingBar mRating;

    private TextView mT01, mT02, mT03;

    private TextView mDetail01, mDetail02, mDetail03;
    private TextView mDetail04, mDetail05, mDetail06, mDetail07;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Intent mIntent = getIntent();
        id_movie = mIntent.getIntExtra("movie_id", 0);

        progressBar = findViewById(R.id.progressBar_detail_movie);
        mDetailBgTop = findViewById(R.id.detail_bg_top);
        mJudul = findViewById(R.id.detail_judul);
        mPoster = findViewById(R.id.detail_poster);
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

        //table detail 01
        mDetail01 = findViewById(R.id.textView);
        mDetail02 = findViewById(R.id.textView7);
        mDetail03 = findViewById(R.id.textView8);

        //ratting detail 02
        mDetail04 = findViewById(R.id.textView9);
        mDetail05 = findViewById(R.id.textView10);
        mDetail06 = findViewById(R.id.textView11);
        mDetail07 = findViewById(R.id.textView12);

        mT01 = findViewById(R.id.textView3);
        mT02 = findViewById(R.id.textView2);
        mT03 = findViewById(R.id.textView4);

        //get data detail from API
        String language = getResources().getString(R.string.language_code);

        movieViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(MovieViewModel.class);

        // recovering the instance state
        if (savedInstanceState == null) {
            movieViewModel.setMovie(language, id_movie);
        }

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
        ArrayList<String> genres = movieItems.getGenre();

        if(genres!=null && !genres.isEmpty()) {
            StringJoiner sj = new StringJoiner(", ");

            for (String s : genres) {
                sj.add(s);
            }
            mGenre.setText(sj.toString());
        }

        //menampilkan age rating
        if(movieItems.isAdult())
            mAgeRating.setText(getResources().getString(R.string.age_category_17));
        else
            mAgeRating.setText(getResources().getString(R.string.age_category_all));

        //menampilkan tanggal release
        if(movieItems.getRelease_date() != null && !movieItems.getRelease_date().isEmpty()){
            String tanggal = movieItems.getRelease_date();
            try {
                Date date=new SimpleDateFormat("yyyy-MM-dd").parse(tanggal);

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String strDate = formatter.format(date);
                mRelease.setText(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //menampilkan overview
        if(movieItems.getDescription()!=null  && !movieItems.getDescription().isEmpty()){
            mOverview.setText(movieItems.getDescription());
        }else{
            mOverview.setText(getResources().getString(R.string.no_translate));
        }

        //menampilkan revenue
        if(movieItems.getRevenue()!=null  && !movieItems.getRevenue().isEmpty()) {
            double rev = Double.parseDouble(movieItems.getRevenue());
            NumberFormat US_currency = NumberFormat.getCurrencyInstance(Locale.US);
            mRevenue.setText(US_currency.format(rev));
        }else{
            mRevenue.setText(" - ");
        }



        //hitung ratting
        double tampung = movieItems.getRatting();
        float hasil_ratting = ((float)tampung / 2);
        mRattingText.setText(String.valueOf(tampung));

        mJudul.setText(movieItems.getName());
        mRating.setRating(hasil_ratting);
        mStatus.setText(movieItems.getStatus());
        mPopularity.setText(movieItems.getPopularity());
        mIMDB.setText(movieItems.getImdb_id());
        mOriLanguage.setText(movieItems.getOriginal_language());

        String vc = movieItems.getVote_count()+" "+ getResources().getString(R.string.vote_count_text);
        mVoteCount.setText(vc);

        Glide.with(this)
                .load(movieItems.getPoster())
                .apply(new RequestOptions().override(200, 300))
                .into(mPoster);

        Glide.with(this)
                .load(movieItems.getBackdrop())
                .apply(new RequestOptions().override(500, 300))
                .into(mDetailBgTop);
    }

    private void hideAll(){
        //set visible = false when still loading
        mDetailBgTop.setVisibility(View.GONE);
        mJudul.setVisibility(View.GONE);
        mPoster.setVisibility(View.GONE);
        mOverview.setVisibility(View.GONE);
        mRattingText.setVisibility(View.GONE);
        mRating.setVisibility(View.GONE);
        mRelease.setVisibility(View.GONE);
        mVoteCount.setVisibility(View.GONE);

        mDetail01.setVisibility(View.GONE);
        mDetail02.setVisibility(View.GONE);
        mDetail03.setVisibility(View.GONE);

        mDetail04.setVisibility(View.GONE);
        mDetail05.setVisibility(View.GONE);
        mDetail06.setVisibility(View.GONE);
        mDetail07.setVisibility(View.GONE);

        mRevenue.setVisibility(View.GONE);
        mPopularity.setVisibility(View.GONE);
        mOriLanguage.setVisibility(View.GONE);
        mIMDB.setVisibility(View.GONE);

        mT01.setVisibility(View.GONE);
        mT02.setVisibility(View.GONE);
        mT03.setVisibility(View.GONE);
    }

    private void seeAll(){
        //set visible = true when loading finish
        mDetailBgTop.setVisibility(View.VISIBLE);
        mJudul.setVisibility(View.VISIBLE);
        mPoster.setVisibility(View.VISIBLE);
        mOverview.setVisibility(View.VISIBLE);
        mRattingText.setVisibility(View.VISIBLE);
        mRating.setVisibility(View.VISIBLE);
        mRelease.setVisibility(View.VISIBLE);
        mVoteCount.setVisibility(View.VISIBLE);

        mDetail01.setVisibility(View.VISIBLE);
        mDetail02.setVisibility(View.VISIBLE);
        mDetail03.setVisibility(View.VISIBLE);

        mDetail04.setVisibility(View.VISIBLE);
        mDetail05.setVisibility(View.VISIBLE);
        mDetail06.setVisibility(View.VISIBLE);
        mDetail07.setVisibility(View.VISIBLE);

        mRevenue.setVisibility(View.VISIBLE);
        mPopularity.setVisibility(View.VISIBLE);
        mOriLanguage.setVisibility(View.VISIBLE);
        mIMDB.setVisibility(View.VISIBLE);

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
