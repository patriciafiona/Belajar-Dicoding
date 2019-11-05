package com.path_studio.moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailST extends AppCompatActivity implements View.OnClickListener {

    private TextView mJudul, mTahun, mOverview, mRattingText_ST;
    private ImageView mPoster, mBack_01;
    private RatingBar mRating;

    private String[] juduls;
    private String[] descs;
    private String[] years;
    private String[] ratings;
    private TypedArray posters;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_st);

        mBack_01 = (ImageView) findViewById(R.id.back_home_02);

        mJudul = (TextView) findViewById(R.id.detail_judul_ST);
        mPoster = (ImageView) findViewById(R.id.detail_poster_ST);
        mTahun = (TextView) findViewById(R.id.detail_tahun_ST);
        mOverview = (TextView) findViewById(R.id.detail_overview_ST);
        mRattingText_ST = (TextView) findViewById(R.id.ratting_text_ST);

        mRating = (RatingBar) findViewById(R.id.ratingBar_ST);

        //set jadi array dulu
        juduls = getResources().getStringArray(R.array.data_sedang_tayang);
        descs = getResources().getStringArray(R.array.data_desc_st);
        years = getResources().getStringArray(R.array.data_year_st);
        ratings = getResources().getStringArray(R.array.data_ratting_st);

        posters = getResources().obtainTypedArray(R.array.data_photo_sedang_tayang);

        //tampilkan datanya
        UI();

        //set tombol back onclick
        mBack_01.setOnClickListener(this);

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

        mRattingText_ST.setText(String.valueOf(hasil_ratting));

        mRating.setRating(hasil_ratting);

        mOverview.setText(descs[index]);
    }

    @Override
    public void onClick(View view) {
        //untuk tombol back ke halaman home
        switch (view.getId()) {
            case R.id.back_home_02:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
        }
    }
}