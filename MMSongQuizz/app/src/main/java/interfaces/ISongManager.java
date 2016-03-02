package interfaces;

import models.Song;

/**
 * Created by remy on 02/03/2016.
 */
public interface ISongManager {

    Song get(String title);
    Song GetRandom();
}
