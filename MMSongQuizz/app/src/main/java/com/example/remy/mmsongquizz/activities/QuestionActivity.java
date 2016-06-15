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
    private String hideresponse;
    private char newReponseArray[] = new char[12];
    private int questionPoints;
    private int sessionPoints;


    private String currentResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        checkNetwork();

        sessionPoints=0;
 		this.nbQuestion =0;
        isPlaying = true;        questionManager = application.getContainer().get(QuestionManager.class);

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

        for(Button button : buttonList){
            button.setEnabled(true);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    putLetter(button);
                }
            });
        }

        setCurrentQuestion(questionManager.getRandomQuestion());

        initView();
    }

    public void submit(){
        String response = currentQuestion.getResponse();
        pausePlayer();
        if (currentQuestion.checkResponse(hideresponse)) {
            application.notify("Réponse correcte !");
            //cumul points
            sessionPoints+=questionPoints;
            setCurrentQuestion(questionManager.getRandomQuestion());
        } else {
            application.notify("Réponse incorrecte !");
//            messageText.setText("Réponse incorrecte !");
        }
    }

    private void initView(){

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearResponse();
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
        this.compteurQuestion.setText(this.nbQuestion+"/"+nbQuestionParSession);
    }

    @Override
    public void onTokenReceived() {
        if(currentQuestion.getType().equals(QuestionType.SOUND)){
            SoundQuestion soundQuestion = (SoundQuestion) currentQuestion;
            this.startPlayer(soundQuestion.getTrack().getUri());
        }
    }

    private void clearResponse(){
        int currentPosition = currentResponse.length()-1;

        if(currentPosition<0 ){
            return;
        }

        char currentChar = hideresponse.charAt(currentPosition);
        StringBuilder sb = new StringBuilder(hideresponse);

        if( currentChar == ' '){
            currentPosition --;

        }
        for(Button b : buttonList){
            Logger.error(b.getText()+" "+String.valueOf(currentChar));

            if(b.getText().toString().toUpperCase().equals(String.valueOf(currentChar)) && !b.isEnabled()){

                b.setEnabled(true);
                break;
            }
        }


        sb.setCharAt(currentPosition, '_');
        currentResponse = currentResponse.substring(0,currentPosition);
        hideresponse = sb.toString();
        responseInput.setText(hideresponse);


    }



    private void setCurrentQuestion(IQuestion question){

        hideresponse="";
        currentResponse="";

        Logger.debug("NBQUESTION :" + nbQuestion);

        if(nbQuestion == nbQuestionParSession){
            Intent toEndSession = new Intent(QuestionActivity.this, EndSessionActivity.class);
            toEndSession.putExtra(EndSessionActivity.NbPointsEarnedParamName, sessionPoints);
            startActivity(toEndSession);
            return;
        }

        currentQuestion = question;
        questionTextView.setText(currentQuestion.getQuestion());

        //HIDE RESPONSE

        String reponse = currentQuestion.getResponse().toUpperCase();
        char[] reponseArray= reponse.replaceAll(" ", "").toCharArray();
        questionPoints = reponseArray.length;
        Random rnd = new Random();
        //Complete avec lettre random
        String randomLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int j=0;
        String newReponseString="";
        for(int i = 0;i<12;i++){
            if(i>=reponseArray.length){
                newReponseArray[i]=randomLetters.charAt(rnd.nextInt(randomLetters.length()));
            }else{
                newReponseArray[i]=reponseArray[j];
                j++;
            }
            newReponseString+=newReponseArray[i];
        }

        Logger.error(newReponseString);
        Arrays.sort(newReponseArray);

        for(j=0;j<12;j++){
            buttonList.get(j).setText(String.valueOf(newReponseArray[j]));
            buttonList.get(j).setEnabled(true);
        }

        // Hide response
        for(int i=0;i<reponse.length();i++){
            if(reponse.charAt(i)!=' ' && reponse.charAt(i)!='-' ){
                hideresponse+="_";
            }else{
                hideresponse+=reponse.charAt(i);
            }
        }

        responseInput.setText(hideresponse);

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

    public void putLetter(Button button){
        if(currentResponse.length()>=currentQuestion.getResponse().length()){
            return;
        }
        StringBuilder sb = new StringBuilder(hideresponse);

        char c=button.getText().charAt(0);
        sb.setCharAt(currentResponse.length(),c);
        hideresponse = sb.toString().toUpperCase();

        currentResponse=(currentResponse+=c).toUpperCase();

        if(currentResponse.length()<hideresponse.length() && hideresponse.charAt(currentResponse.length())==' '){

            currentResponse+=" ";
        }

        button.setEnabled(false);
        responseInput.setText(hideresponse);
        if(currentResponse.length()>=currentQuestion.getResponse().length()){ // at the end
            submit();
        }

    }

    public void pausePlayer(){
        if( getmPlayer() != null){
            getmPlayer().pause();
        }
    }


}
