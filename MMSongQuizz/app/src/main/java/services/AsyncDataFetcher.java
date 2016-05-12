package services;

import java.util.ArrayList;

import javax.inject.Inject;

import models.Artist;

/**
 * Created by remy on 12/05/2016.
 */
public class AsyncDataFetcher {
    private ArtistManager artistManager;
    private TrackManager trackManager;

    private static ArrayList<Artist> artists = new ArrayList<>();

    @Inject
    public AsyncDataFetcher(ArtistManager artistManager, TrackManager trackManager){
        this.artistManager = artistManager;
        this.trackManager = trackManager;
    }


    public void fetch(){

    }



}
