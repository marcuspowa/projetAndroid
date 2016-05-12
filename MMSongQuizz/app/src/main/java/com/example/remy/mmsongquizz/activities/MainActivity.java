package com.example.remy.mmsongquizz.activities;

import android.os.Bundle;


import com.example.remy.mmsongquizz.R;

public class MainActivity extends AbstractSpotifyActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public void onTokenReceived() {
        this.startPlayer("spotify:track:0pLfOjfsw8E3id7eCpGCci");
    }


}