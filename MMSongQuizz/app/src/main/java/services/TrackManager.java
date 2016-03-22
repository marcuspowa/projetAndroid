package services;

import java.util.ArrayList;
import java.util.Random;

import javax.inject.Inject;

import models.Artist;
import models.Track;

/**
 * Created by remy on 02/03/2016.
 */
public class TrackManager {

    @Inject
    public TrackManager(){
    }

    public Track get(String title) {
        return null;
    }

    public Track getRandom() {
        return null;
    }

    public Track getRandom(Artist artist){
        ArrayList<Track> tracks = artist.getTracks();
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(tracks.size());
        Track track = tracks.get(index);

        return track;
    }
}
