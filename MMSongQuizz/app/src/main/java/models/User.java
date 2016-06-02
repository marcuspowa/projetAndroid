package models;

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

    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        try {
            json.put("id", this.id);
            json.put("name", this.name);
            JSONArray genres = new JSONArray();
            for(Genre g : preferedGenres){
                genres.put(g.toJson());
            }
            json.put("genres", genres);
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
            JSONArray genresJson = jsonUser.getJSONArray("genres");
            for(int i=0; i<genresJson.length(); i++) {
                user.getPreferedGenres().add(Genre.fromJson(genresJson.getJSONObject(i)));
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
