package dk.dtu.jimmy.galgeleg;


import java.util.List;

/**
 * Created by Jimmy on 04-11-2017.
 */

public interface IHighscoreDAO {

    List<HighscoreDTO> getAllHighscores();
    void addHighscore(HighscoreDTO dto);
    void deleteHighscore(int ID);
}
