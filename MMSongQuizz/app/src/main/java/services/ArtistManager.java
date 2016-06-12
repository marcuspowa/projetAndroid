package services;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.inject.Inject;

import models.Artist;
import models.Genre;
import models.Track;
import utils.AsyncHttpRequest;
import utils.EchonestUtils;
import utils.HttpUtils;
import utils.Logger;
import utils.SpotifyUtils;

/**
 * Created by remy on 22/03/2016.
 */
public class ArtistManager {
    private HttpUtils httpUtils;

    @Inject
    public ArtistManager(HttpUtils httpUtils){
        this.httpUtils = httpUtils;
    }


    public Artist getRandombyGenre(Genre genre) {
        ArrayList<Artist> artists = getTopByGenre(genre);
        int count = artists.size();
        if(count<=0){
            return null;
        }
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(count);
        Artist artist = artists.get(index);
        return artist;
    }

    public ArrayList<Artist> getTopByGenre(Genre genre){
        if(null == genre){
            throw new IllegalArgumentException("genre is null");
        }
        ArrayList<Artist> artists = new ArrayList<>();
        HashMap<String, String> params = new HashMap<>();
        params.put("type","artist");
//        params.put("q","genre:"+genre.getName());
        params.put("popularity","100");
        params.put("limit","50");


        String url = SpotifyUtils.BASE_URL+"search/?"+HttpUtils.concatParams(params)+"&q=genre:"+ Uri.encode(genre.getName(), "UTF-8");

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization","Bearer "+SpotifyUtils.currentToken);
        AsyncHttpRequest req = httpUtils.asyncRequest(url, headers);
        String requestResult = req.GetResult();
        try {
            JSONObject jsonObject = new JSONObject(requestResult);
            JSONObject jsonArtists = jsonObject.getJSONObject("artists");
            JSONArray jsonItems = jsonArtists.getJSONArray("items");

            for (int i = 0; i < jsonItems.length(); i++) {
                JSONObject jsonArtist = jsonItems.getJSONObject(i);
                Artist artist = Artist.createFromJson(jsonArtist);
                artist.setGenre(genre);
                artists.add(artist);
            }
        } catch (JSONException e) {
            Logger.error("[ArtistManager] json error "+e.getMessage(), e);
        }

        return artists;
    }



}
