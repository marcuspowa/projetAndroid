package com.example.remy.mmsongquizz.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.remy.mmsongquizz.R;

import utils.AsyncHttpRequest;
import utils.HttpUtils;
import utils.Logger;
import utils.Test;import dagger.ObjectGraph;
import utils.MMQuizzModule;
import services.TrackManager;
public class MainActivity extends BaseActivity {
///Commentaire
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String baseUrl = "http://api.music-story.com/oauth/request_token";
        String baseParams = "oauth_consumer_key=c9a6a97e3fe0f115120471c481190baa96649eea";
        Test test = new Test("GET",baseUrl,baseParams);
        String signature = test.getSignature();
        Logger.debug(signature);

		ObjectGraph objectGraph = ObjectGraph.create(new MMQuizzModule());
        TrackManager manager = objectGraph.get(TrackManager.class);
        HttpUtils httpUtils = objectGraph.get(HttpUtils.class);


        AsyncHttpRequest req = httpUtils.asyncRequest(baseUrl+"?"+baseParams+"&oauth_signature="+signature);

        String tmp = req.GetResult();
        Logger.debug(tmp);

    }
}
