package models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by remy on 08/03/2016.
 */
public class Artist {
    private String id;
    private String name;
    private Genre genre;
    private ArrayList<Track> tracks;

    public Artist(String id, String name){
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getId() {
        return id;
    }


    public static Artist createFromJson(JSONObject jsonObject) throws JSONException {
        Artist artist = new Artist(jsonObject.getString("id"), jsonObject.getString("name"));
        return artist;
    }
}
