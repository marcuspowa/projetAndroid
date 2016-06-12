package models;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import utils.Logger;

/**
 * Created by Mickael on 12/03/2016.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 12131415;
    private int id;
    private String name;
    private String password;
    private int points;
    private int currentLevel;
    private int currentQuestion;
    private ArrayList<Genre> preferedGenres;

    public User(){
        preferedGenres = new ArrayList<>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Genre> getPreferedGenres() {
        return preferedGenres;
    }

    public void setPreferedGenres(ArrayList<Genre> preferedGenres) {
        this.preferedGenres = preferedGenres;
    }

    public int getPoints() {
        return points;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        try {
            json.put("id", this.id);
            json.put("name", this.name);
            json.put("password", this.password);
            json.put("currentLevel", this.currentLevel);
            json.put("currentQuestionNumber", this.currentQuestion);
            json.put("points", this.points);
            ArrayList<String> genreNames = new ArrayList<>();
            for(Genre genre : preferedGenres){
                genreNames.add(genre.getName());
            }
            json.put("preferedGenres", TextUtils.join(",", genreNames));
        } catch (JSONException e) {
            Logger.warn("User To Json error",e);
        }
        return json;
    }

    public static User fromJson(JSONObject jsonUser){
        User user = new User();
        try {
            user.setId(jsonUser.getInt("id"));
            user.setName(jsonUser.getString("name"));
            user.password = jsonUser.getString("password");
            user.points = jsonUser.getInt("points");
            user.currentLevel = jsonUser.getInt("currentLevel");
            user.currentQuestion = jsonUser.getInt("currentQuestionNumber");
            String genres = jsonUser.getString("preferedGenres");
            for(String genre : genres.split(",")) {
                user.getPreferedGenres().add(new Genre(genre));
            }
        } catch (JSONException e) {
            Logger.warn("User From Json error",e);
        }
        return user;
    }
    public static User fromJsonString(String json){
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(json);
        } catch (JSONException e) {
            Logger.warn("User From Json String error ", e);
            return null;
        }
        return fromJson(jsonObj);
    }

}
