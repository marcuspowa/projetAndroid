package com.example.remy.mmsongquizz.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.remy.mmsongquizz.R;

import javax.inject.Inject;

import interfaces.IQuestion;
import models.SoundQuestion;
import services.QuestionManager;
import utils.Logger;
import utils.QuestionType;

public class QuestionActivity extends AbstractSpotifyActivity {
    private QuestionManager questionManager;
    private IQuestion currentQuestion;
    private TextView questionTextView;
    private EditText responseInput;
    private Button submitBtn;
    private TextView messageText;
    private LinearLayout playerLayout;
    private ImageButton playerStartBtn;
    private ImageButton playerRestartBtn;
    private boolean isplaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        checkNetwork();

        isplaying = true;
        questionManager = application.getContainer().get(QuestionManager.class);

        questionTextView = (TextView) findViewById(R.id.questionTextView);
        responseInput = (EditText) findViewById(R.id.questionResponseInput);
        submitBtn = (Button) findViewById(R.id.questionSubmitBtn);
        messageText = (TextView) findViewById(R.id.questionMessageText);
        playerLayout = (LinearLayout) findViewById(R.id.player_layout);
        playerStartBtn = (ImageButton) findViewById(R.id.question_play_button);
        playerRestartBtn = (ImageButton) findViewById(R.id.question_restart_button);

        setCurrentQuestion(questionManager.getRandomQuestion());

        initView();
    }

    private void initView(){
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String response = responseInput.getText().toString();
                getmPlayer().pause();
                if (currentQuestion.checkResponse(response)) {
                    application.notify("Réponse correcte !");
                    setCurrentQuestion(questionManager.getRandomQuestion());
                } else {
                    messageText.setText("Réponse incorrecte !");
                }
            }
        });

        playerStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               
                if(isplaying){
                    isplaying=false;
                    QuestionActivity.this.getmPlayer().pause();
                    QuestionActivity.this.playerStartBtn.setImageResource(R.mipmap.play);
                }else{
                    isplaying=true;
                    QuestionActivity.this.getmPlayer().resume();
                    QuestionActivity.this.playerStartBtn.setImageResource(R.mipmap.pause);
                }
            }
        });
        playerRestartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionActivity.this.getmPlayer().seekToPosition(0);
                QuestionActivity.this.getmPlayer().resume();
            }
        });

    }

    @Override
    public void onTokenReceived() {
        if(currentQuestion.getType().equals(QuestionType.SOUND)){
            SoundQuestion soundQuestion = (SoundQuestion) currentQuestion;
            this.startPlayer(soundQuestion.getTrack().getSpotifyId());
        }
    }

    private void setCurrentQuestion(IQuestion question){
        currentQuestion = question;
        questionTextView.setText(currentQuestion.getQuestion());
        messageText.setText("");
        responseInput.setText(currentQuestion.getResponse()); //TODO REMOVE PREFILLED RESPONSE

        if(currentQuestion.getType().equals(QuestionType.SOUND)){
            playerLayout.setVisibility(LinearLayout.VISIBLE);
            this.playerStartBtn.setImageResource(R.mipmap.pause);
            this.isplaying=true;
            authenticateSpotify();
        }
        else {
            playerLayout.setVisibility(LinearLayout.GONE);
        }
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String errorDetails) {
        Logger.debug("Playback error received: " + errorType.name());
        switch (errorType) {
            case TRACK_UNAVAILABLE :
                setCurrentQuestion(questionManager.getQuestion(QuestionType.SOUND));
                SoundQuestion soundQuestion = (SoundQuestion) currentQuestion;
                this.startPlayer(soundQuestion.getTrack().getSpotifyId());
                break;
            default:
                break;
        }
    }





}
