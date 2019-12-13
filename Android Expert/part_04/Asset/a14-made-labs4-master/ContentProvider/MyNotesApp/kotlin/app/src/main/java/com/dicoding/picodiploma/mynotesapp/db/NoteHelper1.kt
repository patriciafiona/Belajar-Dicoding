package com.dicoding.picodiploma.mynotesapp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import com.dicoding.picodiploma.mynotesapp.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME

/**
 * Created by sidiqpermana on 11/23/16.
 */

class NoteHelper (context: Context) {
    private val dataBaseHelper: DatabaseHelper = DatabaseHelper(context)

    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: NoteHelper? = null

        fun getInstance(context: Context): NoteHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = NoteHelper(context)
                    }
                }
            }
            return INSTANCE as NoteHelper
        }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    /**
     * Ambil data dari semua note yang ada di dalam database
     * @return cursor hasil queryAll
     */
    fun queryAll(): Cursor {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                "$_ID DESC",
                null)
    }

    /**
     * Ambil data dari note berdasarakan parameter id
     * @param id id note yang dicari
     * @return cursor hasil queryAll
     */
    fun queryById(id: String): Cursor {
        return database.query(DATABASE_TABLE, null, "$_ID = ?", arrayOf(id), null, null, null, null)
    }

    /**
     * Simpan data ke dalam database
     *
     * @param values nilai data yang akan di simpan
     * @return long id dari data yang baru saja di masukkan
     */
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    /**
     * Update data dalam database
     *
     * @param id     data dengan id berapa yang akan di update
     * @param values nilai data baru
     * @return int jumlah data yang ter-update
     */
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    /**
     * Delete data dalam database
     *
     * @param id data dengan id berapa yang akan di delete
     * @return int jumlah data yang ter-delete
     */
    fun deleteById(id: String): Int {
        return database.delete(TABLE_NAME, "$_ID = '$id'", null)
    }
}


