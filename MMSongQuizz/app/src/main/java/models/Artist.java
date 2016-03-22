package models;

import java.util.ArrayList;

/**
 * Created by remy on 08/03/2016.
 */
public class Artist {
    private int id;
    private String name;
    private ArrayList<Track> tracks;

    public Artist(int id, String name){
        this.id = id;
        this.name = name;
        tracks = new ArrayList<Track>();
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
