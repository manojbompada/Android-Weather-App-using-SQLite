/*
    Cole Howell, Manoj Bompada
    ITCS 4180
    NotesDAO.java
 */

package com.example.weatherappwithdb;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class NotesDAO {

    private SQLiteDatabase db;

    public NotesDAO(SQLiteDatabase db){
        this.db = db;

    }

    public long save(Notes notes){

        ContentValues values = new ContentValues();
        values.put(NotesTable.CITY_KEY, notes.getCityKey());
        values.put(NotesTable.DATE, notes.getDate());
        values.put(NotesTable.NOTE, notes.getNote());


        return db.insert(NotesTable.Notes_TABLE,null,values);

    }

    public boolean update(Notes notes){
        ContentValues values = new ContentValues();
        values.put(NotesTable.CITY_KEY, notes.getCityKey());
        values.put(NotesTable.DATE, notes.getDate());
        values.put(NotesTable.NOTE, notes.getNote());

        return db.update(NotesTable.Notes_TABLE, values, NotesTable.CITY_KEY+ "=?", new String[]{notes.getCityKey()+""})>0;

    }

    public boolean delete(Notes notes){

        return db.delete(NotesTable.Notes_TABLE, NotesTable.CITY_KEY + "=?", new String[]{notes.getCityKey() + ""})>0;
    }

    public void deleteAllNotes(){

        db.execSQL("delete from " + NotesTable.Notes_TABLE);
    }

    public Notes get(String cityKey){
        Notes notes = null;
        Cursor c = db.query(true, NotesTable.Notes_TABLE, new String[]{
                NotesTable.CITY_KEY,
                NotesTable.DATE,
                NotesTable.NOTE
        }, NotesTable.CITY_KEY + "=?", new String[]{cityKey}, null, null, null, null, null);

        if(c != null && c.moveToFirst()){
            notes = buildAppFromCursor(c);
            if(!c.isClosed()){
                c.close();
            }
        }

        return notes;
    }

    public ArrayList<Notes> getAll(){

        ArrayList<Notes> noteslst = new ArrayList<Notes>();

        Cursor c = db.query(NotesTable.Notes_TABLE,
                new String[]{NotesTable.CITY_KEY, NotesTable.DATE, NotesTable.NOTE},null,null,null,null,null);

        if(c != null && c.moveToFirst()){

            do{
                Notes notes = buildAppFromCursor(c);
                if(notes != null){
                    noteslst.add(notes);
                }
            }while(c.moveToNext());

            if(!c.isClosed()){
                c.close();
            }
        }
        return noteslst;

    }

    private Notes buildAppFromCursor(Cursor c){
        Notes notes = null;
        if(c != null){
            notes = new Notes();
            notes.setCityKey(c.getString(0));
            notes.setDate(c.getString(1));
            notes.setNote(c.getString(2));

        }
        return notes;
    }
}
