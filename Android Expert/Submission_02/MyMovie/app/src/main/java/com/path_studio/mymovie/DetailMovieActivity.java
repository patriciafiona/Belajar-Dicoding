package com.path_studio.mymovie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.path_studio.mymovie.Models.Movie;

public class DetailMovieActivity extends YouTubeBaseActivity implements View.OnClickListener {

    public static final String EXTRA_MOVIE = "extra_movie";
    private static final String TAG = "DetailMovieActivity";

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

        //movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

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
