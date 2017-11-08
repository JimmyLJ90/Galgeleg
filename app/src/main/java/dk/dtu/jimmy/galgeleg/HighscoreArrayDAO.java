package dk.dtu.jimmy.galgeleg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jimmy on 04-11-2017.
 */

public class HighscoreArrayDAO implements IHighscoreDAO {

    private List<HighscoreDTO> highscores; // this is sorted by ID's
    private List<HighscoreDTO> sortedHighscores; // this is sorted by score
    private int nextAvailableID;
    private boolean sorted = false;
    public HighscoreArrayDAO()
    {
        nextAvailableID = 0;
        highscores = new ArrayList<>();
        addHighscore(new HighscoreDTO("Anders",10 ));
        addHighscore(new HighscoreDTO("Bente" , 2 ));
        addHighscore(new HighscoreDTO("Charlie" , 27 ));
        addHighscore(new HighscoreDTO("Daniel" , 1 ));
        addHighscore(new HighscoreDTO("Ellen" , 30 ));
        addHighscore(new HighscoreDTO("Frank" , 22));
        addHighscore(new HighscoreDTO("Grethe" , 10));
    }
    @Override
    public List<HighscoreDTO> getAllHighscores() {


        if(!sorted)
        {
            sortedHighscores = highscores.subList(0 , highscores.size());
            sortedHighscores = sortByScore(sortedHighscores);
            sorted = true;
        }
        return sortedHighscores;
    }

    //Sorts an HighscoreDTO Array List by the Highscores score from highest to lowest, uses mergesort
    private List<HighscoreDTO> sortByScore(List<HighscoreDTO> a)
    {
        if(a.size() < 2)
            return merge(a , new ArrayList<HighscoreDTO>());
        List<HighscoreDTO> a1 = a.subList(0 , a.size()/2);
        List<HighscoreDTO> a2 = a.subList(a.size()/2 , a.size());
        return merge(sortByScore(a1) , sortByScore(a2));

    }

    private List<HighscoreDTO> merge(List<HighscoreDTO> a1 , List<HighscoreDTO> a2)
    {
        int size = a1.size()+a2.size();
        int a1Pointer = 0 ,a2Pointer = 0;
        List<HighscoreDTO> result = new ArrayList<>(size);
        for(int i = 0 ; i<size ; i++)
        {
            if(a1Pointer == a1.size())
                result.add(a2.get(a2Pointer++));

            else if(a2Pointer == a2.size())
                result.add(a1.get(a1Pointer++));

            else
                if(a1.get(a1Pointer).getScore() > a2.get(a2Pointer).getScore())
                    result.add(a1.get(a1Pointer++));
                else
                    result.add(a2.get(a2Pointer++));

        }
        return result;

    }

    @Override
    public void addHighscore(HighscoreDTO dto) {
        if(dto.getID() == -1)
        {
            int ID = nextAvailableID;
            dto.setID(ID);
            highscores.add(ID , dto);
            sorted = false;
            findNextID();
        }
    }

    @Override
    public void deleteHighscore(int ID) {
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
