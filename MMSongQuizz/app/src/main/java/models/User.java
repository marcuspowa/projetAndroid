package models;

/**
 * Created by Mickael on 12/03/2016.
 */
public class User {
    private int id;
    private String name;
    private String password;
    private int points;

    public User(){

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


}
