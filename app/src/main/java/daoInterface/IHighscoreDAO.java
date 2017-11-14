package daoInterface;


import java.util.List;

import dto.HighscoreDTO;

/**
 * Created by Jimmy on 04-11-2017.
 * Will only contain the top 200 highscores
 */

public interface IHighscoreDAO {

    List<HighscoreDTO> getAllHighscores();
    List<HighscoreDTO> getSortedHighscores();
    void addHighscore(HighscoreDTO dto);
}
