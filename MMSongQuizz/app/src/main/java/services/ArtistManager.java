package services;

import java.util.ArrayList;

import javax.inject.Inject;

import models.Artist;
import models.Track;

/**
 * Created by remy on 22/03/2016.
 */
public class ArtistManager {

    @Inject
    public ArtistManager(){

    }

    public Artist get(String title) {
        return null;
    }

    public Artist getRandom() {
        Artist artist = new Artist(1, "lynyrd skynyrd");
        artist.getTracks().add(new Track(1, "free bird"));
        artist.getTracks().add(new Track(2, "sweet home alabama"));
        artist.getTracks().add(new Track(3, "last rebel"));
        return artist;
    }

}
