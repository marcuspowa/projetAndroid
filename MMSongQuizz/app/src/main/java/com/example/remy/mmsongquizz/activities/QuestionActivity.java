package com.example.remy.mmsongquizz.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private Button playerStart;
    private Button playerPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        checkNetwork();

        questionManager = application.getContainer().get(QuestionManager.class);

        questionTextView = (TextView) findViewById(R.id.questionTextView);
        responseInput = (EditText) findViewById(R.id.questionResponseInput);
        submitBtn = (Button) findViewById(R.id.questionSubmitBtn);
        messageText = (TextView) findViewById(R.id.questionMessageText);
        playerLayout = (LinearLayout) findViewById(R.id.player_layout);
        playerStart = (Button) findViewById(R.id.player_play_btn);
        playerPause = (Button) findViewById(R.id.player_pause_btn);




        initView();
    }

    private void initView(){
        setCurrentQuestion(questionManager.getRandomQuestion());

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String response = responseInput.getText().toString();
                if (currentQuestion.checkResponse(response)) {
                    application.notify("Réponse correcte !");
                    setCurrentQuestion(questionManager.getRandomQuestion());
                } else {
                    messageText.setText("Réponse incorrecte !");
                }
            }
        });

        playerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionActivity.this.getmPlayer().seekToPosition(0);
                QuestionActivity.this.getmPlayer().resume();
            }
        });
        playerPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionActivity.this.getmPlayer().pause();
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

        if(currentQuestion.getType().equals(QuestionType.SOUND)){
            playerLayout.setVisibility(LinearLayout.VISIBLE);
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
