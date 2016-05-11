package models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by remy on 08/03/2016.
 */
public class Genre {
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
}
