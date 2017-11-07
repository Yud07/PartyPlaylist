package wristonfroze.partyplaylist;

/**
 * Created by myudy on 11/6/2017.
 */

public class Song {
    public String name;
    public String artist;
    public boolean selected;
    public int score;
    // Add URL

    public Song(String name, String artist) {
        this.name = name;
        this.artist = artist;
        this.selected = false;
        this.score = 0;
    }
}
