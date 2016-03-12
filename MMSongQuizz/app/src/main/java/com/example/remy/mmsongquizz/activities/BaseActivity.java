package com.example.remy.mmsongquizz.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by remy on 12/03/2016.
 */
public class BaseActivity extends AppCompatActivity {
    protected MMQuizzApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.application = (MMQuizzApplication) getApplication();
    }

}
