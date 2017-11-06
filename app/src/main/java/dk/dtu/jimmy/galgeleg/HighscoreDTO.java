package dk.dtu.jimmy.galgeleg;

/**
 * Created by Jimmy on 04-11-2017.
 */

public class HighscoreDTO {

    private String name;
    private int score;
    private int ID = -1;



    public HighscoreDTO(String name , int score)
    {
        this.score = score;
        this.name = name;
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
}
