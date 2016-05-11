/*
    Cole Howell, Manoj Bompada
    ITCS 4180
    CitiesDAO.java
 */

package com.example.weatherappwithdb;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CitiesDAO {

    private SQLiteDatabase db;

    public CitiesDAO(SQLiteDatabase db){
        this.db = db;

    }

    public long save(City city){

        ContentValues values = new ContentValues();
        values.put(CitiesTable.CITY_KEY, city.getCityKey());
        values.put(CitiesTable.CITY_NAME, city.getCityName());
        values.put(CitiesTable.CITY_STATE, city.getState());

        return db.insert(CitiesTable.CITY_TABLE,null,values);

    }

    public boolean update(City city){
        ContentValues values = new ContentValues();
        values.put(CitiesTable.CITY_KEY, city.getCityKey());
        values.put(CitiesTable.CITY_NAME, city.getCityName());
        values.put(CitiesTable.CITY_STATE, city.getState());

        return db.update(CitiesTable.CITY_TABLE, values, CitiesTable.CITY_KEY+ "=?", new String[]{city.getCityKey()+""})>0;

    }

    public boolean delete(City city){

        return db.delete(CitiesTable.CITY_TABLE, CitiesTable.CITY_KEY + "=?", new String[]{city.getCityKey() + ""})>0;
    }

    public void deleteAllCities(){

        db.execSQL("delete from " + CitiesTable.CITY_TABLE);
    }

    public City get(String cityname){
        City city = null;
        Cursor c = db.query(true, CitiesTable.CITY_TABLE, new String[]{
                CitiesTable.CITY_KEY,
                CitiesTable.CITY_NAME,
                CitiesTable.CITY_STATE

        }, CitiesTable.CITY_NAME + "=?", new String[]{cityname}, null, null, null, null, null);

        if(c != null && c.moveToFirst()){
            city = buildAppFromCursor(c);
            if(!c.isClosed()){
                c.close();
            }
        }

        return city;
    }

    public ArrayList<City> getAll(){

        ArrayList<City> cities = new ArrayList<City>();

        Cursor c = db.query(CitiesTable.CITY_TABLE,
                new String[]{CitiesTable.CITY_KEY, CitiesTable.CITY_NAME, CitiesTable.CITY_STATE},null,null,null,null,null);

        if(c != null && c.moveToFirst()){

            do{
                City city = buildAppFromCursor(c);
                if(city != null){
                    cities.add(city);
                }
            }while(c.moveToNext());

            if(!c.isClosed()){
                c.close();
            }
        }
        return cities;

    }

    private City buildAppFromCursor(Cursor c){
        City city = null;
        if(c != null){
            city = new City();
            city.setCityKey(c.getString(0));
            city.setCityName(c.getString(1));
            city.setState(c.getString(2));

        }
        return city;
    }
}
