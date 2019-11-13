package com.path_studio.mymovie.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.path_studio.mymovie.Models.Movie;
import com.path_studio.mymovie.R;
import com.path_studio.mymovie.YouTubeConfig;

public class DetailTVActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String EXTRA_TV = "extra_tv";
    private static final String TAG = "DetailMovieActivity";

    private TextView mJudul, mTahun, mOverview, mRattingText_AT, mLinkAT;
    private ImageView mPoster, mBackMain;
    private RatingBar mRating;
    private TypedArray posters;

    //inisiasi parcetable
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);

        movie = getIntent().getParcelableExtra(EXTRA_TV);

        //INISIASI
        posters = getResources().obtainTypedArray(R.array.data_poster_tv);
        mLinkAT = findViewById(R.id.link_web_tv);
        mJudul = findViewById(R.id.detail_judul_tv);
        mPoster = findViewById(R.id.detail_poster_tv);
        mTahun = findViewById(R.id.detail_tahun_tv);
        mOverview = findViewById(R.id.detail_overview_tv);
        mRattingText_AT = findViewById(R.id.ratting_text_tv);
        mRating = findViewById(R.id.ratingBar_tv);

        mBackMain = findViewById(R.id.btn_back_main_02);
        mBackMain.setOnClickListener(this);

        //SET UI-nya beserta datanya
        UI();
    }

    private void UI() {
        mJudul.setText(movie.getName());
        mTahun.setText(movie.getYear());

        mPoster.setImageResource(posters.getResourceId(movie.getPhoto_index(), 0));

        //hitung ratting
        float tampung = movie.getRatting();
        float hasil_ratting = (tampung / 2) / 10;

        mRattingText_AT.setText(String.valueOf(hasil_ratting));

        mRating.setRating(hasil_ratting);
        mOverview.setText(movie.getDescription());
        mLinkAT.setText(movie.getLink_web());
    }

    @Override
    public void onClick(View view) {
        //untuk tombol back ke halaman home
        switch (view.getId()) {
            case R.id.link_web_tv:
                //open website
                goToUrl(movie.getLink_web());
                break;
            case R.id.btn_back_main_02:
                //back to main menu
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
        }
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

}
