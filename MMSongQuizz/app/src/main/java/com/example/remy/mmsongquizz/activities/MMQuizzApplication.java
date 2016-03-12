package com.example.remy.mmsongquizz.activities;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by remy on 12/03/2016.
 */
public class MMQuizzApplication extends Application {

    public static final String NAME = "MMSongQuizz";



    public void notify(String msg){
        // Toast : affichage sur l'ecran
        Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        t.show();
    }

    public void log(String msg){
        Log.i(NAME, msg);
    }
}
