package com.example.remy.mmsongquizz.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.remy.mmsongquizz.R;

import models.User;
import services.UserManager;

public class CreateUserActivity extends BaseActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button backBtn;
    private Button submitBtn;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        checkNetwork();

        usernameInput = (EditText) findViewById(R.id.createuser_login);
        passwordInput = (EditText) findViewById(R.id.createuser_password);
        submitBtn = (Button) findViewById(R.id.createuser_submit);
        backBtn = (Button) findViewById(R.id.createuser_backBtn);

        userManager = application.getContainer().get(UserManager.class);

        initView();
    }

    private void initView(){

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCreateUser = new Intent(CreateUserActivity.this, LoginActivity.class);
                startActivity(toCreateUser);
            }
        });
    }

    private void createUser(){
        String login = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(login.length() < 1){
            application.notify("Le nom ne peut pas être vide");
            return;
        }
        if(password.length() < 3){
            application.notify("Le mot de passe doit contenir au moin 3 caractéres");
            return;
        }

        User newUser = userManager.addUser(login, password);

        if(newUser != null){
            application.notify("Utilisateur créé");
            userManager.setCurrentUser(newUser);
            Intent toCreateUser = new Intent(CreateUserActivity.this, GenreActivity.class);
            startActivity(toCreateUser);
        }
    }
}
