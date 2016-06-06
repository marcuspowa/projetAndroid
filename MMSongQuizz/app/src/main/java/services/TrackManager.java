package services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.inject.Inject;

import models.Artist;
import models.Track;
import utils.AsyncHttpRequest;
import utils.EchonestUtils;
import utils.HttpUtils;
import utils.Logger;

/**
 * Created by remy on 02/03/2016.
 */
public class TrackManager {
    private HttpUtils httpUtils;

    @Inject
    public TrackManager(HttpUtils httpUtils){
        this.httpUtils = httpUtils;
    }

    public Track get(String title) {
        return null;
    }

    public Track getRandom() {
        return null;
    }

    public Track getRandom(Artist artist){
        ArrayList<Track> tracks = artist.getTracks();
        if(null == tracks || tracks.size() <= 0){
            tracks = getByArtist(artist);
        }
        int count = tracks.size();
        if(count<=0){
            return null;
        }
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(count);
        Track track = tracks.get(index);

        return track;
    }


    public ArrayList<Track> getByArtist(Artist artist){
        if(null == artist){
            throw new IllegalArgumentException("artist is null");
        }
        ArrayList<Track> tracks = new ArrayList<>();
        HashMap<String, String> params = new HashMap<>();
        params.put("api_key", EchonestUtils.API_KEY);
        params.put("artist_id", artist.getId());
        params.put("bucket", "id:spotify");
        params.put("sort", "song_hotttnesss-desc");
        params.put("song_type", "studio");
        params.put("results","100");


        String url = EchonestUtils.BASE_URL+"song/search?"+ HttpUtils.concatParams(params)+"&bucket=tracks";

        AsyncHttpRequest req = httpUtils.asyncRequest(url);

        String requestResult = req.GetResult();
        boolean success = EchonestUtils.getSuccessFromReponse(requestResult);
        if(!success){
            Logger.warn("[TrackManager] response error (url:" + url + ")");
            return tracks;
        }
        try {
            JSONObject jsonObject = new JSONObject(requestResult);
            JSONObject jsonResponse = jsonObject.getJSONObject("response");
            JSONArray jsonSongs = jsonResponse.getJSONArray("songs");

            for (int i = 0; i < jsonSongs.length(); i++) {
                JSONObject jsonTrack = jsonSongs.getJSONObject(i);
                Track track = Track.createFromJson(jsonTrack);
                track.setArtist(artist);
                tracks.add(track);
            }

        } catch (JSONException e) {
            Logger.error("[GenreManager] json error", e);
        }
        return removeDuplicatedObjects(tracks);
    }

    public ArrayList<Track> removeDuplicatedObjects(ArrayList<Track> tracks){
        ArrayList<Track> result = new ArrayList<>();
        for(Track trackToAdd : tracks){
            boolean alreadyAdded = false;
            for(Track track : result){
                if(track.getTitle().toLowerCase().replace(" ", "").equals(trackToAdd.getTitle().toLowerCase().replace(" ", ""))){
                    alreadyAdded = true;
                }
            }
            if(!alreadyAdded){
                result.add(trackToAdd);
            }
        }
        return result;
    }
}
