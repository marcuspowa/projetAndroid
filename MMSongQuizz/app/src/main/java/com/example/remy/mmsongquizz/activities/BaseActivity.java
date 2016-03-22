package com.example.remy.mmsongquizz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by remy on 12/03/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected MMQuizzApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.application = (MMQuizzApplication) getApplication();
    }


    protected void checkNetwork(){
        boolean networkAvailable = application.networkIsAvailable();
        if(!networkAvailable){
            Intent toNetworkError = new Intent(this, NetworkErrorActivity.class);
            startActivity(toNetworkError);
        }
    }

}
