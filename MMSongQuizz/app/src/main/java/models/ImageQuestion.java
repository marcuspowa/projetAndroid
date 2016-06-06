package models;

import interfaces.IQuestion;
import utils.QuestionType;

/**
 * Created by remy on 12/05/2016.
 */
public class ImageQuestion implements IQuestion {
    private Artist artist;
    private String urlImage;

    public ImageQuestion(Artist artist,String urlImage) {
        this.artist = artist;
        this.urlImage = urlImage;
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
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
