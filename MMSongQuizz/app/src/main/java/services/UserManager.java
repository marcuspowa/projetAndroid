package services;

import com.example.remy.mmsongquizz.activities.MMQuizzApplication;

import javax.inject.Inject;

import models.User;
import utils.Logger;

/**
 * Created by remy on 19/05/2016.
 */
public class UserManager {

    private static User currentUser;
    public static final String UserCacheKey = "MMSongQuizzCurrentUser";

    private CacheManager cacheManager;

    @Inject
    public UserManager(CacheManager cacheManager){
        this.cacheManager = cacheManager;
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



}
