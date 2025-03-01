package com.path_studio.submission4.Database;

import android.provider.BaseColumns;

public class DatabaseContract {

    /* Mempermudah akses nama tabel dan nama kolom di dalam database kita. */

    public static String TABLE_NAME_01 = "fav_movie";
    public static String TABLE_NAME_02 = "fav_tv";

    public static final class FavouriteColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String DATA_ID = "data_id";
        public static String RATTING = "ratting";
        public static String POSTER = "poster";
        public static String TYPE = "type";
    }

}
