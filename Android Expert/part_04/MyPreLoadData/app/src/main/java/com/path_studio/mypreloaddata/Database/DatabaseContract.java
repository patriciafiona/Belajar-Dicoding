package com.path_studio.mypreloaddata.Database;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_NAME = "table_mahasiswa";
    static final class MahasiswaColumns implements BaseColumns {
        static String NAMA = "nama";
        static String NIM = "nim";
    }
}
