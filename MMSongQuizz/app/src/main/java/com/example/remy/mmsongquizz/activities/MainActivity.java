package com.example.remy.mmsongquizz.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.remy.mmsongquizz.R;

import utils.AsyncHttpRequest;
import utils.HttpUtils;
import utils.Logger;
import utils.Test;import dagger.ObjectGraph;
import utils.MMQuizzModule;
import services.TrackManager;
public class MainActivity extends BaseActivity {
    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkNetwork();

        startBtn = (Button) findViewById(R.id.startBtn);

        initView();


        String baseUrl = "http://api.music-story.com/oauth/request_token";
        String baseParams = "oauth_consumer_key=c9a6a97e3fe0f115120471c481190baa96649eea";
        Test test = new Test("GET",baseUrl,baseParams);
        String signature = test.getSignature();
        Logger.debug(signature);

        TrackManager manager = application.getContainer().get(TrackManager.class);
        HttpUtils httpUtils = application.getContainer().get(HttpUtils.class);


        AsyncHttpRequest req = httpUtils.asyncRequest(baseUrl + "?" + baseParams + "&oauth_signature=" + signature);

        String tmp = req.GetResult();
        Logger.debug(tmp);

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
