package com.UI;

import android.widget.Button;

/**
 * Created by remy on 15/06/2016.
 */
public class QuestionLetterState {
    public static final String EmptyCharacter = "_";
    private String responseLetter;
    private String answeredLetter;
    private boolean locked;
    private int clickedButtonIndex;

    public QuestionLetterState(String responseLetter){
        this.responseLetter = responseLetter;
        this.answeredLetter = EmptyCharacter;
        this.locked = false;
    }

    public String getResponseLetter() {
        return responseLetter;
    }

    public String getAnsweredLetter() {
        return answeredLetter;
    }

    public void setAnsweredLetter(String answeredLetter) {
        this.answeredLetter = answeredLetter;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public int getClickedButtonIndex() {
        return clickedButtonIndex;
    }

    public void setClickedButtonIndex(int clickedButtonIndex) {
        this.clickedButtonIndex = clickedButtonIndex;
    }
}
