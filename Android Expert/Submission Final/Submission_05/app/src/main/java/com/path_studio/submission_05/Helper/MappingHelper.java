package com.path_studio.submission_05.Helper;

import android.database.Cursor;

import com.path_studio.submission_05.Database.DatabaseContract;
import com.path_studio.submission_05.Entity.Favourite;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<Favourite> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<Favourite> favouritesList = new ArrayList<>();

        if(notesCursor != null){

            while (notesCursor.moveToNext()) {
                int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns._ID));
                int data_id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.DATA_ID));

                String type = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.TYPE));
                String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.TITLE));
                String poster = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.POSTER));
                double ratting = notesCursor.getDouble(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.RATTING));

                favouritesList.add(new Favourite(id, data_id, type, title, poster, ratting));
            }

        }

        return favouritesList;
    }

    public static Favourite mapCursorToObject(Cursor notesCursor) {
        notesCursor.moveToFirst();
        int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns._ID));
        int data_id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.DATA_ID));

        String type = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.TYPE));
        String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.TITLE));
        String poster = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.POSTER));
        double ratting = notesCursor.getDouble(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.RATTING));

        return new Favourite(id, data_id, type, title, poster, ratting);
    }

}
