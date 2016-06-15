package com.example.remy.mmsongquizz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.example.remy.mmsongquizz.R;


import models.Genre;
import models.User;
import services.ArtistManager;
import services.CacheManager;
import services.GenreManager;
import services.TrackManager;
import services.UserManager;
import utils.Logger;
import utils.SpotifyUtils;

public class MainActivity extends BaseActivity {

    private Button genrePrefsBtn;
    private Button logoutBtn;
    private Button startBtn;
    private Button leaderboardBtn;
    private TextView usernameField;
    private TextView pointsField;
    private TextView levelField;
    private UserManager userManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkNetwork();

        genrePrefsBtn = (Button) findViewById(R.id.main_genre_pref);
        logoutBtn = (Button) findViewById(R.id.main_logoutBtn);
        startBtn = (Button) findViewById(R.id.startBtn);
        leaderboardBtn = (Button) findViewById(R.id.main_leaderboardsBtn);
        usernameField = (TextView) findViewById(R.id.main_usernameField);
        pointsField = (TextView) findViewById(R.id.main_pointsField);
        levelField = (TextView) findViewById(R.id.main_levelField);


        userManager = application.getContainer().get(UserManager.class);
        CacheManager cacheManager = application.getContainer().get(CacheManager.class);
        SpotifyUtils spotifyUtils = application.getContainer().get(SpotifyUtils.class);

        spotifyUtils.fetchToken();

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
        Logger.debug("Current User initialisé");

        //if no prefered genres : redirect to genre selection
        if(userManager.getCurrentUser().getPreferedGenres().size() == 0){
            application.notify("Veuillez sélectionner vos genres préférés");
            Intent toGenrePrefs = new Intent(MainActivity.this, GenreActivity.class);
            startActivity(toGenrePrefs);
        }

        initView();
    }



    private void initView(){
        User currentUser = userManager.getCurrentUser();
        usernameField.setText(currentUser.getName());
        pointsField.setText(currentUser.getPoints()+" pts");
        levelField.setText("lvl "+currentUser.getCurrentLevel());

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userManager.setCurrentUser(null);
                userManager.setStayConnected(false);
                Intent toLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(toLogin);
            }
        });
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
        leaderboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLeaderboard = new Intent(MainActivity.this, LeaderboardActivity.class);
                startActivity(toLeaderboard);
            }
        });
    }


}
