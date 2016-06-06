package services;

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

    public ArrayList<Artist> getByGenre(Genre genre){
        Logger.error("BIIIIITE");
        if(null == genre){
            throw new IllegalArgumentException("genre is null");
        }
        ArrayList<Artist> artists = new ArrayList<>();
        HashMap<String, String> params = new HashMap<>();
        params.put("api_key",EchonestUtils.API_KEY);
        params.put("genre",genre.getName());
        params.put("bucket","songs");
        params.put("results","100");


        String url = EchonestUtils.BASE_URL+"artist/search?"+HttpUtils.concatParams(params)+"&bucket=id:spotify";

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
                Artist artist = Artist.createFromJson(jsonArtist);
                artist.setGenre(genre);
                artists.add(artist);
            }
        } catch (JSONException e) {
            Logger.error("[ArtistManager] json error "+e.getMessage(), e);
        }

        return artists;
    }

    public ArrayList<Artist> getTopByGenre(Genre genre){
        if(null == genre){
            throw new IllegalArgumentException("genre is null");
        }
        ArrayList<Artist> artists = new ArrayList<>();
        HashMap<String, String> params = new HashMap<>();
        params.put("api_key",EchonestUtils.API_KEY);
        params.put("genre",genre.getName());
        params.put("results","100");


        String url = EchonestUtils.BASE_URL+"artist/top_hottt?"+HttpUtils.concatParams(params)+"&bucket=id:spotify";

        AsyncHttpRequest req = httpUtils.asyncRequest(url);

        String requestResult = req.GetResult();
        Logger.debug(requestResult);
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
                        Artist artist = Artist.createFromJson(jsonArtist);
                        artist.setGenre(genre);
                        artists.add(artist);
            }

        } catch (JSONException e) {
            Logger.error("[ArtistManager] json error fff"+e.getMessage(), e);
            Logger.error("URL utilisé ARTISTE MANAGER : "+url);
        }
        return artists;
    }

    public String getImageUrl(Artist artist){
        String spotifyId = artist.getIdSpotify();
        String url = "https://api.spotify.com/v1/artists/"+spotifyId;
        AsyncHttpRequest req = httpUtils.asyncRequest(url);
        String requestResult = req.GetResult();

        try {
            JSONObject jsonObject = new JSONObject(requestResult);
            JSONArray imageArray = jsonObject.getJSONArray("images");
            JSONObject imageObject = imageArray.getJSONObject(0);
            String imageUrl = imageObject.getString("url");
            Logger.error("URLImage : "+imageUrl);
            return imageUrl;

        } catch (JSONException e) {
            Logger.error("[ArtistManager] [IMAGE URL] json error "+e.getMessage(), e);
        }

        return null;
    }

}
