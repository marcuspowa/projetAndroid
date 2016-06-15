package services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        genres.add(new Genre("rock","Rock"));
        genres.add(new Genre("jazz","Jazz"));
        genres.add(new Genre("blues","Blues"));
        genres.add(new Genre("pop","Pop"));
        genres.add(new Genre("classical","Classique"));
        genres.add(new Genre("r&b","RnB"));
        genres.add(new Genre("metal","Métal"));
        genres.add(new Genre("rap","Rap"));
        genres.add(new Genre("hip-hop","Hip Hop"));
        genres.add(new Genre("hard-rock","Hard Rock"));
        genres.add(new Genre("electro","Electro"));
        genres.add(new Genre("folk","Folk"));
        genres.add(new Genre("country","Country"));
        genres.add(new Genre("reggae","Reggae"));
        genres.add(new Genre("soul","Soul"));
        genres.add(new Genre("french-rock","Rock français"));
        genres.add(new Genre("french-pop","Variété française"));
        genres.add(new Genre("pop-rock","Pop Rock"));

        Collections.sort(genres, new Comparator<Genre>() {
            @Override
            public int compare(Genre genre1, Genre genre2) {
                return genre1.getName().compareTo(genre2.getName());
            }
        });

        return genres;
    }
}
