package com.example.remy.mmsongquizz.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.remy.mmsongquizz.R;

import java.util.HashMap;

import utils.AsyncHttpRequest;
import utils.HttpUtils;
import utils.Logger;
import utils.MusicStoryEncryption;
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

        // genere Access Token
        String baseUrl = "http://api.music-story.com/oauth/request_token";
        HashMap<String,String> param = new HashMap<>();
        param.put("oauth_consumer_key", MusicStoryEncryption.consumerKey);
        String signedUrl= MusicStoryEncryption.signUrl(baseUrl,param,"");


        Logger.debug(signedUrl);

        TrackManager manager = application.getContainer().get(TrackManager.class);
        HttpUtils httpUtils = application.getContainer().get(HttpUtils.class);

        AsyncHttpRequest req = httpUtils.asyncRequest(signedUrl);

        String tmp = req.GetResult();
        Logger.debug("resulta :"+tmp);

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
