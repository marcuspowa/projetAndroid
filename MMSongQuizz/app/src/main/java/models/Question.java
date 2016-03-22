package models;

import interfaces.IQuestion;

/**
 * Created by remy on 22/03/2016.
 */
public class Question implements IQuestion{
    private String question;
    private String response;

    public Question(String question, String response){
        super();
        this.question = question;
        this.response = response;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public boolean checkResponse(String response) {
        return this.response.equals(response);
    }

}
