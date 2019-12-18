package com.path_studio.submission4.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.path_studio.submission4.Database.FavouriteHelper;
import com.path_studio.submission4.Entity.Favourite;
import com.path_studio.submission4.Models.TVItems;
import com.path_studio.submission4.R;
import com.path_studio.submission4.ViewModels.TVShowViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringJoiner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import static com.path_studio.submission4.Database.DatabaseContract.FavouriteColumns.DATA_ID;
import static com.path_studio.submission4.Database.DatabaseContract.FavouriteColumns.POSTER;
import static com.path_studio.submission4.Database.DatabaseContract.FavouriteColumns.RATTING;
import static com.path_studio.submission4.Database.DatabaseContract.FavouriteColumns.TITLE;
import static com.path_studio.submission4.Database.DatabaseContract.FavouriteColumns.TYPE;
import static com.path_studio.submission4.Database.DatabaseContract.TABLE_NAME_02;

public class DetailTVActivity extends AppCompatActivity implements View.OnClickListener{

    private int id_show;

    private ProgressBar progressBar;
    private TVShowViewModel tvShowViewModel;
    private ArrayList<TVItems> mData = new ArrayList<>();

    private static final String TAG = "DetailTVShowActivity";

    private TextView mJudul, mOverview, mRattingText;
    private TextView mGenre, mStatus, mCreatedBy, mFirstAir, mLastAir, mVoteCount,
            mLinkHomepage, mPopularity, mOriLanguage, mNetwork;
    private ImageView mPoster, mDetailBgTop;
    private RatingBar mRating;
    private TVItems tvItems;
    private Button mSeeAllSeasson;
    private ImageView mCurrentSeassonPoster;
    private TextView mCurrentSeassonName, mCurrentSeassonDetail, mCurrentSeassonOverview;
    private ConstraintLayout mSeassonBox;

    private TextView mT01, mT02, mT03;

    private TextView mDetail11, mDetail12, mDetail13;
    private TextView mDetail21, mDetail22, mDetail23, mDetail24;
    private TextView mFADText, mLADText;

    private FloatingActionButton fabAddTVShow;

    private Favourite favourite;
    private FavouriteHelper favouriteHelper;
    public static final String EXTRA_FAVOURITE = "extra_favourite";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;
    public static final int RESULT_DELETE = 301;
    private TVItems items = new TVItems();
    private boolean status_tv_fav = false;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);

        initiate();

        //get data detail from API
        String language = getResources().getString(R.string.language_code);

        tvShowViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(TVShowViewModel.class);

        // recovering the instance state
        if (savedInstanceState == null) {
            tvShowViewModel.setTVShow(language, id_show);
        }

        showLoading(true);
        hideAll();

        favouriteHelper = FavouriteHelper.getInstance(getApplicationContext());
        favourite = getIntent().getParcelableExtra(EXTRA_FAVOURITE);

        if (favourite != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        } else {
            favourite = new Favourite();
        }

        tvShowViewModel.getTVShow().observe(this, new Observer<ArrayList<TVItems>>() {
            @Override
            public void onChanged(ArrayList<TVItems> tvItems) {
                if (tvItems != null) {
                    setDataUI(tvItems);
                    showLoading(false);
                    seeAll();

                    favouriteHelper.open();
                    database(tvItems);
                }
            }
        });

    }

    private void database(ArrayList<TVItems> movieItems){

        //get movie id
        ArrayList<TVItems> data = movieItems;
        items = mData.get(0);
        int tv_id = items.getId_TV();

        //check apakah movie sudah favorit atau belum
        status_tv_fav = favouriteHelper.selectById(tv_id, TABLE_NAME_02);
        if(status_tv_fav){
            //sudah favorit, hati merah
            fabAddTVShow.setImageResource(R.drawable.ic_favorite_red_24dp);
        }else{
            //belum favorit, hati grey
            fabAddTVShow.setImageResource(R.drawable.ic_favorite_gray_24dp);
        }

        //set onclicknya
        fabAddTVShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check apakah sudah favorit atau belum
                if(status_tv_fav){
                    //sudah favorit, ubah ke unfavorit
                    status_tv_fav = false;

                    //delete datanya
                    deleteFavourite(items, view);

                }else{
                    //belum favorit, ubah ke favorit
                    status_tv_fav = true;

                    //insert datanya
                    insertFavourite(items, view);

                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        favouriteHelper.close();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void insertFavourite(TVItems items, View view){
        favourite.setData_id(items.getId_TV());
        favourite.setTitle(items.getTitle());
        favourite.setPoster(items.getPoster());
        favourite.setRatting(items.getRatting());
        favourite.setType("tv_show");

        Intent intent = new Intent();
        intent.putExtra(EXTRA_FAVOURITE, favourite);
        intent.putExtra(EXTRA_POSITION, position);

        ContentValues values = new ContentValues();
        values.put(TITLE, items.getTitle());
        values.put(TYPE, "tv_show");
        values.put(DATA_ID, items.getId_TV());
        values.put(RATTING, items.getRatting());
        values.put(POSTER, items.getPoster());
        long result = favouriteHelper.insert(values, TABLE_NAME_02);

        if (result > 0) {
            favourite.setId((int) result);
            setResult(RESULT_ADD, intent);

            //berhasil, maka ganti warna hatinya
            showSnackbarMessage(getResources().getString(R.string.add_favourite), view);
            fabAddTVShow.setImageResource(R.drawable.ic_favorite_red_24dp);

        } else {
            Toast.makeText(DetailTVActivity.this, getResources().getString(R.string.failed_add_data), Toast.LENGTH_SHORT).show();
        }

    }

    private void deleteFavourite(TVItems items, View view){

        long result = favouriteHelper.deleteById(String.valueOf(items.getId_TV()), TABLE_NAME_02);

        if (result > 0) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_POSITION, position);
            setResult(RESULT_DELETE, intent);

            showSnackbarMessage(getResources().getString(R.string.unfavourite), view);
            fabAddTVShow.setImageResource(R.drawable.ic_favorite_gray_24dp);

        } else {
            Toast.makeText(DetailTVActivity.this, getResources().getString(R.string.failed_remove_data), Toast.LENGTH_SHORT).show();
        }

    }

    private void showSnackbarMessage(String message, View view) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    private void initiate(){
        Intent mIntent = getIntent();
        id_show = mIntent.getIntExtra("tv_id", 0);

        progressBar = findViewById(R.id.progressBar_detail_tv);
        mDetailBgTop = findViewById(R.id.detail_bg_top_2);
        mJudul = findViewById(R.id.detail_judul_2);
        mPoster = findViewById(R.id.detail_poster_2);
        mOverview = findViewById(R.id.detail_overview_tv);
        mRattingText = findViewById(R.id.ratting_text_tv);
        mRating = findViewById(R.id.ratingBar_tv);
        mFirstAir = findViewById(R.id.detail_first_air);
        mLastAir = findViewById(R.id.detail_last_air);
        mVoteCount = findViewById(R.id.detail_voteCount_2);
        mPopularity = findViewById(R.id.detail_popularity_2);
        mStatus = findViewById(R.id.detail_in_production);
        mCreatedBy = findViewById(R.id.detail_created_by_2);
        mGenre = findViewById(R.id.detail_genre_2);
        mLinkHomepage = findViewById(R.id.detail_homepage);
        mOriLanguage = findViewById(R.id.detail_languages_tv);
        mNetwork = findViewById(R.id.networks_tv);

        mSeassonBox = findViewById(R.id.c_seasson_box);
        mT01 = findViewById(R.id.textView2);
        mT02 = findViewById(R.id.textView5);
        mT03 = findViewById(R.id.textView6);

        mDetail11 = findViewById(R.id.textView13);
        mDetail12 = findViewById(R.id.textView14);
        mDetail13 = findViewById(R.id.textView15);

        mDetail21 = findViewById(R.id.textView16);
        mDetail22 = findViewById(R.id.textView17);
        mDetail23 = findViewById(R.id.textView18);
        mDetail24 = findViewById(R.id.textView19);

        mFADText = findViewById(R.id.textView3);
        mLADText = findViewById(R.id.textView4);

        //current seasson detail
        mCurrentSeassonPoster = findViewById(R.id.detail_current_seasson_poster);
        mCurrentSeassonName = findViewById(R.id.c_seasson_name);
        mCurrentSeassonDetail = findViewById(R.id.c_seasson_detail);
        mCurrentSeassonOverview = findViewById(R.id.detail_current_seasson_overview);
        mSeeAllSeasson = findViewById(R.id.detail_see_all_season);

        mSeeAllSeasson.setOnClickListener(this);
        mLinkHomepage.setOnClickListener(this);

        fabAddTVShow = findViewById(R.id.fab_add_tv);
    }

    private void setDataUI(ArrayList<TVItems> items){
        mData.clear();
        mData.addAll(items);

        tvItems = mData.get(0);

        //menampilkan genre
        ArrayList<String> genres = tvItems.getGenre();
        if(genres!=null && !genres.isEmpty()) {
            StringJoiner sj = new StringJoiner(", ");

            for (String s : genres) {
                sj.add(s);
            }
            mGenre.setText(sj.toString());
        }

        //menampilkan created by
        ArrayList<String> created_by = tvItems.getCreated_by();
        if(created_by!=null && !created_by.isEmpty()) {
            StringJoiner sj = new StringJoiner(", ");

            for (String s : created_by) {
                sj.add(s);
            }
            mCreatedBy.setText(sj.toString());
        }else{
            mCreatedBy.setText("-");
        }

        //menampilkan network
        ArrayList<String> network = tvItems.getNetwork();
        if(network!=null && !network.isEmpty()) {
            StringJoiner sj = new StringJoiner(", ");

            for (String s : network) {
                sj.add(s);
            }
            mNetwork.setText(sj.toString());
        }else{
            mNetwork.setText("-");
        }

        //menampilkan overview
        if(tvItems.getDescription()!=null  && !tvItems.getDescription().isEmpty()){
            mOverview.setText(tvItems.getDescription());
        }else{
            mOverview.setText(getResources().getString(R.string.no_translate));
        }

        //menampilkan tanggal first air
        if(tvItems.getFirst_air_date() != null && !tvItems.getFirst_air_date().isEmpty()){
            String tanggal = tvItems.getFirst_air_date();
            try {
                Date date=new SimpleDateFormat("yyyy-MM-dd").parse(tanggal);

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String strDate = formatter.format(date);
                mFirstAir.setText(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //menampilkan tanggal last air
        if(tvItems.getLast_air_date() != null && !tvItems.getLast_air_date().isEmpty()){
            String tanggal = tvItems.getLast_air_date();
            try {
                Date date=new SimpleDateFormat("yyyy-MM-dd").parse(tanggal);

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String strDate = formatter.format(date);
                mLastAir.setText(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //hitung ratting
        double tampung = tvItems.getRatting();
        float hasil_ratting = ((float)tampung / 2);
        mRattingText.setText(String.valueOf(tampung));

        mJudul.setText(tvItems.getTitle());
        mRating.setRating(hasil_ratting);
        mStatus.setText(tvItems.getProduction_status());
        mPopularity.setText(tvItems.getPopularity());
        mLinkHomepage.setText(tvItems.getLink_homepage());
        mOriLanguage.setText(tvItems.getOrigial_language());

        String vc = tvItems.getVote_count()+" "+ getResources().getString(R.string.vote_count_text);
        mVoteCount.setText(vc);

        Glide.with(this)
                .load(tvItems.getPoster())
                .apply(new RequestOptions().override(200, 300))
                .into(mPoster);

        Glide.with(this)
                .load(tvItems.getBackdrop())
                .apply(new RequestOptions().override(500, 300))
                .into(mDetailBgTop);

        //current seasson detail
        if(tvItems.getSeasson_name() != null && !tvItems.getSeasson_name().isEmpty()){

            int length_seasson = tvItems.getSeasson_name().size();

            if(!tvItems.getSeasson_poster().get(length_seasson - 1).equals("http://image.tmdb.org/t/p/originalnull")){
                Glide.with(this)
                        .load(tvItems.getSeasson_poster().get(length_seasson-1))
                        .apply(new RequestOptions().override(200, 300))
                        .into(mCurrentSeassonPoster);
            }else{
                mCurrentSeassonPoster.setImageResource(R.color.colorSolidGrey);
            }


            mCurrentSeassonName.setText(tvItems.getSeasson_name().get(length_seasson-1));

            String date = tvItems.getSeasson_airDate().get(length_seasson-1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = null;
            try {
                parse = sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar c = Calendar.getInstance();
            c.setTime(parse);

            int eps_count = tvItems.getSeasson_episodeCount().get(length_seasson-1);

            String seassonDetail = c.get(Calendar.YEAR) + " | " + eps_count + " Episodes.";

            mCurrentSeassonDetail.setText(seassonDetail);

            if(tvItems.getSeasson_overview().get(length_seasson-1)!=null && !tvItems.getSeasson_overview().get(length_seasson - 1).equals(""))
                mCurrentSeassonOverview.setText(tvItems.getSeasson_overview().get(length_seasson-1));
            else
                mCurrentSeassonOverview.setText(getResources().getString(R.string.no_overview));
        }

    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void hideAll(){
        mDetailBgTop.setVisibility(View.GONE);
        mJudul.setVisibility(View.GONE);
        mPoster.setVisibility(View.GONE);
        mOverview.setVisibility(View.GONE);
        mVoteCount.setVisibility(View.GONE);
        mSeassonBox.setVisibility(View.GONE);
        mSeeAllSeasson.setVisibility(View.GONE);
        mT01.setVisibility(View.GONE);
        mT02.setVisibility(View.GONE);
        mT03.setVisibility(View.GONE);

        mDetail11.setVisibility(View.GONE);
        mDetail12.setVisibility(View.GONE);
        mDetail13.setVisibility(View.GONE);

        mDetail21.setVisibility(View.GONE);
        mDetail22.setVisibility(View.GONE);
        mDetail23.setVisibility(View.GONE);
        mDetail24.setVisibility(View.GONE);

        mPopularity.setVisibility(View.GONE);
        mLinkHomepage.setVisibility(View.GONE);
        mOriLanguage.setVisibility(View.GONE);
        mNetwork.setVisibility(View.GONE);

        mRattingText.setVisibility(View.GONE);
        mRating.setVisibility(View.GONE);
        mFirstAir.setVisibility(View.GONE);
        mLastAir.setVisibility(View.GONE);
        mFADText.setVisibility(View.GONE);
        mLADText.setVisibility(View.GONE);
    }

    private void seeAll(){
        mDetailBgTop.setVisibility(View.VISIBLE);
        mJudul.setVisibility(View.VISIBLE);
        mPoster.setVisibility(View.VISIBLE);
        mOverview.setVisibility(View.VISIBLE);
        mVoteCount.setVisibility(View.VISIBLE);
        mSeassonBox.setVisibility(View.VISIBLE);
        mSeeAllSeasson.setVisibility(View.VISIBLE);
        mT01.setVisibility(View.VISIBLE);
        mT02.setVisibility(View.VISIBLE);
        mT03.setVisibility(View.VISIBLE);

        mDetail11.setVisibility(View.VISIBLE);
        mDetail12.setVisibility(View.VISIBLE);
        mDetail13.setVisibility(View.VISIBLE);

        mDetail21.setVisibility(View.VISIBLE);
        mDetail22.setVisibility(View.VISIBLE);
        mDetail23.setVisibility(View.VISIBLE);
        mDetail24.setVisibility(View.VISIBLE);

        mPopularity.setVisibility(View.VISIBLE);
        mLinkHomepage.setVisibility(View.VISIBLE);
        mOriLanguage.setVisibility(View.VISIBLE);
        mNetwork.setVisibility(View.VISIBLE);

        mRattingText.setVisibility(View.VISIBLE);
        mRating.setVisibility(View.VISIBLE);
        mFirstAir.setVisibility(View.VISIBLE);
        mLastAir.setVisibility(View.VISIBLE);
        mFADText.setVisibility(View.VISIBLE);
        mLADText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_homepage:
                goToUrl(tvItems.getLink_homepage());
                break;
            case R.id.detail_see_all_season:
                Intent i = new Intent(this, DetailTvSeasonActivity.class);
                i.putExtra("tv_id", id_show);
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