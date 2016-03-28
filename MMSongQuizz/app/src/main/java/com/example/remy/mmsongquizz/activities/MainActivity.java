package com.example.remy.mmsongquizz.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.remy.mmsongquizz.R;

import java.util.HashMap;

import services.MusicStoryTokenManager;
import utils.AsyncHttpRequest;
import utils.HttpUtils;
import utils.Logger;
import utils.MusicStoryEncryption;
import dagger.ObjectGraph;
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

        MusicStoryTokenManager tokenManager = application.getContainer().get(MusicStoryTokenManager.class);
        tokenManager.getToken();

        String baseUrl = "http://api.music-story.com/artist/search";
        HashMap<String,String> param = new HashMap<>();
        param.put("name", "Bob");
        String signedUrl= MusicStoryEncryption.signUrl(baseUrl,param);
        Logger.debug("signedUrl" + signedUrl);

        HttpUtils httpUtils = application.getContainer().get(HttpUtils.class);

        AsyncHttpRequest req = httpUtils.asyncRequest(signedUrl);

        String requestResult = req.GetResult();
        Logger.debug("result: " + requestResult);
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
