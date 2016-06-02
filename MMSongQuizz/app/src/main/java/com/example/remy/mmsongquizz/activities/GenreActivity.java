package com.example.remy.mmsongquizz.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.remy.mmsongquizz.R;

import java.util.ArrayList;

import models.Genre;
import services.CacheManager;
import services.GenreManager;
import services.UserManager;
import utils.Logger;

public class GenreActivity extends BaseActivity {

    private GenreManager genreManager;
    private UserManager userManager;

    private Button homeBtn;
    private Button submitBtn;
    private ListView genreListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        genreManager = application.getContainer().get(GenreManager.class);
        userManager = application.getContainer().get(UserManager.class);

        homeBtn = (Button) findViewById(R.id.genre_home_btn);
        submitBtn = (Button) findViewById(R.id.genre_submit);
        genreListView = (ListView) findViewById(R.id.genre_list);

        initView();
    }

    private void initView(){
        ArrayList<Genre> genres = genreManager.getAll();

        genreListView.setAdapter(new GenreAdapter(this, android.R.layout.simple_list_item_multiple_choice, genres.toArray(new Genre[genres.size()])));

        for (int i = 0; i < genreListView.getAdapter().getCount(); i++) {
            Genre genreInList = (Genre)genreListView.getAdapter().getItem(i);
            for(Genre genre : userManager.getCurrentUser().getPreferedGenres()){
                if(genreInList.getName().equals(genre.getName())){
                    genreListView.setItemChecked(i, true);
                    break;
                }
            }
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checked = genreListView.getCheckedItemPositions();
                ArrayList<Genre> selectedGenres = getSelectedGenres();
                userManager.getCurrentUser().setPreferedGenres(selectedGenres);
                userManager.setCurrentUser(userManager.getCurrentUser(), true);
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHome = new Intent(GenreActivity.this, MainActivity.class);
                startActivity(toHome);
            }
        });


    }

    private ArrayList<Genre> getSelectedGenres(){
        ArrayList<Genre> selectedGenres = new ArrayList<Genre>();
        SparseBooleanArray checked = genreListView.getCheckedItemPositions();
        for (int i = 0; i < genreListView.getAdapter().getCount(); i++) {
            if (checked.get(i)) {
                Genre genre = (Genre)genreListView.getAdapter().getItem(i);
                Logger.debug("CHECKED " + genre.getName());
                selectedGenres.add(genre);
            }
        }
        return selectedGenres;
    }

}
class GenreAdapter extends ArrayAdapter<Genre>{
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
