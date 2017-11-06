package dk.dtu.jimmy.galgeleg;

import java.util.ArrayList;

/**
 * Created by Jimmy on 04-11-2017.
 */

public class HighscoreArrayDAO implements IHighscoreDAO {

    private ArrayList<HighscoreDTO> highscores;
    private int nextAvailableID;
    public HighscoreArrayDAO()
    {
        nextAvailableID = 0;
        highscores = new ArrayList<>();
        add(new HighscoreDTO("Anders",10 ));
        add(new HighscoreDTO("Bente" , 2 ));
        add(new HighscoreDTO("Charlie" , 27 ));
        add(new HighscoreDTO("Daniel" , 1 ));
        add(new HighscoreDTO("Ellen" , 30 ));
        add(new HighscoreDTO("Frank" , 22));
        add(new HighscoreDTO("Grethe" , 10));
    }
    @Override
    public ArrayList<HighscoreDTO> getAll() {

        return highscores;
    }

    @Override
    public void add(HighscoreDTO dto) {
        if(dto.getID() == -1)
        {
            int ID = nextAvailableID;
            dto.setID(ID);
            highscores.add(ID , dto);
            findNextID();
        }
    }

    @Override
    public void delete(int ID) {
        for(int i = 0 ; i<highscores.size() ; i++)
            if(highscores.get(i).getID() == ID)
            {
                highscores.remove(i);
                if(ID < nextAvailableID)
                    nextAvailableID = ID;
                break;
            }
    }

    private void findNextID()
    {
        if(nextAvailableID == highscores.size()-1)
            nextAvailableID++;
        else
        {
            for(int i = nextAvailableID+1 ; i<highscores.size() ; i++)
            {
                if(highscores.get(i).getID() != highscores.get(i-1).getID()+1)
                {
                    nextAvailableID = highscores.get(i-1).getID()+1;
                    break;
                }
            }
        }


    }
}
