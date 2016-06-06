package models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import javax.inject.Inject;

/**
 * Created by remy on 07/03/2016.
 */
public class Track implements Serializable {
    private static final long serialVersionUID = 656874978;
    private String id;
    private String title;
    private String spotifyId;
    private Artist artist;


    public Track(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }

    public String getSpotifyId() {
        return spotifyId;
    }

    public static Track createFromJson(JSONObject jsonObject) throws JSONException {
        Track track = new Track(jsonObject.getString("id"), formatTitle(jsonObject.getString("title")));
        JSONArray jsonTracks = jsonObject.getJSONArray("tracks");

        if(jsonTracks.length() > 0){
            String spotId = jsonTracks.getJSONObject(0).getString("foreign_id");
            track.setSpotifyId(spotId);
        }
        return track;
    }

    public static String formatTitle(String title){
        String newTitle="";
        int i =0;
        while(i!=title.length()){
            if(title.charAt(i)=='('){
                break;
            }
            newTitle+=title.charAt(i);
            i++;
        }
      return newTitle;
    }
}
