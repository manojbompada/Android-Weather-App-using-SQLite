/*
    Cole Howell, Manoj Bompada
    ITCS 4180
    CitytempUtil.java
 */

package com.example.weatherappwithdb;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Manoj on 3/19/2016.
 */
public class CitytempUtil {
    static public class CityJSONParser {
        public static City parseCity(String s) throws JSONException {
            City city = new City();

            JSONObject root = new JSONObject(s);
                JSONObject cityJSONObj = root.getJSONObject("current_observation");
                JSONObject citytempJSONObj = cityJSONObj.getJSONObject("display_location");

                String currTemp = cityJSONObj.getString("temp_f");
                String cityname = citytempJSONObj.getString("city");
                String citystate = citytempJSONObj.getString("state");

                city.setCityName(cityname.replaceAll(" ", "_"));
                city.setState(citystate);
                city.setCityCurrTemp(currTemp);
                Log.d("demo", "Current city temp = " + currTemp);
                return city;
        }
    }
}
