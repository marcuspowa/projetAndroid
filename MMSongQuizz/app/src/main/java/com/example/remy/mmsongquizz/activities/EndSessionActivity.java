package com.example.remy.mmsongquizz.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.remy.mmsongquizz.R;

import models.User;
import services.UserManager;

/**
 * Created by Mickael on 02/06/2016.
 */
public class EndSessionActivity extends BaseActivity {
    public static final String NbPointsEarnedParamName = "nbPointsEarned";
    private Button homeBtn;
    private UserManager userManager;
    private TextView nbpointsearnedLabel;
    private int nbPointsEarned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endsession);

        checkNetwork();

        homeBtn = (Button) findViewById(R.id.endsession_home_btn);
        nbpointsearnedLabel = (TextView) findViewById(R.id.endsession_nbpointsearnedLabel);

        userManager = application.getContainer().get(UserManager.class);

        Intent intent = getIntent();
        nbPointsEarned = intent.getIntExtra(NbPointsEarnedParamName, 0);

        if(nbPointsEarned>0){
            updateUser(nbPointsEarned);
        }

        initView();
    }



    public void initView(){

        nbpointsearnedLabel.setText(nbPointsEarned+" points");

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHome = new Intent(EndSessionActivity.this, MainActivity.class);
                startActivity(toHome);
            }
        });
    }

    public void updateUser(int nbPoints){

        User currentUser = userManager.getCurrentUser();

        currentUser.setPoints(currentUser.getPoints() + nbPoints);
        currentUser.setCurrentLevel(currentUser.getCurrentLevel() + 1);
        currentUser.setCurrentQuestion(0);

        currentUser = userManager.updateUser(currentUser);
        userManager.setCurrentUser(currentUser);
        application.notify(nbPoints + " points ont été ajoutés");
    }
}
