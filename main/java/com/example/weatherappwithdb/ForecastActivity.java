/*
    Cole Howell, Manoj Bompada
    ITCS 4180
    ForecastActivity.java
 */

package com.example.weatherappwithdb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForecastActivity extends AppCompatActivity {

    public static final String FORECTY ="forcast city" ;
    public static final String FOREST = "forcast state" ;
    public static final String FOREKEY = "forcast key";
    public static final String FOREWEATHERLST ="weather lst" ;
    public static final String FOREPOSITION ="position" ;
    ProgressDialog progressDialog;
    ListView forecatlv;
    TextView ctytxt;
    String city="",state="";
    public static DatabaseDataManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        dm = new DatabaseDataManager(this);

        forecatlv = (ListView) findViewById(R.id.weatherlistView);
        ctytxt = (TextView) findViewById(R.id.locationtxt);

        if(getIntent().getExtras()!= null){
            city = (String) getIntent().getExtras().getString(CityDataActivty.CITYDATANAME);
            state = (String) getIntent().getExtras().getString(CityDataActivty.CITYDATASTATE);
        }

        String API_URL = "http://api.wunderground.com/api/041713a953f1014f/forecast10day/q/"+state+"/"+city+".xml";
        Pattern p = Patterns.WEB_URL;
        Matcher m = p.matcher(API_URL );
        if(m.matches()){
            Log.d("demo", "URL MATCHES");
            new GetForcastWeatherAsyncTask().execute(API_URL);

        }
        else{
            Toast.makeText(ForecastActivity.this, "no cities match your query", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ForecastActivity.this, MainActivity.class);
            intent.putExtra(FORECTY,city);
            intent.putExtra(FOREST,state);
            intent.putExtra(FOREKEY,"forekey");
            startActivity(intent);
            finish();
        }
    }

    class GetForcastWeatherAsyncTask extends AsyncTask<String, Void, ArrayList<Forecast>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ForecastActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Loading Forecast Data");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<Forecast> result) {
            super.onPostExecute(result);
            final ArrayList<Forecast> forcastlst = result;
            if (result != null) {
                progressDialog.dismiss();
                ctytxt.setText(city.replaceAll("_"," ") + ", " + state);

                }

                ForecastAdapter adapter = new ForecastAdapter(ForecastActivity.this,R.layout.forecast_customlistview,forcastlst);
                forecatlv.setAdapter(adapter);
                adapter.setNotifyOnChange(true);

                forecatlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("demo", "Position : " + position + " value: " + forcastlst.get(position).toString());
                        Intent intent = new Intent(ForecastActivity.this, AddNotes.class);
                        intent.putExtra(FOREWEATHERLST, forcastlst);
                        intent.putExtra(FOREPOSITION, position);
                        intent.putExtra(FORECTY, city);
                        intent.putExtra(FOREST, state);
                        startActivity(intent);
                    }

                });


                forecatlv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
                       dm.deleteAllNotes();

                        Toast.makeText(ForecastActivity.this,"Note deleted successfully",Toast.LENGTH_SHORT).show();

                        Log.d("demo", String.valueOf(dm.getAllNotes()));

                        return true;
                    }
                });
            }

        @Override
        protected ArrayList<Forecast> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int statusCode = connection.getResponseCode();
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();

                    return ForecastParser.ForecastPullParser.parseForecast(inputStream);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
