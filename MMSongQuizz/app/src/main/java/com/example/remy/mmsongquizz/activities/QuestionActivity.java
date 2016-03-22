package com.example.remy.mmsongquizz.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.remy.mmsongquizz.R;

import javax.inject.Inject;

import interfaces.IQuestion;
import services.QuestionManager;

public class QuestionActivity extends BaseActivity {
    private QuestionManager questionManager;
    private IQuestion currentQuestion;
    private TextView questionTextView;
    private EditText responseInput;
    private Button submitBtn;
    private TextView messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questionManager = application.getContainer().get(QuestionManager.class);

        questionTextView = (TextView) findViewById(R.id.questionTextView);
        responseInput = (EditText) findViewById(R.id.questionResponseInput);
        submitBtn = (Button) findViewById(R.id.questionSubmitBtn);
        messageText = (TextView) findViewById(R.id.questionMessageText);

        initView();
    }

    private void initView(){
        currentQuestion = questionManager.getRandomQuestion();

        questionTextView.setText(currentQuestion.getQuestion());

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String response = responseInput.getText().toString();
                if(currentQuestion.checkResponse(response)){
                    application.notify("Réponse correcte !");
                    messageText.setText("");
                }
                else{
                    messageText.setText("Réponse incorrecte !");
                }
            }
        });

    }





}
