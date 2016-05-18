package models;

import interfaces.IQuestion;
import utils.QuestionType;

/**
 * Created by remy on 12/05/2016.
 */
public class SoundQuestion implements IQuestion {
    private Artist artist;
    private Track track;

    public SoundQuestion(Artist artist, Track track) {
        this.artist = artist;
        this.track = track;
    }

    @Override
    public QuestionType getType() {
        return QuestionType.SOUND;
    }

    public Track getTrack() {
        return track;
    }

    @Override
    public String getQuestion() {
        return "Qui interprète cet extrait ?";
    }

    @Override
    public boolean checkResponse(String response) {
        return artist.getName().equals(response);
    }
}
