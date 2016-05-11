/*
    Cole Howell, Manoj Bompada
    ITCS 4180
    MainActivity.java
 */

package com.example.weatherappwithdb;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String CITYLIST = "citylist";
    public static final String CITYNAME = "cityname";
    public static final String STATE = "state";
    String city,state;
    ArrayList<City> cListfromDB = new ArrayList<City>();
    ListView lv;
    TextView textView;
    public static DatabaseDataManager dm;
    CityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Homework 6");

        dm = new DatabaseDataManager(this);


        lv = (ListView) findViewById(R.id.listView);
        textView = (TextView) findViewById(R.id.textView);

        cListfromDB = dm.getAllCities();


        if(cListfromDB.size() == 0 )
        {
            textView.setText("There are no cities to display. Add using the \"Add City\" button from the options menu.");
        }
        else
        {
            String st = "";
            String mcity="";
            for(City x: cListfromDB){
                st = x.getState();
                mcity = x.getCityName();

            new GetCityCurrTempAsyncTask().execute("http://api.wunderground.com/api/041713a953f1014f/conditions/q/"+st+"/"+mcity+".json");
        }

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("demo", "Position : " + position + " value: " + cListfromDB.get(position).toString());

                    Intent intent = new Intent(MainActivity.this, CityDataActivty.class);
                    intent.putExtra(CITYNAME, cListfromDB.get(position).getCityName());
                    intent.putExtra(STATE, cListfromDB.get(position).getState());
                    intent.putExtra(CITYLIST, cListfromDB);
                    startActivity(intent);
                }
            });

            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    City city = cListfromDB.get(position);
                    dm.deleteCity(city);
                    cListfromDB.remove(position);
                    Toast.makeText(MainActivity.this,"City deleted successfully",Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    if(cListfromDB.size() == 0 )
                    {
                        textView.setText("There are no cities to display. Add using the \"Add City\" button from the options menu.");

                    }
                    adapter.notifyDataSetChanged();
                    return true;
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * On selecting action bar icons
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.Add_City:
                Intent intent1 = new Intent(MainActivity.this, AddCityActivity.class);
                intent1.putExtra(CITYLIST, cListfromDB);

                startActivity(intent1);
                return true;

            case R.id.Clear_City:

                if(cListfromDB.size() == 0 )
                {
                    Toast.makeText(MainActivity.this,"There are no Cities/Notes to delete",Toast.LENGTH_SHORT).show();
                }
                else{
                    dm.deleteAllCities();
                    dm.deleteAllNotes();
                    cListfromDB.clear();
                    adapter.notifyDataSetChanged();

                    Toast.makeText(MainActivity.this,"All cities and notes are deleted successfully",Toast.LENGTH_SHORT).show();
                    textView.setText("There are no cities to display. Add using the \"Add City\" button from the options menu.");

                }

                return true;

            case R.id.View_Note:
                if (cListfromDB.size() == 0){
                    Toast.makeText(MainActivity.this, "There are no cities to view notes. Add a city using the 'Add City' button from the options menu", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                    startActivity(intent);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class GetCityCurrTempAsyncTask extends AsyncTask<String, Void, City> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(City city) {
            Log.d("demo", "temp in post: " + city.getCityCurrTemp());
            Log.d("demo", "City from parse = " + city.getCityName());
            for(City x: cListfromDB){
                if(city.getCityName().equals(x.getCityName()) && city.getState().equals(x.getState()) ){

                    Log.d("demo","City from parse = "+city.getCityName()+" "+"City from list = "+x.getCityName());
                    x.setCityCurrTemp(city.getCityCurrTemp());
                }

            }
            adapter = new CityAdapter(MainActivity.this,R.layout.city_layout,cListfromDB);
            lv.setAdapter(adapter);
            adapter.setNotifyOnChange(true);
        }

        @Override
        protected City doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                Log.d("demo","URL ="+ url.toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int statusCode = connection.getResponseCode();
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    Log.d("demo", " HttpURLConnection.HTTP_OK   ");
                    StringBuilder sb = new StringBuilder();
                    String line = reader.readLine();
                    while (line != null) {
                        sb.append(line);
                        line = reader.readLine();
                    }

                    return CitytempUtil.CityJSONParser.parseCity(sb.toString());

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

