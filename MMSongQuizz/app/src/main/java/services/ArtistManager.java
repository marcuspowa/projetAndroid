package services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import models.Artist;
import models.Genre;
import models.Track;
import utils.AsyncHttpRequest;
import utils.EchonestUtils;
import utils.HttpUtils;
import utils.Logger;

/**
 * Created by remy on 22/03/2016.
 */
public class ArtistManager {
    private HttpUtils httpUtils;

    @Inject
    public ArtistManager(HttpUtils httpUtils){
        this.httpUtils = httpUtils;
    }

    public Artist get(String title) {
        return null;
    }

    public Artist getRandom() {
        Artist artist = new Artist("1", "lynyrd skynyrd");
        artist.getTracks().add(new Track(1, "free bird"));
        artist.getTracks().add(new Track(2, "sweet home alabama"));
        artist.getTracks().add(new Track(3, "last rebel"));
        return artist;
    }

    public ArrayList<Artist> getByGenre(String genreName){
        ArrayList<Artist> artists = new ArrayList<>();
        HashMap<String, String> params = new HashMap<>();
        params.put("api_key",EchonestUtils.API_KEY);
        params.put("genre",genreName);
        params.put("bucket","songs");

        String url = EchonestUtils.BASE_URL+"artist/search?"+HttpUtils.concatParams(params);

        AsyncHttpRequest req = httpUtils.asyncRequest(url);

        String requestResult = req.GetResult();
        boolean success = EchonestUtils.getSuccessFromReponse(requestResult);
        if(!success){
            Logger.warn("[ArtistManager] response error (url:" + url+")");
            return artists;
        }
        try {
            JSONObject jsonObject = new JSONObject(requestResult);
            JSONObject jsonResponse = jsonObject.getJSONObject("response");
            JSONArray jsonArtists = jsonResponse.getJSONArray("artists");

            for (int i = 0; i < jsonArtists.length(); i++) {
                JSONObject jsonArtist = jsonArtists.getJSONObject(i);
                Artist artist = new Artist(jsonArtist.getString("id"), jsonArtist.getString("name"));
                artists.add(artist);
            }
        } catch (JSONException e) {
            Logger.error("[GenreManager] json error", e);
        }
        return artists;
    }

}
