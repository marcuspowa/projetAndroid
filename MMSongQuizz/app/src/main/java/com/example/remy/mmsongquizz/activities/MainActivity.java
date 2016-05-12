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
public class MainActivity extends AbstractSpotifyActivity {

    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startBtn = (Button) findViewById(R.id.startBtn);

        initView();



        GenreManager genreManager = application.getContainer().get(GenreManager.class);
        ArtistManager artistManager = application.getContainer().get(ArtistManager.class);
        TrackManager trackManager = application.getContainer().get(TrackManager.class);
    }

    @Override
    public void onTokenReceived() {
        this.startPlayer("spotify:track:0pLfOjfsw8E3id7eCpGCci");
    }

    private void initView(){
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toQuestion = new Intent(MainActivity.this, QuestionActivity.class);
                startActivity(toQuestion);
            }
        });
    }


}