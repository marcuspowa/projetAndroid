package models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import utils.Logger;

/**
 * Created by remy on 08/03/2016.
 */
public class Genre implements Serializable {
    private static final long serialVersionUID = 65613243;
    private String name;

    public Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public static Genre createFromJson(JSONObject jsonObject) throws JSONException {
        return new Genre(jsonObject.getString("name"));
    }

    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
        } catch (JSONException e) {
            Logger.warn("Genre to JSON error",e);
        }
        return json;
    }

    public static Genre fromJson(JSONObject json){
        try {
            return new Genre(json.getString("name"));
        } catch (JSONException e) {
            Logger.warn("Genre fromoJSON error", e);
        }
        return null;
    }

}
