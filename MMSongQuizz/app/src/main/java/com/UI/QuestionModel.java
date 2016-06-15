package com.UI;

import android.widget.Button;

import com.example.remy.mmsongquizz.activities.QuestionActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import interfaces.IQuestion;
import utils.Logger;

/**
 * Created by remy on 15/06/2016.
 */
public class QuestionModel {
    private IQuestion question;
    private ArrayList<QuestionLetterState> letterStates;
    private int currentLetterIndex; //from 0 to n-1
    private int questionPoints;
    public QuestionActivity qActivity;

    public QuestionModel(QuestionActivity qActivity,IQuestion question){
        this.qActivity = qActivity;
        this.question = question;
        this.currentLetterIndex = 0;
        letterStates = new ArrayList<>();

        String response = question.getResponse();
        char[] responseArray= response.toCharArray();
        for(char c : responseArray){
            QuestionLetterState letterState = new QuestionLetterState(String.valueOf(c));
            if(c == ' '){
                letterState.setAnsweredLetter(String.valueOf(c));
                letterState.setLocked(true);
            }
            letterStates.add(letterState);
        }
    }

    public boolean isFullyFilled(){
        for(QuestionLetterState letterState : letterStates){
            if(letterState.getAnsweredLetter().equals(QuestionLetterState.EmptyCharacter)){
                return false;
            }
        }
        return true;
    }


    public void putLetter(String letter, int btnIndex, boolean lockLetter){
        if(currentLetterIndex >= letterStates.size()){
            return;
        }
        Logger.debug("PUTTING LETTER: " + letter + " - BTN index: " + btnIndex);
        if(currentLetterIndex <= 0){
            currentLetterIndex=0;
        }
        QuestionLetterState letterState = letterStates.get(currentLetterIndex);
        if(letterState.isLocked()){
            currentLetterIndex++;
            putLetter(letter, btnIndex);
            return;
        }
        letterState.setAnsweredLetter(letter);
        letterState.setClickedButtonIndex(btnIndex);
        if(lockLetter){
            letterState.setLocked(true);
        }
        qActivity.getButtonList().get(letterState.getClickedButtonIndex()).setEnabled(false);
        currentLetterIndex++;
    }
    public void putLetter(String letter, int btnIndex){
        putLetter(letter, btnIndex, false);
    }


    public void putIndice(){
        ArrayList<QuestionLetterState> availableLetters = new ArrayList<>();
        Random rand = new Random();
        for(QuestionLetterState letterState : letterStates){
            if(!letterState.isLocked()){
                availableLetters.add(letterState);
            }
        }
        if(availableLetters.size() == 0){
            return;
        }
        QuestionLetterState indiceLetter;
        if(availableLetters.size() == 1){
            indiceLetter = availableLetters.get(0);
        }
        else{
            indiceLetter = availableLetters.get(rand.nextInt(availableLetters.size() - 1));
        }

        indiceLetter.setAnsweredLetter(indiceLetter.getResponseLetter().toUpperCase());
        indiceLetter.setLocked(true);
        Logger.debug("INDICE LETTER: " + indiceLetter.getResponseLetter());
        for(int i = 0; i<qActivity.getButtonList().size(); i++){
            Button btn = qActivity.getButtonList().get(i);
            if(btn.isEnabled() && btn.getText().toString().equalsIgnoreCase(indiceLetter.getResponseLetter())){
                indiceLetter.setClickedButtonIndex(i);
                qActivity.getButtonList().get(i).setEnabled(false);
                break;
            }
        }
        questionPoints--;
        if(questionPoints<0){
            questionPoints = 0;
        }
    }

    public int getQuestionPoints() {
        return questionPoints;
    }

    public void clearLast(){
        if(currentLetterIndex < 0){
            return;
        }
        if(currentLetterIndex >= letterStates.size()){
            currentLetterIndex=letterStates.size()-1;
        }
        QuestionLetterState letterState = letterStates.get(currentLetterIndex);
        if(letterState.isLocked()){
            currentLetterIndex--;
            clearLast();
            return;
        }
        letterState.setAnsweredLetter(QuestionLetterState.EmptyCharacter);
        qActivity.getButtonList().get(letterState.getClickedButtonIndex()).setEnabled(true);
        letterState.setClickedButtonIndex(-1);
        currentLetterIndex--;
    }

    public String getResponseText(){
        String response = "";
        for(QuestionLetterState letterState : letterStates){
            response+=letterState.getAnsweredLetter();
        }
        return response;
    }

    public char[] getButtonLettersList(){
        String response = question.getResponse().toUpperCase();
        char[] reponseArray= response.replaceAll(" ", "").toCharArray();
        questionPoints = reponseArray.length;
        Random rnd = new Random();
        //Complete avec lettre random
        String randomLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int j=0;
        String newResponseString="";
        char[] newResponseArray = new char[12];
        for(int i = 0;i<12;i++){
            if(i>=reponseArray.length){
                newResponseArray[i]=randomLetters.charAt(rnd.nextInt(randomLetters.length()));
            }else{
                newResponseArray[i]=reponseArray[j];
                j++;
            }
            newResponseString+=newResponseArray[i];
        }

        Logger.error(newResponseString);
        Arrays.sort(newResponseArray);
        return newResponseArray;
    }

}
