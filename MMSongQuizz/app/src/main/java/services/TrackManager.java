package services;

import javax.inject.Inject;

import interfaces.ITrackManager;
import models.Track;

/**
 * Created by remy on 02/03/2016.
 */
public class TrackManager implements ITrackManager {

    @Inject
    public TrackManager(){
    }

    @Override
    public Track get(String title) {
        return null;
    }

    @Override
    public Track GetRandom() {
        return null;
    }
}
