package com.UI;

import android.view.View;
import android.widget.Button;

import com.example.remy.mmsongquizz.activities.QuestionActivity;

/**
 * Created by remy on 16/06/2016.
 */
public class ResponseButtonClickListener implements View.OnClickListener {

    private int btnIndex;
    private QuestionActivity qActivity;

    public ResponseButtonClickListener(QuestionActivity qActivity, int btnIndex) {
        this.btnIndex = btnIndex;
        this.qActivity = qActivity;
    }

    @Override
    public void onClick(View v) {
        qActivity.getQuestionModel().putLetter(qActivity.getButtonList().get(btnIndex).getText().toString(), btnIndex);
        qActivity.update();
    }
}
