package com.example.remy.mmsongquizz.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.remy.mmsongquizz.R;

public class NetworkErrorActivity extends BaseActivity{
    private Button retryBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_error);

        retryBtn = (Button) findViewById(R.id.RetryBtn);

        initView();
    }



    public void initView(){
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean networkAvailable = application.networkIsAvailable();
                if(!networkAvailable){
                    application.notify("Auncun réseau détécté");
                }
                else{
                    Intent toMain = new Intent(NetworkErrorActivity.this, MainActivity.class);
                    startActivity(toMain);
                }
            }
        });
    }
}
