package com.example.remy.mmsongquizz.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.remy.mmsongquizz.R;

import services.UserManager;

public class ApplicationSettingsActivity extends BaseActivity {

    private EditText apiHostAddressInput;
    private CheckBox standAloneCheckbox;
    private Button submitBtn;
    private Button backBtn;
    private UserManager userManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_settings);

        apiHostAddressInput = (EditText) findViewById(R.id.appsettings_apiHost);
        standAloneCheckbox = (CheckBox) findViewById(R.id.appsettings_standalone_checkbox);
        submitBtn = (Button) findViewById(R.id.appsettings_submit);
        backBtn = (Button) findViewById(R.id.appsettings_backBtn);

        userManager = application.getContainer().get(UserManager.class);

        initView();
    }

    private void initView(){
        // pre fill fields
        String apiHostAddress = userManager.getApiHostAddress();
        apiHostAddressInput.setText(apiHostAddress);

        standAloneCheckbox.setChecked(userManager.isStandAloneMode());

        //events
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newApiHost = apiHostAddressInput.getText().toString();
                userManager.setApiHostAddress(newApiHost);

                boolean newUseStandAlone = standAloneCheckbox.isChecked();
                userManager.setStandAloneMode(newUseStandAlone);

                application.notify("paramètres mis à jour");
                Intent toLogin = new Intent(ApplicationSettingsActivity.this, LoginActivity.class);
                startActivity(toLogin);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLogin = new Intent(ApplicationSettingsActivity.this, LoginActivity.class);
                startActivity(toLogin);
            }
        });
    }
}
