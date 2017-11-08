package dk.dtu.jimmy.galgeleg;

/**
 * Created by Jimmy on 04-11-2017.
 */

public class HighscoreDTO {

    private String name;
    private int score;
    private int ID = -1;
    private long timestamp;



    public HighscoreDTO(String name , int score)
    {
        this.score = score;
        this.name = name;
        this.timestamp = System.currentTimeMillis();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
