package com.path_studio.submission_05.BannerWidget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.path_studio.submission_05.Database.FavouriteHelper;
import com.path_studio.submission_05.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewsFactoryMovie implements RemoteViewsService.RemoteViewsFactory {
    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final Context mContext;

    private ArrayList poster_movie = new ArrayList<>();

    private FavouriteHelper favouriteHelper;

    StackRemoteViewsFactoryMovie(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {
        //require
    }

    private void getDataMovie(){
        favouriteHelper = FavouriteHelper.getInstance(mContext.getApplicationContext());
        favouriteHelper.open();
        poster_movie = favouriteHelper.selectData("fav_movie");
        favouriteHelper.close();
    }

    @Override
    public void onDataSetChanged() {
        //Ini berfungsi untuk melakukan refresh saat terjadi perubahan.
        getDataMovie();

        if(poster_movie!=null && !poster_movie.isEmpty()){
            for(int i=0; i<poster_movie.size();i++){
                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(mContext)
                            .asBitmap()
                            .load(poster_movie.get(i))
                            .submit(512, 512)
                            .get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mWidgetItems.add(bitmap);
            }
        }else{
            Log.e("link poster", "Kosong");
        }

    }

    @Override
    public void onDestroy() {
        //required
    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item_movie);
        rv.setImageViewBitmap(R.id.banner_image_movie, mWidgetItems.get(position));

        Bundle extras = new Bundle();
        extras.putInt(FavouriteMovieBannerWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.banner_image_movie, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
