/*
    Cole Howell, Manoj Bompada
    ITCS 4180
    NotesDAO.java
 */

package com.example.weatherappwithdb;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class NotesTable {
    static final String Notes_TABLE = "notes";
    static final String CITY_KEY = "citykey";
    static final String DATE = "date";
    static final String NOTE = "note";


    static public void onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + Notes_TABLE+ " (");
        sb.append(CITY_KEY + " TEXT primary key, ");
        sb.append(DATE + " TEXT , ");
        sb.append(NOTE + " TEXT );");

        try {
            db.execSQL(sb.toString());
        } catch (SQLiteException ex){
            ex.printStackTrace();
        }
    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + Notes_TABLE);
        NotesTable.onCreate(db);
    }
}
