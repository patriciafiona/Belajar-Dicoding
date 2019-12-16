package com.path_studio.submission4.Helper;

import android.database.Cursor;

import com.path_studio.submission4.Database.DatabaseContract;
import com.path_studio.submission4.Entity.Favourite;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<Favourite> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<Favourite> notesList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns._ID));
            int data_id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.DATA_ID));

            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.TITLE));
            String description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.DESCRIPTION));
            String poster = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.POSTER));
            double ratting = notesCursor.getDouble(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.RATTING));

            notesList.add(new Favourite(id, data_id, title, description, poster, ratting));
        }
        return notesList;
    }

}
