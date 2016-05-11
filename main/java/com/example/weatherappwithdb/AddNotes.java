/*
    Cole Howell, Manoj Bompada
    ITCS 4180
    AddNotes.java
 */

package com.example.weatherappwithdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddNotes extends AppCompatActivity {

    ArrayList<Forecast> forecastArrayList = new ArrayList<Forecast>();
    int position;
    String citynm;
    String state;
    EditText notetxt;
    Button savebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        setTitle("Add Notes Activity");

        if(getIntent().getExtras()!= null){
            forecastArrayList = (ArrayList<Forecast>) getIntent().getExtras().getSerializable(ForecastActivity.FOREWEATHERLST);
            position = getIntent().getExtras().getInt(ForecastActivity.FOREPOSITION);
            citynm = (String) getIntent().getExtras().getString(ForecastActivity.FORECTY);
            state = (String) getIntent().getExtras().getString(ForecastActivity.FOREST);
        }

        notetxt = (EditText) findViewById(R.id.noteText);
        savebtn = (Button) findViewById(R.id.saveNote);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                City cityobj = MainActivity.dm.getCity(citynm);

                Notes notes = new Notes();
                notes.setNote(notetxt.getText().toString());
                notes.setDate(forecastArrayList.get(position).getDate());
                notes.setCityKey(cityobj.getCityKey());
                MainActivity.dm.saveNotes(notes);

                Toast.makeText(AddNotes.this,"Note Saved Successfully",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                finish();
            }
        });


    }
}
