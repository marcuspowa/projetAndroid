package services;

import com.example.remy.mmsongquizz.activities.MMQuizzApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import models.Artist;
import models.Track;
import models.User;
import utils.AsyncHttpPostRequest;
import utils.AsyncHttpRequest;
import utils.EchonestUtils;
import utils.HttpUtils;
import utils.Logger;

/**
 * Created by remy on 19/05/2016.
 */
public class UserManager {

    private static String MMSongQuizzApiHost = "http://192.168.1.19/api/";
    private static User currentUser;
    public static final String UserCacheKey = "MMSongQuizzCurrentUser";

    private CacheManager cacheManager;
    private HttpUtils httpUtils;

    @Inject
    public UserManager(CacheManager cacheManager, HttpUtils httpUtils){
        this.cacheManager = cacheManager;
        this.httpUtils = httpUtils;
    }

    public User getCurrentUser(boolean getFromCache) {
        if(getFromCache){
            currentUser = cacheManager.getObject(MMQuizzApplication.getContext(), UserManager.UserCacheKey);
        }
        return currentUser;
    }
    public User getCurrentUser() {
        return getCurrentUser(false);
    }

    public void setCurrentUser(User user, boolean setInCache){
        if(setInCache){
            cacheManager.setObject(MMQuizzApplication.getContext(), UserCacheKey, user);
        }
        currentUser = user;
    }
    public void setCurrentUser(User user){
        setCurrentUser(user, true);
    }

    public User addUser(String username, String password){
        User user = new User();
        user.setName(username);
        user.setPassword(password);

        String url = MMSongQuizzApiHost+"user/";

        AsyncHttpPostRequest req = httpUtils.asyncPostRequest(url, user.toJson());

        String requestResult = req.GetResult();
        try {
            JSONObject jsonObject = new JSONObject(requestResult);
            boolean success = jsonObject.getBoolean("success");
            if(!success){
                String message = jsonObject.getString("message");
                Logger.warn("[UserManager] response error (url:" + url+") - " + message);
                return null;
            }
            JSONObject userJson = jsonObject.getJSONObject("data");
            User updatedUser = User.fromJson(userJson);
            return updatedUser;

        } catch (JSONException e) {
            Logger.error("[UserManager] json error", e);
        }
        return null;
    }

    public User updateUser(User user){
        HashMap<String, String> params = new HashMap<>();

        String url = MMSongQuizzApiHost+"user/"+user.getId();

        AsyncHttpPostRequest req = httpUtils.asyncPostRequest(url, user.toJson());

        String requestResult = req.GetResult();
        try {
            JSONObject jsonObject = new JSONObject(requestResult);
            boolean success = jsonObject.getBoolean("success");
            if(!success){
                String message = jsonObject.getString("message");
                Logger.warn("[UserManager] response error (url:" + url+") - " + message);
                return null;
            }
            JSONObject userJson = jsonObject.getJSONObject("data");
            User updatedUser = User.fromJson(userJson);
            return updatedUser;

        } catch (JSONException e) {
            Logger.error("[UserManager] json error", e);
        }
        return null;
    }

    public User checkCredentials(String login, String password){
        HashMap<String, String> params = new HashMap<>();
        params.put("name", login);
        params.put("password", password);

        String url = MMSongQuizzApiHost+"user/CheckCredentials?"+ HttpUtils.concatParams(params);

        AsyncHttpRequest req = httpUtils.asyncRequest(url);

        String requestResult = req.GetResult();
        try {
            JSONObject jsonObject = new JSONObject(requestResult);
            boolean success = jsonObject.getBoolean("success");
            if(!success){
                String message = jsonObject.getString("message");
                Logger.warn("[UserManager] response error (url:" + url+") - " + message);
                return null;
            }
            JSONObject userJson = jsonObject.getJSONObject("data");
            User user = User.fromJson(userJson);
            return user;

        } catch (JSONException e) {
            Logger.error("[UserManager] json error", e);
        }
        return null;
    }


    public ArrayList<User> getAll(){
        ArrayList<User> users = new ArrayList<>();


        String url = MMSongQuizzApiHost+"user";

        AsyncHttpRequest req = httpUtils.asyncRequest(url);

        String requestResult = req.GetResult();
        try {
            JSONObject jsonObject = new JSONObject(requestResult);
            boolean success = jsonObject.getBoolean("success");
            if(!success){
                String message = jsonObject.getString("message");
                Logger.warn("[UserManager] response error (url:" + url+") - " + message);
                return users;
            }
            JSONArray jsonUsers = jsonObject.getJSONArray("data");

            for (int i = 0; i < jsonUsers.length(); i++) {
                JSONObject jsonUser = jsonUsers.getJSONObject(i);
                User user = User.fromJson(jsonUser);
                users.add(user);
            }
        } catch (JSONException e) {
            Logger.error("[UserManager] json error", e);
        }
        return users;
    }


}
