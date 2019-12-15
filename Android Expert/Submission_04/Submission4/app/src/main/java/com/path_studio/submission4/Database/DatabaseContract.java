package com.path_studio.submission4.Database;

import android.provider.BaseColumns;

public class DatabaseContract {

    /* Mempermudah akses nama tabel dan nama kolom di dalam database kita. */

    static String TABLE_NAME = "fav_movie";

    public static final class FavouriteColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String RATTING = "ratting";
        public static String POSTER = "poster";
        public static String TYPE = "type";
    }

}
