package com.path_studio.moviecatalogue.Activities;

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
import com.path_studio.moviecatalogue.Activities.MainActivity;
import com.path_studio.moviecatalogue.R;
import com.path_studio.moviecatalogue.YouTubeConfig;

public class DetailAT extends YouTubeBaseActivity implements View.OnClickListener {

    private static final String TAG = "DetailSTActivity";

    YouTubePlayerView mYouTubePlayerView;
    Button mPlayST;
    YouTubePlayer.OnInitializedListener mOnInitialozedListener;

    private TextView mJudul, mTahun, mOverview, mRattingText_AT, mLinkAT;
    private ImageView mPoster, mBack_01;
    private RatingBar mRating;

    private String[] juduls;
    private String[] descs;
    private String[] years;
    private String[] ratings;
    private String[] urls;
    private TypedArray posters;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_at);

        //buat youtube
        Log.d(TAG,"oncreate Starting");

        mPlayST = (Button) findViewById(R.id.YT_play_02);
        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.view_AT_video);

        mOnInitialozedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG,"OnCLick: Done Initializing Youtube Player");

                //get link video trailer from array
                String[] links = getResources().getStringArray(R.array.link_trailer_at);
                String link = links[getIntent().getExtras().getInt("IndexMovie")];

                youTubePlayer.loadVideo(link);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG,"OnCLick: Failed Initializing Youtube Player");
            }
        };

        mPlayST.setOnClickListener(this);

        //end youtube
        mLinkAT = (TextView) findViewById(R.id.link_web_at);
        mBack_01 = (ImageView) findViewById(R.id.back_home_01);

        mJudul = (TextView) findViewById(R.id.detail_judul);
        mPoster = (ImageView) findViewById(R.id.detail_poster);
        mTahun = (TextView) findViewById(R.id.detail_tahun);
        mOverview = (TextView) findViewById(R.id.detail_overview);
        mRattingText_AT = (TextView) findViewById(R.id.ratting_text_AT);

        mRating = (RatingBar) findViewById(R.id.ratingBar_AT);

        //set jadi array dulu
        juduls = getResources().getStringArray(R.array.data_akan_tayang);
        descs = getResources().getStringArray(R.array.data_desc_at);
        years = getResources().getStringArray(R.array.data_year_at);
        ratings = getResources().getStringArray(R.array.data_ratting_at);
        urls = getResources().getStringArray(R.array.link_web_at);

        posters = getResources().obtainTypedArray(R.array.data_photo_akan_tayang);

        //tampilkan datanya
        UI();

        //set tombol back onclick
        mBack_01.setOnClickListener(this);
        mLinkAT.setOnClickListener(this);

    }

    public void UI() {
        Bundle extras = getIntent().getExtras();
        index = extras.getInt("IndexMovie");

        mJudul.setText(juduls[index]);
        mTahun.setText(years[index]);

        mPoster.setImageResource(posters.getResourceId(index, 0));

        //hitung ratting
        float tampung = Integer.valueOf(ratings[index]);
        float hasil_ratting = ((float) tampung / 2) / 10;

        mRattingText_AT.setText(String.valueOf(hasil_ratting));

        mRating.setRating(hasil_ratting);
        mOverview.setText(descs[index]);
        mLinkAT.setText(urls[index]);
    }

    @Override
    public void onClick(View view) {
        //untuk tombol back ke halaman home
        switch (view.getId()) {
            case R.id.back_home_01:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.YT_play_02:
                Log.d(TAG,"OnCLick: Initializing Youtube Player");
                mYouTubePlayerView.initialize(YouTubeConfig.getApiKey(), mOnInitialozedListener);
                break;
            case R.id.link_web_at:
                //open website
                goToUrl(urls[index]);
                break;
        }
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

}
