package services;

import java.util.ArrayList;

import javax.inject.Inject;

import interfaces.IQuestion;
import models.Artist;
import models.Genre;
import models.Question;
import models.Track;
import utils.Logger;

/**
 * Created by remy on 22/03/2016.
 */
public class QuestionManager {
    private GenreManager genreManager;
    private ArtistManager artistManager;
    private TrackManager trackManager;


    @Inject
    public QuestionManager(GenreManager genreManager, ArtistManager artistManager, TrackManager trackManager){
        this.genreManager = genreManager;
        this.artistManager = artistManager;
        this.trackManager = trackManager;
    }

    public IQuestion getRandomQuestion(){
        Genre genre = null;
        Artist artist = null;
        Track track = null;

        genre = genreManager.getRandom();
        Logger.debug("random genre: " + genre.getName());
        while(null == artist){
            artist = artistManager.getRandombyGenre(genre);
            Logger.debug("random Artist: " + artist.getName());
        }

        while(null == track){
            track = trackManager.getRandom(artist);
            Logger.debug("tracks id:" + track.getId()+" title:" + track.getTitle()+ " spotifyId:" + track.getSpotifyId());
        }


        return createQuestion(artist, track);
    }

    private IQuestion createQuestion(Artist artist, Track track){

        Question question = new Question("Quel artiste interprète '"+track.getTitle()+"'?", artist.getName());


        return question;
    }



}
