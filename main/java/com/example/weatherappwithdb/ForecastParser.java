/*
    Cole Howell, Manoj Bompada
    ITCS 4180
    ForecastParser.java
 */

package com.example.weatherappwithdb;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class ForecastParser {
    public static class ForecastPullParser {

        public static ArrayList<Forecast> parseForecast(InputStream inputStream) throws XmlPullParserException, IOException {

            boolean hflag= false;
            boolean lflag=false;
            boolean mwindflag = false;
            boolean simpleforecastflag = false;
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(inputStream, "UTF-8");
            Forecast forecast = null;
            ArrayList<Forecast> forcastList = new ArrayList<Forecast>();
            int event = parser.getEventType();

          try{
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        try{

                           if(parser.getName().equals("forecastday")){
                                forecast = new Forecast();
                            }
                            else if (parser.getName().equals("pretty")){
                                String datetime = parser.nextText().trim();
                                String[] datearray = datetime.split(" ");
                                String mnth = datearray[4];
                                String datecomma = datearray[5];
                                String dt = datecomma.substring(0,2);

                                forecast.setDate(dt+" "+mnth);
                            }
                            else if (parser.getName().equals("high")){
                                hflag = true;
                            }
                            else if (parser.getName().equals("low")){
                                lflag = true;
                            }
                            else if(parser.getName().equals("fahrenheit")){
                                if(hflag == true){
                                    hflag = false;
                                    forecast.setHightemp(parser.nextText().trim());
                                }
                                if(lflag == true) {
                                    lflag = false;
                                    forecast.setLowTemp(parser.nextText().trim());
                                }
                            }
                            else if (parser.getName().equals("conditions")){
                                forecast.setClouds(parser.nextText().trim());
                            }
                            else if (parser.getName().equals("icon_url")){
                                forecast.setIconUrl(parser.nextText().trim());
                            }
                            else if (parser.getName().equals("maxwind")){
                                mwindflag = true;
                            }
                            else if (parser.getName().equals("mph")){
                                if(mwindflag == true) {
                                    forecast.setMaxwindSpeed(parser.nextText().trim());
                                }
                            }

                            else if (parser.getName().equals("dir")){
                                if(mwindflag == true) {
                                    mwindflag = false;
                                    forecast.setWindDirection(parser.nextText().trim());
                                }

                            }
                            else if (parser.getName().equals("avehumidity")){
                                forecast.setAvghumidity(parser.nextText().trim());
                            }

                        }catch(Exception e){
                            e.printStackTrace();
                    }
                        break;

                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("forecastday")){
                            hflag= false;
                            lflag=false;
                            mwindflag = false;
                            if(!forecast.getDate().equals(""))
                            forcastList.add(forecast);
                            forecast=null;
                        }
                        break;
                    default:
                        break;
                }
                event = parser.next();
            }
            return forcastList;
        }catch(Exception e){

              e.printStackTrace();
              return null;
          }
        }
    }
}

