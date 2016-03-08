package com.example.remy.mmsongquizz.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.remy.mmsongquizz.R;

import dagger.ObjectGraph;
import utils.MMQuizzModule;
import services.TrackManager;

public class MainActivity extends AppCompatActivity {
///Commentaire
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ObjectGraph objectGraph = ObjectGraph.create(new MMQuizzModule());
        TrackManager manager = objectGraph.get(TrackManager.class);
    }
}
