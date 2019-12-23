package com.path_studio.submission_05.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.path_studio.submission_05.Database.FavouriteHelper;

import androidx.annotation.NonNull;

import static com.path_studio.submission_05.Database.DatabaseContract.AUTHORITY_TV;
import static com.path_studio.submission_05.Database.DatabaseContract.FavouriteColumns.CONTENT_URI_02;
import static com.path_studio.submission_05.Database.DatabaseContract.TABLE_NAME_02;

public class FavouriteTVProvider extends ContentProvider {
    private static final int FAVOURITE = 2;
    private static final int FAVOURITE_ID = 22;
    private FavouriteHelper favouriteHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // content://com.path_studio.submission_05.tv/fav_tv
        sUriMatcher.addURI(AUTHORITY_TV, TABLE_NAME_02, FAVOURITE);
        // content://com.path_studio.submission_05.tv/fav_tv/id
        sUriMatcher.addURI(AUTHORITY_TV,
                TABLE_NAME_02 + "/#",
                FAVOURITE_ID);
    }

    public FavouriteTVProvider() {
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        favouriteHelper = FavouriteHelper.getInstance(getContext());
        favouriteHelper.open();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case FAVOURITE:
                cursor = favouriteHelper.queryAllTV();
                break;
            case FAVOURITE_ID:
                cursor = favouriteHelper.queryTVById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        return cursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        long added;
        switch (sUriMatcher.match(uri)) {
            case FAVOURITE:
                added = favouriteHelper.insert(contentValues, "fav_tv");
                break;
            default:
                added = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI_02, null);
        return Uri.parse(CONTENT_URI_02 + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated = 0;
        getContext().getContentResolver().notifyChange(CONTENT_URI_02, null);
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case FAVOURITE_ID:
                deleted = favouriteHelper.deleteById(uri.getLastPathSegment(), "fav_tv");
                break;
            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI_02, null);
        return deleted;
    }
}
