/*
    Cole Howell, Manoj Bompada
    ITCS 4180
    NotesAdapter.java
 */

package com.example.weatherappwithdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NotesAdapter extends ArrayAdapter{

    Context mContext;
    int mResource;
    ArrayList<Notes> mObjects;

    public NotesAdapter(Context context, int resource, ArrayList objects){
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mObjects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        Holder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new Holder();
            holder.note = (TextView) convertView.findViewById(R.id.note);
            holder.date = (TextView) convertView.findViewById(R.id.date);

            convertView.setTag(holder);
        }

        holder = (Holder) convertView.getTag();
        TextView note = holder.note;
        TextView date = holder.date;
        note.setText(mObjects.get(position).getNote());
        date.setText(mObjects.get(position).getDate());

        return convertView;
    }

    private class Holder {
        TextView note, date;
    }
}
