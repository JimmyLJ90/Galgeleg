package dk.dtu.jimmy.galgeleg;

import java.util.ArrayList;

/**
 * Created by Jimmy on 04-11-2017.
 */

public interface IHighscoreDAO {

    ArrayList<HighscoreDTO> getAll();
    void add(HighscoreDTO dto);
    void delete(int ID);
}
