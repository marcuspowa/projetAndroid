package com.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import models.Genre;

/**
 * Created by remy on 02/06/2016.
 */
public class GenreAdapter extends ArrayAdapter<Genre> {
    private Context context;
    private int layoutResourceId;
    private Genre data[] = null;
    private ArrayList<Genre> preselected;

    public GenreAdapter(Context context, int layoutResourceId, Genre[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.preselected = preselected;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Genre currentGenre = getItem(position);
        View v = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_multiple_choice, null);
        TextView tv = (TextView) v.findViewById(android.R.id.text1);
        tv.setText(currentGenre.getName());


        return v;
    }

}
