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

    private UserManager userManager;

    @Inject
    public GenreManager(UserManager userManager){
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
        ArrayList<Genre> genres = new ArrayList<>();
        genres.add(new Genre("rock"));
        genres.add(new Genre("jazz"));
        genres.add(new Genre("blues"));


        return genres;
    }
}
