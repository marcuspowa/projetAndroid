package services;

import javax.inject.Inject;

import interfaces.IQuestion;
import models.Artist;
import models.Question;
import models.Track;

/**
 * Created by remy on 22/03/2016.
 */
public class QuestionManager {
    private ArtistManager artistManager;
    private TrackManager trackManager;


    @Inject
    public QuestionManager(ArtistManager artistManager, TrackManager trackManager){
        this.artistManager = artistManager;
        this.trackManager = trackManager;
    }

    public IQuestion getRandomQuestion(){
        Artist randomArtist = artistManager.getRandom();
        Track randomTrack = trackManager.getRandom(randomArtist);


        return createQuestion(randomArtist, randomTrack);
    }

    private IQuestion createQuestion(Artist artist, Track track){

        Question question = new Question("Quel artiste interprète '"+track.getTitle()+"'?", artist.getName());


        return question;
    }



}
