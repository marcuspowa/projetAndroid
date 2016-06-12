package models;

import interfaces.IQuestion;
import utils.QuestionType;

/**
 * Created by remy on 12/05/2016.
 */
public class ImageQuestion implements IQuestion {
    private Artist artist;

    public ImageQuestion(Artist artist) {
        this.artist = artist;
    }

    @Override
    public QuestionType getType() {
        return QuestionType.IMAGE;
    }

    @Override
    public String getQuestion() {
        return "Qui est-ce ?";
    }

    @Override
    public String getResponse() {

        return this.artist.getName();
    }

    @Override
    public boolean checkResponse(String response) {

        return response.equals(getResponse());
    }


    public String getUrlImage() {
        return artist.getImageUrl();
    }

}
