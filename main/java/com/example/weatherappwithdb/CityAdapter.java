/*
    Cole Howell, Manoj Bompada
    ITCS 4180
    CityAdapter.java
 */

package com.example.weatherappwithdb;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CityAdapter extends ArrayAdapter {

    Context mContext;
    int mResource;
    ArrayList<City> mObjects;

    public CityAdapter(Context context, int resource, ArrayList objects) {

        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mObjects = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new Holder();
            holder.city = (TextView) convertView.findViewById(R.id.cityname);
            holder.currtemp = (TextView) convertView.findViewById(R.id.temp);

            convertView.setTag(holder);
        }
        holder = (Holder) convertView.getTag();
        TextView city = holder.city;
        TextView temp = holder.currtemp;
        city.setText(mObjects.get(position).getCityName().replaceAll("_"," ") + ", " + mObjects.get(position).getState());
        Log.d("demo", "curr temp in adapter= " + mObjects.get(position).getCityCurrTemp());
        temp.setText(mObjects.get(position).getCityCurrTemp()+" °F");
        return convertView;
    }

    private class Holder {
        TextView city;
        TextView currtemp;
    }
}