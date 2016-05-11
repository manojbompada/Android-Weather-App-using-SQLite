/*
    Cole Howell, Manoj Bompada
    ITCS 4180
    CityDataActivity.java
 */

package com.example.weatherappwithdb;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class CityDataActivty extends TabActivity {
    public static final String CITYDATANAME = "cityname" ;
    public static final String CITYDATASTATE = "state" ;
    String city,state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_tab_data_activty);

        if(getIntent().getExtras()!= null){
            city = (String) getIntent().getExtras().getString(MainActivity.CITYNAME);
            state = (String) getIntent().getExtras().getString(MainActivity.STATE);
        }

        TabHost tabHost = getTabHost();

        TabHost.TabSpec hourly = tabHost.newTabSpec("HOURLYDATA");
        hourly.setIndicator("HOURLYDATA");
        Intent hourlyIntent = new Intent(this, HourlyDataActivity.class);
        hourlyIntent.putExtra(CITYDATANAME,city);
        hourlyIntent.putExtra(CITYDATASTATE, state);
        hourly.setContent(hourlyIntent);

        TabHost.TabSpec forecast = tabHost.newTabSpec("FORECAST");
        forecast.setIndicator("FORECAST");
        Intent forecastIntent = new Intent(this, ForecastActivity.class);
        forecastIntent.putExtra(CITYDATANAME,city);
        forecastIntent.putExtra(CITYDATASTATE, state);
        forecast.setContent(forecastIntent);

        tabHost.addTab(hourly);
        tabHost.addTab(forecast);
    }
}
