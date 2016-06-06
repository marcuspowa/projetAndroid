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
    private String idSpotify;

    public Artist(String id, String name, String idSpotify){
        this.id = id;
        this.name = name;
        this.idSpotify = idSpotify;
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

    public String getIdSpotify() {
        return idSpotify;
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

        if(!jsonObject.has("foreign_ids")){
            Logger.error("[ARTIST]: ERREUR pas de foreign_ids");
        }else{
            JSONArray arraySpotifyId = jsonObject.getJSONArray("foreign_ids");
            String spotifyid = null;
            if(arraySpotifyId.length() >0){
                JSONObject objectSpotifyId = arraySpotifyId.getJSONObject(0);
                spotifyid = objectSpotifyId.getString("foreign_id");
                spotifyid = spotifyid.split(":")[2];
            }

            artist = new Artist(jsonObject.getString("id"), jsonObject.getString("name"),spotifyid);
        }


        return artist;
    }
}
