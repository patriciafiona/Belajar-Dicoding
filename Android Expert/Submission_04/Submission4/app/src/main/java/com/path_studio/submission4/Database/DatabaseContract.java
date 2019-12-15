package com.path_studio.submission4.Database;

import android.provider.BaseColumns;

public class DatabaseContract {

    /* Mempermudah akses nama tabel dan nama kolom di dalam database kita. */

    static String TABLE_NAME = "fav_movie";

    static final class FavouriteColumns implements BaseColumns {
        static String TITLE = "title";
        static String DESCRIPTION = "description";
        static String RATTING = "ratting";
        static String POSTER = "poster";
        static String TYPE = "type";
        static String BACKDROP = "backdrop";
    }

}
