package dao;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import daoInterface.IHighscoreDAO;
import dto.HighscoreDTO;

/**
 * Created by Jimmy on 13-11-2017.
 */

public class HighscorePrefManDAO implements IHighscoreDAO {



    private SharedPreferences prefs;
    public HighscorePrefManDAO(Activity context)
    {
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context);

    }

    @Override
    public List<HighscoreDTO> getAllHighscores()
    {
        //Syntax for highscore strings
        // "NAME;SCORE;TIMESTAMP;WORD
        Set<String> stringSet = prefs.getStringSet("highscores" , new HashSet<String>());
        List<HighscoreDTO> result = new ArrayList<>(stringSet.size());
        for(String highscore : stringSet)
        {
            String[] vars = highscore.split(";");
            result.add(new HighscoreDTO(vars[0] , Integer.parseInt(vars[1]) , Long.parseLong(vars[2]) , vars[3]));
        }
        return result;
    }


    @Override
    public List<HighscoreDTO> getSortedHighscores()
    {
        return sortByScore(getAllHighscores());
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
    public void addHighscore(HighscoreDTO dto)
    {
        Set<String> stringSet = prefs.getStringSet("highscores" , new HashSet<String>());
        String strDto = dto.getName()+";"+dto.getScore()+";"+dto.getTimestamp()+";"+dto.getWord();
        if(stringSet.size() < 200)
        {
            stringSet.add(strDto);
            prefs.edit().putStringSet("highscores" , stringSet).apply();
            return;
        }

        int lowestScore = 100000;
        String strLowestscore = "";

        for(String highscore : stringSet)
        {
            int score = Integer.parseInt(highscore.split(";")[1]);
            if(score < lowestScore)
            {
                lowestScore = score;
                strLowestscore = highscore;
            }
        }

        if(lowestScore <= dto.getScore())
        {
            stringSet.remove(strLowestscore);
            stringSet.add(strDto);
            prefs.edit().putStringSet("highscores" ,stringSet).apply();
        }


    }
}
