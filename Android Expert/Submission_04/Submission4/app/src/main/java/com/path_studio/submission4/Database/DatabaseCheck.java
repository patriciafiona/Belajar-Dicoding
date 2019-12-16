package com.path_studio.submission4.Database;

import android.app.Activity;

import com.path_studio.submission4.Entity.Favourite;

public class DatabaseCheck {

    private boolean isEdit = false;
    private Favourite favourite;
    private int position;
    private FavouriteHelper favouriteHelper;

    private boolean isFavourite = false;

    public static final String EXTRA_favourite = "extra_favourite";
    public static final String EXTRA_POSITION = "extra_position";

    private final Activity mActivity;

    public DatabaseCheck(Activity mActivity){
        this.mActivity = mActivity;
        Initiate();
    }

    protected void Initiate() {
        favouriteHelper = FavouriteHelper.getInstance(mActivity.getApplicationContext());

        favourite = mActivity.getIntent().getParcelableExtra(EXTRA_favourite);
        if (favourite != null) {
            position = mActivity.getIntent().getIntExtra(EXTRA_POSITION, 0);
            isEdit = true;
        } else {
            favourite = new Favourite();
        }

    }

    public boolean movieIsFavourite(int movie_id){
        return isFavourite;
    }

}
