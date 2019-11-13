package com.path_studio.mymovie.Activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.path_studio.mymovie.Models.Movie;
import com.path_studio.mymovie.R;
import com.path_studio.mymovie.YouTubeConfig;

public class DetailMovieActivity extends YouTubeBaseActivity implements View.OnClickListener {

    public static final String EXTRA_MOVIE = "extra_movie";
    private static final String TAG = "DetailMovieActivity";

    private TextView mJudul, mTahun, mOverview, mRattingText_AT, mLinkAT;
    private ImageView mPoster;
    private RatingBar mRating;
    private TypedArray posters;

    //inisiasi parcetable
    private Movie movie;

    //Youtube
    YouTubePlayerView mYouTubePlayerView;
    Button mPlayST;
    YouTubePlayer.OnInitializedListener mOnInitialozedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        //buat youtube
        Log.d(TAG,"oncreate Starting");

        mPlayST = (Button) findViewById(R.id.YT_play_01);
        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.view_movie_video);

        mOnInitialozedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG,"OnCLick: Done Initializing Youtube Player");

                //get link video trailer
                String link = movie.getLink_trailer();
                youTubePlayer.loadVideo(link);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG,"OnCLick: Failed Initializing Youtube Player");
            }
        };

        mPlayST.setOnClickListener(this);

        //end youtube

        //INISIASI
        posters = getResources().obtainTypedArray(R.array.data_poster_movie);

        mLinkAT = findViewById(R.id.link_web_movie);

        mJudul = findViewById(R.id.detail_judul);
        mPoster = findViewById(R.id.detail_poster);
        mTahun = findViewById(R.id.detail_tahun);
        mOverview = findViewById(R.id.detail_overview_movie);
        mRattingText_AT = findViewById(R.id.ratting_text_movie);

        mRating = findViewById(R.id.ratingBar_movie);

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
            case R.id.YT_play_01:
                Log.d(TAG,"OnCLick: Initializing Youtube Player");
                mYouTubePlayerView.initialize(YouTubeConfig.getApiKey(), mOnInitialozedListener);
                break;
            case R.id.link_web_movie:
                //open website
                goToUrl(movie.getLink_web());
                break;
        }
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

}
