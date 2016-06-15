package com.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import models.Genre;
import models.User;

/**
 * Created by remy on 02/06/2016.
 */
public class UserAdapter extends ArrayAdapter<User> {
    private Context context;
    private int layoutResourceId;
    private User data[] = null;

    public UserAdapter(Context context, int layoutResourceId, User[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User currentUser = getItem(position);
        View v = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, null);
        TextView tv = (TextView) v.findViewById(android.R.id.text1);
        TextView tvPoints = (TextView) v.findViewById(android.R.id.text2);
        tv.setText(currentUser.getName());
        tvPoints.setText(currentUser.getPoints()+" pts");


        return v;
    }

}
