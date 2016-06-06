package com.example.remy.mmsongquizz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import com.example.remy.mmsongquizz.R;


import models.User;
import services.ArtistManager;
import services.CacheManager;
import services.GenreManager;
import services.TrackManager;
import services.UserManager;

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


        GenreManager genreManager = application.getContainer().get(GenreManager.class);
        UserManager userManager = application.getContainer().get(UserManager.class);
        ArtistManager artistManager = application.getContainer().get(ArtistManager.class);
        TrackManager trackManager = application.getContainer().get(TrackManager.class);
        CacheManager cacheManager = application.getContainer().get(CacheManager.class);



        if(userManager.getCurrentUser() == null) {
            if (!cacheManager.exists(MMQuizzApplication.getContext(), UserManager.UserCacheKey)) {
                User userTest = new User();
                userTest.setId(1);
                userTest.setName("toto");
                userManager.setCurrentUser(userTest);
            } else {
                userManager.setCurrentUser(userManager.getCurrentUser(true));
            }
        }

//        userManager.updateUser(userManager.getCurrentUser());

//        userManager.getAll();

        initView();
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
