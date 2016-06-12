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
    private String uri;
    private Artist artist;


    public Track(String id, String title, String uri) {
        this.id = id;
        this.title = title;
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public String getUri() {
        return uri;
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

    public static Track createFromJson(JSONObject jsonObject) throws JSONException {
        Track track = new Track(jsonObject.getString("id"), formatTitle(jsonObject.getString("name")), jsonObject.getString("uri"));

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
