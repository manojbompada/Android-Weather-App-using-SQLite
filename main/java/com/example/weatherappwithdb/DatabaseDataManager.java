/*
    Cole Howell, Manoj Bompada
    ITCS 4180
    DatabaseDataManager.java
 */

package com.example.weatherappwithdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseDataManager {
    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private CitiesDAO citiesDAO;
    private NotesDAO notesDAO;

    public DatabaseDataManager(Context mContext){
        this.mContext = mContext;
        dbOpenHelper = new DatabaseOpenHelper(this.mContext);
        db = dbOpenHelper.getWritableDatabase();

        if(db ==null)
            dbOpenHelper.onCreate(db);
        citiesDAO = new CitiesDAO(db);
        notesDAO = new NotesDAO(db);
    }

    public void close(){
            if (db != null){
                db.close();
        }
    }

    public CitiesDAO getCitiesDAO(){
        return this.citiesDAO;
    }

    public NotesDAO getNotesDAO(){
        return this.notesDAO;
    }

    public long saveCity(City city){
        return this.citiesDAO.save(city);
    }

    public long saveNotes(Notes notes){
        return this.notesDAO.save(notes);
    }

    public boolean deleteCity(City city){
        return this.citiesDAO.delete(city);
    }

    public boolean deleteNotes(Notes notes){
        return this.notesDAO.delete(notes);
    }

    public City getCity(String cityname){
        return this.citiesDAO.get(cityname);
    }

    public Notes getNotes(String citykey){
        return this.notesDAO.get(citykey);
    }

    public ArrayList<City> getAllCities(){
        return this.citiesDAO.getAll();
    }

    public ArrayList<Notes> getAllNotes(){
        return this.notesDAO.getAll();
    }

    public void deleteAllCities(){
        this.citiesDAO.deleteAllCities();
    }

    public void deleteAllNotes(){
        this.notesDAO.deleteAllNotes();
    }
}
