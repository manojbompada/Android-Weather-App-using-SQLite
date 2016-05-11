/*
    Cole Howell, Manoj Bompada
    ITCS 4180
    Notes.java
 */

package com.example.weatherappwithdb;

public class Notes {

    String cityKey,date, note;

    @Override
    public String toString() {
        return "Notes{" +
                "cityKey='" + cityKey + '\'' +
                ", date='" + date + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public String getCityKey() {
        return cityKey;
    }

    public void setCityKey(String cityKey) {
        this.cityKey = cityKey;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
