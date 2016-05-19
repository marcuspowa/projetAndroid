package models;

import android.text.TextUtils;

import java.util.ArrayList;

import interfaces.IQuestion;
import utils.QuestionType;

/**
 * Created by remy on 22/03/2016.
 */
public class BasicQuestion implements IQuestion{
    private Artist artist;
    private ArrayList<Track> tracks;

    public BasicQuestion(Artist artist, ArrayList<Track> tracks) {
        this.artist = artist;
        this.tracks = tracks;
    }

    @Override
    public QuestionType getType() {
        return QuestionType.BASIC;
    }

    @Override
    public String getQuestion() {
        ArrayList<String> titles = new ArrayList<>();
        for(Track track : tracks){
            titles.add(track.getTitle());
        }
        return "Qui interprète les titres : " + TextUtils.join(" , ", titles);
    }

    @Override
    public String getResponse() {
        return this.artist.getName();
    }

    @Override
    public boolean checkResponse(String response) {
        return this.getResponse().equals(response);
    }

}
