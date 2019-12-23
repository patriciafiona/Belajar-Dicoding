package com.path_studio.submission_05.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.path_studio.submission_05.Database.DatabaseContract.TABLE_NAME_01;
import static com.path_studio.submission_05.Database.DatabaseContract.TABLE_NAME_02;

public class FavouriteHelper {

    private static final String DATABASE_TABLE_MOVIE = TABLE_NAME_01;
    private static final String DATABASE_TABLE_TV = TABLE_NAME_02;
    private static DatabaseHelper dataBaseHelper;
    private static FavouriteHelper INSTANCE;
    public static SQLiteDatabase database;

    private static final String DATA_ID_COl = "data_id";
    private boolean status_data = false;

    private FavouriteHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FavouriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavouriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }
    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public Cursor queryAllMovie() {
        if(database.isOpen()){
            return database.query(
                    DATABASE_TABLE_MOVIE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    _ID + " ASC");
        }else{
            open();
            return database.query(
                    DATABASE_TABLE_MOVIE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    _ID + " ASC");
        }

    }

    public Cursor queryAllTV() {
        if(database.isOpen()){
            return database.query(
                    DATABASE_TABLE_TV,
                    null,
                    null,
                    null,
                    null,
                    null,
                    _ID + " ASC");
        }else{
            open();
            return database.query(
                    DATABASE_TABLE_TV,
                    null,
                    null,
                    null,
                    null,
                    null,
                    _ID + " ASC");
        }
    }

    public long insert(ContentValues values, String DATABASE_TABLE) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteById(String id, String DATABASE_TABLE) {
        return database.delete(DATABASE_TABLE, " data_id = ?", new String[]{id});
    }

    public boolean selectById(int dataId, String DATABASE_TABLE){
        Cursor c = database.rawQuery("select * from " + DATABASE_TABLE + " where " + DATA_ID_COl + "='" + dataId + "'" , null);

        if (!(c.moveToFirst())) {
            status_data = false;
        }else{
            status_data = true;
        }

        c.close();

        return status_data;
    }

    public ArrayList selectData(String DATABASE_TABLE){
        ArrayList array_list = new ArrayList<>();
        array_list.clear();

        Cursor c = database.rawQuery("select * from " + DATABASE_TABLE + "", null);
        c.moveToFirst();

        while(c.isAfterLast() == false) {
            array_list.add(c.getString(c.getColumnIndex("poster")));
            c.moveToNext();
        }
        return array_list;
    }

}
