package com.example.remy.mmsongquizz.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.remy.mmsongquizz.R;

import java.util.ArrayList;

import models.Genre;
import services.GenreManager;
import utils.Logger;

public class GenreActivity extends BaseActivity {

    private GenreManager genreManager;

    private Button homeBtn;
    private Button submitBtn;
    private ListView genreListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        genreManager = application.getContainer().get(GenreManager.class);

        homeBtn = (Button) findViewById(R.id.genre_home_btn);
        submitBtn = (Button) findViewById(R.id.genre_submit);
        genreListView = (ListView) findViewById(R.id.genre_list);

        initView();
    }

    private void initView(){
        ArrayList<Genre> genres = genreManager.getAll();

        genreListView.setAdapter(new GenreAdapter(this, android.R.layout.simple_list_item_multiple_choice, genres.toArray(new Genre[genres.size()])));


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checked = genreListView.getCheckedItemPositions();

                for (int i = 0; i < genreListView.getAdapter().getCount(); i++) {
                    if (checked.get(i)) {
                        Genre genre = (Genre)genreListView.getAdapter().getItem(i);
                        Logger.debug("CHECKED " + genre.getName());
                    }
                }

            }
        });


    }


}
class GenreAdapter extends ArrayAdapter<Genre>{
    Context context;
    int layoutResourceId;
    Genre data[] = null;

    public GenreAdapter(Context context, int layoutResourceId, Genre[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_multiple_choice, null);
        TextView tv = (TextView) v.findViewById(android.R.id.text1);
        tv.setText(getItem(position).getName());


        return v;
    }

}
