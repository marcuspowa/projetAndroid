package models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import utils.Logger;

/**
 * Created by remy on 08/03/2016.
 */
public class Artist implements Serializable {
    private static final long serialVersionUID = 95686985;
    private String id;
    private String name;
    private Genre genre;
    private ArrayList<Track> tracks;
    private String imageUrl;

    public Artist(String id, String name, String imageUrl){
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        tracks = new ArrayList<Track>();
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
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
        Artist artist = null;

        JSONArray imageArray = jsonObject.getJSONArray("images");
        JSONObject imageObject = imageArray.getJSONObject(0);
        String imageUrl = imageObject.getString("url");
        artist = new Artist(jsonObject.getString("id"), jsonObject.getString("name"), imageUrl);

        return artist;
    }
}
