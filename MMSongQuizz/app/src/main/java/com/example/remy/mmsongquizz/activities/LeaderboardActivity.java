package com.example.remy.mmsongquizz.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.UI.GenreAdapter;
import com.UI.UserAdapter;
import com.example.remy.mmsongquizz.R;

import java.util.ArrayList;

import models.Genre;
import models.User;
import services.UserManager;

public class LeaderboardActivity extends BaseActivity {
    private Button homeBtn;
    private ListView leaderList;

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        checkNetwork();

        homeBtn = (Button) findViewById(R.id.leaderboard_home_btn);
        leaderList = (ListView) findViewById(R.id.leaderboard_list);


        userManager = application.getContainer().get(UserManager.class);

        initView();
    }

    private void initView(){
        ArrayList<User> users = userManager.getleaderBoard();

        leaderList.setAdapter(new UserAdapter(this, android.R.layout.simple_list_item_2, users.toArray(new User[users.size()])));

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHome = new Intent(LeaderboardActivity.this, MainActivity.class);
                startActivity(toHome);
            }
        });
    }
}
