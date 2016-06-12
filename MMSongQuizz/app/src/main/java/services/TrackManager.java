package services;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import utils.SpotifyUtils;

/**
 * Created by remy on 02/03/2016.
 */
public class TrackManager {
    private HttpUtils httpUtils;

    @Inject
    public TrackManager(HttpUtils httpUtils){
        this.httpUtils = httpUtils;
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
        params.put("country","FR");

        String url = null;
        url = SpotifyUtils.BASE_URL+"artists/" + artist.getId() +"/top-tracks/?" + HttpUtils.concatParams(params);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization","Bearer "+SpotifyUtils.currentToken);
        AsyncHttpRequest req = httpUtils.asyncRequest(url, headers);

        String requestResult = req.GetResult();
        try {
            JSONObject jsonObject = new JSONObject(requestResult);
            JSONArray jsonTracks = jsonObject.getJSONArray("tracks");

            for (int i = 0; i < jsonTracks.length(); i++) {
                JSONObject jsonTrack = jsonTracks.getJSONObject(i);
                Track track = Track.createFromJson(jsonTrack);
                track.setArtist(artist);
                tracks.add(track);
            }

        } catch (JSONException e) {
            Logger.error("[TrackManager] json error", e);
        }
        return tracks;
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
