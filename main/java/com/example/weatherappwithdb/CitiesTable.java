/*
    Cole Howell, Manoj Bompada
    ITCS 4180
    CitiesTable.java
 */

package com.example.weatherappwithdb;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

/**
 * Created by Manoj on 3/18/2016.
 */
public class CitiesTable {

    static final String CITY_TABLE = "city";
    static final String CITY_KEY = "citykey";
    static final String CITY_NAME = "cityname";
    static final String CITY_STATE= "state";

    static public void onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + CITY_TABLE+ " (");
        sb.append(CITY_KEY + " TEXT primary key, ");
        sb.append(CITY_NAME + " TEXT , ");
        sb.append(CITY_STATE + " TEXT );");

        try {
            db.execSQL(sb.toString());
        } catch (SQLiteException ex){
            ex.printStackTrace();
        }
    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + CITY_TABLE);
        CitiesTable.onCreate(db);
    }
}
