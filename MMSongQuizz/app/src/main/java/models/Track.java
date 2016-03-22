package models;

import javax.inject.Inject;

/**
 * Created by remy on 07/03/2016.
 */
public class Track {
    private int id;
    private String title;


    public Track(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
