package services;

import android.webkit.WebView;

import java.util.ArrayList;
import java.util.Random;

import javax.inject.Inject;

import interfaces.IQuestion;
import models.Artist;
import models.Genre;
import models.BasicQuestion;
import models.ImageQuestion;
import models.SoundQuestion;
import models.Track;
import utils.AsyncHttpRequest;
import utils.EchonestUtils;
import utils.Logger;
import utils.QuestionType;

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
        return getQuestion(QuestionType.getRandom());
    }

    public IQuestion getQuestion(QuestionType type){
        Genre genre = null;
        Artist artist = null;
        ArrayList<Track> tracks;
        Random randomGenerator = new Random();

        genre = genreManager.getRandom();
        Logger.debug("random genre: " + genre.getName());
        while(null == artist){
            artist = artistManager.getRandombyGenre(genre);
            Logger.debug("random Artist: " + artist.getName());
        }
        tracks = trackManager.getByArtist(artist);
        artist.setTracks(tracks);

        if(type == QuestionType.BASIC){ // BASIC
            ArrayList<Track> tmpTracks = new ArrayList<>();
            tmpTracks.addAll(tracks);
            while (tmpTracks.size() > 3){
                tmpTracks.remove(randomGenerator.nextInt(tmpTracks.size()));
            }
            BasicQuestion question = new BasicQuestion(artist, tmpTracks);
            return question;
        }
        else if(type == QuestionType.SOUND){ // MUSIC
            Track track = trackManager.getRandom(artist);
            while (track.getSpotifyId() == null){
                track = trackManager.getRandom(artist);
            }
            SoundQuestion question = new SoundQuestion(artist, track);
            return question;
        }
        else if(type == QuestionType.IMAGE){ // IMAGE

            Logger.error(artist.getIdSpotify());
            String imageUrl = artistManager.getImageUrl(artist);
            ImageQuestion question = new ImageQuestion(artist,imageUrl);

            return question;
        }

        return null;
    }

    public ArtistManager getArtistManager() {
        return artistManager;
    }

    public void setArtistManager(ArtistManager artistManager) {
        this.artistManager = artistManager;
    }


}
