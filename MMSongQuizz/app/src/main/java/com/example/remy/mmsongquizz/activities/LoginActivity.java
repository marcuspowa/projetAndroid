package com.example.remy.mmsongquizz.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.remy.mmsongquizz.R;

import models.User;
import services.UserManager;
import utils.Logger;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    // UI references.
    private AutoCompleteTextView loginInput;
    private EditText mPasswordView;
    private Button signInButton;
    private Button createUserBtn;
    private View mProgressView;
    private View mLoginFormView;

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkNetwork();
        // Set up the login form.
        loginInput = (AutoCompleteTextView) findViewById(R.id.login_login);
        mPasswordView = (EditText) findViewById(R.id.login_password);
        signInButton = (Button) findViewById(R.id.login_sign_in_button);
        createUserBtn = (Button) findViewById(R.id.login_createuserBtn);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        userManager = application.getContainer().get(UserManager.class);



        initView();
    }

    private void initView(){
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        createUserBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCreateUser = new Intent(LoginActivity.this, CreateUserActivity.class);
                startActivity(toCreateUser);
            }
        });

    }

    private void attemptLogin() {
        String login = loginInput.getText().toString();
        String password = mPasswordView.getText().toString();

        User user = userManager.checkCredentials(login, password);
        if(null != user){
            Logger.debug("Login activity : Credentials OK");
            userManager.setCurrentUser(user);
            Intent toHome = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(toHome);
        }
        else{
            application.notify("Credentials invalides");
        }
    }



}

