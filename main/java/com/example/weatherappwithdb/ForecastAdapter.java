/*
    Cole Howell, Manoj Bompada
    ITCS 4180
    ForecastAdapter.java
 */

package com.example.weatherappwithdb;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ForecastAdapter extends ArrayAdapter {
    Context mContext;
    int mResource;
    ArrayList<Forecast> mObjects;
    static DatabaseDataManager dm;

    public ForecastAdapter(Context context, int resource, ArrayList objects) {

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
            holder.time = (TextView) convertView.findViewById(R.id.time1);
            holder.cond = (TextView) convertView.findViewById(R.id.condition);
            holder.temp = (TextView) convertView.findViewById(R.id.temptxt);
            holder.txtimage = (ImageView) convertView.findViewById(R.id.adapterimageView);
            holder.notesimg = (ImageView) convertView.findViewById(R.id.noteimg);

            convertView.setTag(holder);
        }
        holder = (Holder) convertView.getTag();
        TextView time = holder.time;
        TextView cond = holder.cond;
        TextView temp = holder.temp;
        ImageView txtimage = holder.txtimage;
        ImageView notesImage = holder.notesimg;
        try {
            time.setText(mObjects.get(position).getDate());
            cond.setText(mObjects.get(position).getClouds());
            temp.setText(mObjects.get(position).getHightemp() + "°F" + "/" + mObjects.get(position).getLowTemp() + "°F");

            if (dm.getAllNotes().equals(position)) {
                notesImage.setImageResource(R.drawable.notes);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        Picasso.with(mContext).load(mObjects.get(position).getIconUrl()).into(txtimage);
        return convertView;
    }

    private class Holder {
        TextView time;
        TextView cond;
        TextView temp;
        ImageView txtimage,notesimg;

    }
}
