package com.example.remy.mmsongquizz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.remy.mmsongquizz.R;

import java.util.ArrayList;
import java.util.HashMap;


import models.Artist;
import models.Genre;
import models.Track;
import services.ArtistManager;
import services.GenreManager;
import services.TrackManager;
import utils.AsyncHttpRequest;
import utils.EchonestUtils;
import utils.HttpUtils;
import utils.Logger;
public class MainActivity extends BaseActivity {

    private Button genrePrefsBtn;
    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkNetwork();

        genrePrefsBtn = (Button) findViewById(R.id.main_genre_pref);
        startBtn = (Button) findViewById(R.id.startBtn);

        initView();



        GenreManager genreManager = application.getContainer().get(GenreManager.class);
        ArtistManager artistManager = application.getContainer().get(ArtistManager.class);
        TrackManager trackManager = application.getContainer().get(TrackManager.class);
    }



    private void initView(){
        genrePrefsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toGenrePrefs = new Intent(MainActivity.this, GenreActivity.class);
                startActivity(toGenrePrefs);
            }
        });
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toQuestion = new Intent(MainActivity.this, QuestionActivity.class);
                startActivity(toQuestion);
            }
        });
    }


}