package com.example.remy.mmsongquizz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.UI.QuestionModel;
import com.UI.ResponseButtonClickListener;
import com.example.remy.mmsongquizz.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import interfaces.IQuestion;
import models.ImageQuestion;
import models.SoundQuestion;
import services.QuestionManager;
import utils.Logger;
import utils.QuestionType;

public class QuestionActivity extends AbstractSpotifyActivity {
    private QuestionManager questionManager;
    private IQuestion currentQuestion;
    private TextView questionTextView;
    private TextView responseInput;
    private ImageButton clearBtn;
    private Button indiceBtn;
    private TextView compteurQuestion;
    private LinearLayout playerLayout;
    private ImageButton playerStartBtn;
    private ImageButton playerRestartBtn;
    private boolean isPlaying;
    private int nbQuestion;
    public static final int  nbQuestionParSession =10;
    private WebView myWebView;
    private ArrayList<Button> buttonList;
    private int sessionPoints;

    private String currentResponse;

    private QuestionModel questionModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        checkNetwork();

        sessionPoints=0;
 		this.nbQuestion =0;
        isPlaying = true;

        questionManager = application.getContainer().get(QuestionManager.class);

        questionTextView = (TextView) findViewById(R.id.questionTextView);
        responseInput = (TextView) findViewById(R.id.questionResponseInput);
        this.compteurQuestion = (TextView) findViewById(R.id.compteurQuestion);

        clearBtn = (ImageButton) findViewById(R.id.question_clearBtn);
        indiceBtn = (Button) findViewById(R.id.question_indiceBtn);

        playerLayout = (LinearLayout) findViewById(R.id.player_layout);

        playerStartBtn = (ImageButton) findViewById(R.id.question_play_button);
        playerRestartBtn = (ImageButton) findViewById(R.id.question_restart_button);

        myWebView = (WebView)findViewById(R.id.ImageWebview);

        buttonList = new ArrayList<Button>();
        buttonList.add((Button)findViewById(R.id.button1));
        buttonList.add((Button)findViewById(R.id.button2));
        buttonList.add((Button)findViewById(R.id.button3));
        buttonList.add((Button)findViewById(R.id.button4));
        buttonList.add((Button)findViewById(R.id.button5));
        buttonList.add((Button)findViewById(R.id.button6));
        buttonList.add((Button)findViewById(R.id.button7));
        buttonList.add((Button)findViewById(R.id.button8));
        buttonList.add((Button)findViewById(R.id.button9));
        buttonList.add((Button)findViewById(R.id.button10));
        buttonList.add((Button)findViewById(R.id.button11));
        buttonList.add((Button) findViewById(R.id.button12));

        for(int i=0; i<buttonList.size(); i++){
            buttonList.get(i).setEnabled(true);
            buttonList.get(i).setOnClickListener(new ResponseButtonClickListener(this, i));
        }

        setCurrentQuestion(questionManager.getRandomQuestion());

        initView();
    }

    public QuestionModel getQuestionModel() {
        return questionModel;
    }

    public ArrayList<Button> getButtonList() {
        return buttonList;
    }

    public void submit(){
        String response = currentQuestion.getResponse();
        pausePlayer();
        if (currentQuestion.checkResponse(questionModel.getResponseText())) {
            application.notify("Réponse correcte !");
            //cumul points
            sessionPoints+=questionModel.getQuestionPoints();
            setCurrentQuestion(questionManager.getRandomQuestion());
        } else {
            application.notify("Réponse incorrecte !");
//            messageText.setText("Réponse incorrecte !");
        }
    }

    public void update(){
        responseInput.setText(questionModel.getResponseText());
        if(getQuestionModel().isFullyFilled()){
            submit();
        }
    }

    private void initView(){
        indiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHintLetter();
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionModel.clearLast();
                responseInput.setText(questionModel.getResponseText());
            }
        });

        playerStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isPlaying){
                    isPlaying=false;
                    QuestionActivity.this.pausePlayer();
                    QuestionActivity.this.playerStartBtn.setImageResource(R.mipmap.play);
                }else{
                    isPlaying=true;
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
        this.compteurQuestion.setText(this.nbQuestion + "/" + nbQuestionParSession);
    }

    public void addHintLetter(){
        questionModel.putIndice();
        update();
    }

    @Override
    public void onTokenReceived() {
        if(currentQuestion.getType().equals(QuestionType.SOUND)){
            SoundQuestion soundQuestion = (SoundQuestion) currentQuestion;
            this.startPlayer(soundQuestion.getTrack().getUri());
        }
    }




    private void setCurrentQuestion(IQuestion question){

        Logger.debug("NBQUESTION :" + nbQuestion);

        if(nbQuestion == nbQuestionParSession){
            Intent toEndSession = new Intent(QuestionActivity.this, EndSessionActivity.class);
            toEndSession.putExtra(EndSessionActivity.NbPointsEarnedParamName, sessionPoints);
            startActivity(toEndSession);
            return;
        }

        currentQuestion = question;
        questionTextView.setText(currentQuestion.getQuestion());
        questionModel = new QuestionModel(this, currentQuestion);

        //HIDE RESPONSE

        char[] newReponseArray = questionModel.getButtonLettersList();
        for(int j=0;j<12;j++){
            buttonList.get(j).setText(String.valueOf(newReponseArray[j]));
            buttonList.get(j).setEnabled(true);
        }
        responseInput.setText(questionModel.getResponseText());

        if(currentQuestion.getType().equals(QuestionType.SOUND)){
            playerLayout.setVisibility(LinearLayout.VISIBLE);
            this.playerStartBtn.setImageResource(R.mipmap.pause);
            this.isPlaying=true;
            this.myWebView.setVisibility(LinearLayout.GONE);
            authenticateSpotify();
        }

        else if(currentQuestion.getType().equals(QuestionType.IMAGE)){
            myWebView.setVisibility(LinearLayout.VISIBLE);
            ImageQuestion questionImage = (ImageQuestion)currentQuestion;
            playerLayout.setVisibility(LinearLayout.GONE);

            String html = "<html><body><img src=\"" + questionImage.getUrlImage() + "\" width=\"100%\" height=\"100%\"\"/></body></html>";
            myWebView.loadData(html, "text/html", null);

        }else{
            playerLayout.setVisibility(LinearLayout.GONE);
            this.myWebView.setVisibility(LinearLayout.GONE);
        }
        this.nbQuestion++;
        this.compteurQuestion.setText(this.nbQuestion + "/" + nbQuestionParSession);
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String errorDetails) {
        Logger.debug("Playback error received: " + errorType.name());
        switch (errorType) {
            case TRACK_UNAVAILABLE :
                setCurrentQuestion(questionManager.getQuestion(QuestionType.SOUND));
                SoundQuestion soundQuestion = (SoundQuestion) currentQuestion;
                this.startPlayer(soundQuestion.getTrack().getUri());
                break;
            default:
                break;
        }
    }



    public void pausePlayer(){
        if( getmPlayer() != null){
            getmPlayer().pause();
        }
    }


}
