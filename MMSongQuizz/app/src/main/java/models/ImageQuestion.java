package models;

import interfaces.IQuestion;
import utils.QuestionType;

/**
 * Created by remy on 12/05/2016.
 */
public class ImageQuestion implements IQuestion {


    @Override
    public QuestionType getType() {
        return QuestionType.IMAGE;
    }

    @Override
    public String getQuestion() {
        return null;
    }

    @Override
    public String getResponse() {
        return null;
    }

    @Override
    public boolean checkResponse(String response) {
        return false;
    }
}
