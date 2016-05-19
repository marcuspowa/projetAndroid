package models;

import java.util.ArrayList;

/**
 * Created by Mickael on 12/03/2016.
 */
public class User {
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

}
