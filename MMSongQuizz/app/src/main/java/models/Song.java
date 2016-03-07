package models;

/**
 * Created by remy on 07/03/2016.
 */
public class Song {
    private String title;
    private String artisit;


    public Song(){
    }

    public Song(String title, String artist){
        this.title = title;
        this.artisit = artist;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtisit() {
        return artisit;
    }

    public void setArtisit(String artisit) {
        this.artisit = artisit;
    }
}
