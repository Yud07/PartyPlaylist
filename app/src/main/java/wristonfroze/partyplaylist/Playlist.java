package wristonfroze.partyplaylist;

public class Playlist {
    public String name;
    public String date;
    public boolean selected;
    // Add URL

    public Playlist(String name, String date) {
        this.name = name;
        this.date = date;
        this.selected = false;
    }
}
