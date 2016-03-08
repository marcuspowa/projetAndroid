package interfaces;

import models.Track;

/**
 * Created by remy on 02/03/2016.
 */
public interface ITrackManager {

    Track get(String title);
    Track GetRandom();
}
