package com.path_studio.moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailAT extends AppCompatActivity implements View.OnClickListener {

    private TextView mJudul, mTahun, mOverview, mRattingText_AT;
    private ImageView mPoster;
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
        setContentView(R.layout.activity_detail_at);

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

        posters = getResources().obtainTypedArray(R.array.data_photo_akan_tayang);

        //tampilkan datanya
        UI();

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
    }

    @Override
    public void onClick(View view) {

    }
}
