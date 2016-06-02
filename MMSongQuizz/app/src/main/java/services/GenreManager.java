package services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.inject.Inject;

import models.Genre;
import utils.AsyncHttpRequest;
import utils.EchonestUtils;
import utils.HttpUtils;
import utils.Logger;

/**
 * Created by remy on 22/03/2016.
 */
public class GenreManager {
    private static ArrayList<Genre> genreList = new ArrayList<>();
    private static boolean cached = false;

    private HttpUtils httpUtils;
    private UserManager userManager;

    @Inject
    public GenreManager(HttpUtils httpUtils, UserManager userManager){
        this.httpUtils = httpUtils;
        this.userManager = userManager;
    }

    public Genre get(String name) {
        ArrayList<Genre> genres = getAll();
        Genre result = null;
        for (Genre genre : genres) {
            if(genre.getName().equals(name)){
                result = genre;
            }
        }
        return result;
    }

    public Genre getRandom() {
//        return new Genre("rock");
//        ArrayList<Genre> genres = getAll();
        ArrayList<Genre> genres = userManager.getCurrentUser().getPreferedGenres();
        int count = genres.size();
        if(count<=0){
            return null;
        }
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(count);
        Genre genre = genres.get(index);
        return genre;
    }

    public ArrayList<Genre> getAll(){
        if(cached){
            return genreList;
        }

        ArrayList<Genre> genres = new ArrayList<>();
        HashMap<String, String> params = new HashMap<>();
        params.put("api_key", EchonestUtils.API_KEY);

        String url = EchonestUtils.BASE_URL+"genre/list?"+HttpUtils.concatParams(params);

        AsyncHttpRequest req = httpUtils.asyncRequest(url);

        String requestResult = req.GetResult();
        boolean success = EchonestUtils.getSuccessFromReponse(requestResult);
        if(!success){
            Logger.warn("[GenreManager] response error (url:" + url+")");
            return genres;
        }

        try {
            JSONObject jsonObject = new JSONObject(requestResult);
            JSONObject jsonResponse = jsonObject.getJSONObject("response");

            JSONArray jsonGenres = jsonResponse.getJSONArray("genres");
            for (int i = 0; i < jsonGenres.length(); i++) {
                JSONObject jsonGenre = jsonGenres.getJSONObject(i);
                Genre genre = Genre.createFromJson(jsonGenre);
                if(genre.getName().contains("rock")){
                    genres.add(genre);
                }
            }
        } catch (JSONException e) {
            Logger.error("[GenreManager] json error",e);
        }
        genreList.addAll(genres);
        cached = true;
        return genres;
    }
}
