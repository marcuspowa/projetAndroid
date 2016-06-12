package com.example.remy.mmsongquizz.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.remy.mmsongquizz.R;

/**
 * Created by Mickael on 02/06/2016.
 */
public class EndSessionActivity extends BaseActivity {
    private Button homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endsession);

        checkNetwork();

        homeBtn = (Button) findViewById(R.id.endsession_home_btn);

        initView();
    }



    public void initView(){
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHome = new Intent(EndSessionActivity.this, MainActivity.class);
                startActivity(toHome);
            }
        });
    }
}
