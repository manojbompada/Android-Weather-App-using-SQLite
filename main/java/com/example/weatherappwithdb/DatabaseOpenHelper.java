/*
    Cole Howell, Manoj Bompada
    ITCS 4180
    DatabaseOpenHelper.java
 */

package com.example.weatherappwithdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper{
    static final String DB_NAME = "weather.db";
    static final int DB_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        CitiesTable.onCreate(db);
        NotesTable.onCreate(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        CitiesTable.onUpgrade(db,oldVersion,newVersion);
        NotesTable.onUpgrade(db,oldVersion,newVersion);
    }
}
