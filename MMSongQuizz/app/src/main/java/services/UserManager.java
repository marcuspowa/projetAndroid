package services;

import javax.inject.Inject;

import models.User;

/**
 * Created by remy on 19/05/2016.
 */
public class UserManager {

    private static User currentUser;

    @Inject
    public UserManager(){

    }

    public User getCurrentUser(){
        return currentUser;
    }

    public void setCurrentUser(User user){
        currentUser = user;
    }



}
