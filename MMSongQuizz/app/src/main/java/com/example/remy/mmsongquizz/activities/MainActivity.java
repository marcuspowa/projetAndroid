package com.example.remy.mmsongquizz.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.remy.mmsongquizz.R;

import utils.AsyncHttpRequest;
import utils.HttpUtils;
import utils.Test;import dagger.ObjectGraph;
import utils.MMQuizzModule;
import services.TrackManager;
public class MainActivity extends BaseActivity {
///Commentaire
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Test test = new Test("GET","http://api.music-story.com/oauth/request_token","oauth_consumer_key=c9a6a97e3fe0f115120471c481190baa96649eea");
        System.out.println("TATA"+test.getSignature());

		ObjectGraph objectGraph = ObjectGraph.create(new MMQuizzModule());
        TrackManager manager = objectGraph.get(TrackManager.class);

        HttpUtils httpUtils = objectGraph.get(HttpUtils.class);

        AsyncHttpRequest req = httpUtils.asyncRequest("http://www.google.fr");

        String tmp = req.GetResult();
        application.log(tmp);

    }
}
