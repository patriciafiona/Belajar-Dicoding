package com.path_studio.submission4.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    /*
    Tanggung jawab utama dari kelas di atas adalah menciptakan database dengan tabel yang dibutuhkan
    dan handle ketika terjadi perubahan skema pada tabel (terjadi pada metode onUpgrade()).
    */

    public static String DATABASE_NAME = "movie_catalogue_app";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_NAME,
            DatabaseContract.FavouriteColumns._ID,
            DatabaseContract.FavouriteColumns.TYPE,
            DatabaseContract.FavouriteColumns.TITLE,
            DatabaseContract.FavouriteColumns.DESCRIPTION,
            DatabaseContract.FavouriteColumns.RATTING
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
        onCreate(db);
    }

}
