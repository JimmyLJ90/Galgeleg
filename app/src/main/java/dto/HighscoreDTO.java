package dto;

/**
 * Created by Jimmy on 04-11-2017.
 */

public class HighscoreDTO {

    private String name;
    private int score;
    private long timestamp;
    private String word;



    public HighscoreDTO(String name , int score , long timestamp, String word)
    {
        this.score = score;
        this.name = name;
        this.timestamp = timestamp;
        this.word = word;
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


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
