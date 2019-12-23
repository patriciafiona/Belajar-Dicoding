package com.path_studio.submission_05.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.path_studio.submission_05.Database.FavouriteHelper;

import androidx.annotation.NonNull;

import static com.path_studio.submission_05.Database.DatabaseContract.AUTHORITY_MOVIE;
import static com.path_studio.submission_05.Database.DatabaseContract.FavouriteColumns.CONTENT_URI_01;
import static com.path_studio.submission_05.Database.DatabaseContract.TABLE_NAME_01;

public class FavouriteMovieProvider extends ContentProvider {

    private static final int FAVOURITE = 1;
    private static final int FAVOURITE_ID = 11;
    private FavouriteHelper favouriteHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // content://com.path_studio.submission_05.MOVIE/fav_movie
        sUriMatcher.addURI(AUTHORITY_MOVIE, TABLE_NAME_01, FAVOURITE);
        // content://com.path_studio.submission_05.MOVIE/fav_movie/id
        sUriMatcher.addURI(AUTHORITY_MOVIE,
                TABLE_NAME_01 + "/#",
                FAVOURITE_ID);
    }

    public FavouriteMovieProvider() {
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
                cursor = favouriteHelper.queryAllMovie();
                break;
            case FAVOURITE_ID:
                cursor = favouriteHelper.queryMovieById(uri.getLastPathSegment());
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
                added = favouriteHelper.insert(contentValues, "fav_movie");
                break;
            default:
                added = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI_01, null);
        return Uri.parse(CONTENT_URI_01 + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated = 0;
        getContext().getContentResolver().notifyChange(CONTENT_URI_01, null);
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case FAVOURITE_ID:
                deleted = favouriteHelper.deleteById(uri.getLastPathSegment(), "fav_movie");
                break;
            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI_01, null);
        return deleted;
    }

}
