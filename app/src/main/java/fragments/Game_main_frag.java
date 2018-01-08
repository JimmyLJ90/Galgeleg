package fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseIntArray;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dk.dtu.jimmy.galgeleg.GalgelegApp;
import dk.dtu.jimmy.galgeleg.R;
import galgeleg_logik.Galgelogik;


/**
 * A simple {@link Fragment} subclass.
 */
public class Game_main_frag extends Fragment implements AdapterView.OnItemClickListener {
    private LinearLayout theWord;
    private SparseIntArray gallowsMap;
    private ImageView gallows;
    private GridView keyboard;
    private ArrayList<String> alphabet;
    private ArrayAdapter<String> arrayAdapter;
    private View root;


    public Game_main_frag() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_game_main_frag, container, false);


        getActivity().setTitle("Galgeleg");
        gallowsMap = new SparseIntArray(7);
        gallowsMap.put(0, R.mipmap.galge_0_forkert);
        gallowsMap.put(1, R.mipmap.galge_1_forkert);
        gallowsMap.put(2, R.mipmap.galge_2_forkert);
        gallowsMap.put(3, R.mipmap.galge_3_forkert);
        gallowsMap.put(4, R.mipmap.galge_4_forkert);
        gallowsMap.put(5, R.mipmap.galge_5_forkert);
        gallowsMap.put(6, R.mipmap.galge_6_forkert);
        theWord = (LinearLayout) root.findViewById(R.id.LinearLayout);
        gallows = (ImageView) root.findViewById(R.id.imageView2);
        keyboard = (GridView) root.findViewById(R.id.game_keyboard);


        System.out.println("Args: "+ getArguments());
        if(savedInstanceState != null)
        {
            createKeyboardFromSavedState();
        }
        else if(getArguments() != null)
        {
            String word = getArguments().getString("word");
            if(word.length()>0)
                GalgelegApp.getInstance().logic.nulstil(word);
            else
                GalgelegApp.getInstance().logic.nulstil();
            createNewKeyboard();
        }
        else
        {
            GalgelegApp.getInstance().logic.nulstil();
            createNewKeyboard();
        }
        updateScreen();


        return root;
    }




    public void updateScreen() {
        Galgelogik logic = GalgelegApp.getInstance().logic;
        String visibleWord = logic.getSynligtOrd();
        theWord.removeAllViews();
        ArrayList<View> allLetters = new ArrayList<>();
        float textSize = visibleWord.length() <= 4 ? 30 : visibleWord.length() <= 10 ? 24 : 18;
        for (int i = 0; i < visibleWord.length(); i++) {
            TextView tv = new TextView(getActivity());
            String letter = "" + visibleWord.charAt(i);

            if (letter.equals("*"))
                tv.setText("_");
            else
                tv.setText(letter.toUpperCase());

            tv.setWidth(0);
            tv.setTextSize(textSize);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            theWord.addView(tv, i);
        }

        if (logic.getAntalForkerteBogstaver() <= 6)
            gallows.setImageResource(gallowsMap.get(GalgelegApp.getInstance().logic.getAntalForkerteBogstaver()));
        logic.logStatus();

    }

    public void createNewKeyboard() {


        String[] tempAlphabet = {"A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P",
                "Q", "R", "S", "T", "U", "V", "W", "X",
                "Y", "Z", "Æ", "Ø", "Å"};

        alphabet = new ArrayList<>(Arrays.asList(tempAlphabet));

        arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.keyboard_element, R.id.textView2, alphabet);
        keyboard.setAdapter(arrayAdapter);
        keyboard.setOnItemClickListener(this);
    }
    public void createKeyboardFromSavedState() {


        String[] tempAlphabet = {"A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P",
                "Q", "R", "S", "T", "U", "V", "W", "X",
                "Y", "Z", "Æ", "Ø", "Å"};

        alphabet = new ArrayList<>(Arrays.asList(tempAlphabet));

        arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.keyboard_element, R.id.textView2, alphabet);
        keyboard.setAdapter(arrayAdapter);
        keyboard.setOnItemClickListener(this);
        List<String> usedLetters  = GalgelegApp.getInstance().logic.getBrugteBogstaver();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i<usedLetters.size() ; i++)
                {
                    int pos = alphabet.indexOf(usedLetters.get(i).toUpperCase());

                    View letter = keyboard.getChildAt(pos);
                    letter.setAlpha(0.5f);
                    letter.setOnClickListener(null);
                }
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String letter = ((String) parent.getItemAtPosition(position)).toLowerCase();
        if (!GalgelegApp.getInstance().downloading) {
            view.animate().alpha(0.5f).translationZ(-4f).setDuration(19).setInterpolator(new AccelerateDecelerateInterpolator());
            view.setOnClickListener(null);
            guess(letter);

        }

    }

    public void guess(String letter) {

        Galgelogik logic = GalgelegApp.getInstance().logic;
        logic.gætBogstav(letter);
        if (logic.erSpilletSlut()) {
            Fragment gameOver;
            Bundle arg = new Bundle();
            getActivity().
                    getSupportFragmentManager().
                    popBackStack("game", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            if (logic.erSpilletTabt()) {
                gameOver = new Game_lost_frag();
                arg.putString("word", logic.getOrdet());
                gameOver.setArguments(arg);
                updateScreen();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_content, gameOver)
                        .addToBackStack("popit")
                        .commit();
            } else if (logic.erSpilletVundet()) {

                gameOver = new Game_won_frag();
                arg.putInt("tries", logic.getAntalForkerteBogstaver());
                arg.putInt("score", getScore());
                arg.putString("word", logic.getOrdet());
                gameOver.setArguments(arg);
                updateScreen();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_content, gameOver)
                        .addToBackStack("popit")
                        .commit();
            }
        }
        else
            updateScreen();

    }

    public int getScore() {
        //Arbitrary score calculation

        Galgelogik logic  = GalgelegApp.getInstance().logic;
        double scoreCoeff = ((double) getUniqueLetters(logic.getOrdet())) / logic.getOrdet().length();
        int score = (int) ((1 + 1 / Math.pow(2, logic.getAntalForkerteBogstaver())) * scoreCoeff * 100);
        return score;
    }

    private int getUniqueLetters(String word) {
        ArrayList<String> uniqueLetters = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            String letter = word.substring(i, i + 1);
            if (!uniqueLetters.contains(letter))
                uniqueLetters.add(letter);
        }
        return uniqueLetters.size();
    }


}
