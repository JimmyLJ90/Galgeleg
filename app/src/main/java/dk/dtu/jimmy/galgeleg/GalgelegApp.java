package dk.dtu.jimmy.galgeleg;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import galgeleg_logik.Galgelogik;

/**
 * Created by Jimmy on 30-12-2017.
 */

public class GalgelegApp extends Application {
    private static GalgelegApp ourInstance;
    public static GalgelegApp getInstance() {
        return ourInstance;
    }

    public SharedPreferences prefs;
    public Galgelogik logic;
    public List<String> wordList;
    public boolean downloading = false;

    @Override
    public void onCreate() {

        super.onCreate();
        ourInstance = this;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        logic = new Galgelogik();
        Set<String> wordListSet = prefs.getStringSet("wordList" , new HashSet<String>());

        if(wordListSet.size()== 0)
            wordList = logic.getMuligeOrd();
        else
            wordList = new ArrayList<String>(wordListSet);

        logic.setMuligeOrd(wordList);



    }

}
