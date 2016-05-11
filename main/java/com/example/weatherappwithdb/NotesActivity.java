/*
    Cole Howell, Manoj Bompada
    ITCS 4180
    NotesActivty.java
 */

package com.example.weatherappwithdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {

    ListView notesView;
    public static DatabaseDataManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        setTitle("Notes Activity");

        dm = new DatabaseDataManager(this);

        notesView = (ListView) findViewById(R.id.notesListView);

        NotesAdapter notesAdapter = new NotesAdapter(this, R.layout.activity_notes_layout, dm.getAllNotes());
        notesView.setAdapter(notesAdapter);
    }
}
