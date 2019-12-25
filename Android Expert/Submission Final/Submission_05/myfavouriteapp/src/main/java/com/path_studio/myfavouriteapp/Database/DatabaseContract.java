package com.path_studio.myfavouriteapp.Database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    /* Mempermudah akses nama tabel dan nama kolom di dalam database kita. */

    public static final String AUTHORITY_MOVIE = "com.path_studio.submission_05.movie";
    public static final String AUTHORITY_TV = "com.path_studio.submission_05.tv";
    private static final String SCHEME = "content";

    public static String TABLE_NAME_01 = "fav_movie";
    public static String TABLE_NAME_02 = "fav_tv";

    public static final class FavouriteColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String DATA_ID = "data_id";
        public static String RATTING = "ratting";
        public static String POSTER = "poster";
        public static String TYPE = "type";

        // untuk membuat URI content://com.path_studio.myfavouriteapp.movie/fav_movie
        public static final Uri CONTENT_URI_01 = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY_MOVIE)
                .appendPath(TABLE_NAME_01)
                .build();

        // untuk membuat URI content://com.path_studio.myfavouriteapp.tv/fav_tv
        public static final Uri CONTENT_URI_02 = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY_TV)
                .appendPath(TABLE_NAME_02)
                .build();

    }

}
