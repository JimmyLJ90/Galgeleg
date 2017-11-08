package fragments;



import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import dk.dtu.jimmy.galgeleg.R;
import galgeleg_logik.Galgelogik;


/**
 * A simple {@link Fragment} subclass.
 */
public class Game_main_frag extends Fragment implements AdapterView.OnItemClickListener {

    private Galgelogik logic = Galgelogik.getInstance();
    private LinearLayout theWord;
    private SparseIntArray gallowsMap;
    private ImageView gallows;
    private GridView keyboard;
    private ArrayList<String> alphabet;
    private ArrayAdapter<String> arrayAdapter;
    private View root;
    private ProgressDialog dialog;
    private boolean ready; // used for when loading in words from the internet


    public Game_main_frag() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_game_main_frag, container, false);


        getActivity().setTitle("Galgeleg");
        theWord = (LinearLayout) root.findViewById(R.id.LinearLayout);
        gallows = (ImageView)root.findViewById(R.id.imageView2);
        keyboard = (GridView) root.findViewById(R.id.game_keyboard);


        createNewKeyboard();
        //Denne Hashmap gør det nemmere at hente det rigtige billede alt efter hvor mange bogstaver man har forkert
        gallowsMap = new SparseIntArray(7);
        gallowsMap.put(0 , R.mipmap.galge_0_forkert);
        gallowsMap.put(1 , R.mipmap.galge_1_forkert);
        gallowsMap.put(2 , R.mipmap.galge_2_forkert);
        gallowsMap.put(3 , R.mipmap.galge_3_forkert);
        gallowsMap.put(4 , R.mipmap.galge_4_forkert);
        gallowsMap.put(5 , R.mipmap.galge_5_forkert);
        gallowsMap.put(6 , R.mipmap.galge_6_forkert);



        String ord = logic.getOrdet();
        logic.logStatus();

        dialog = ProgressDialog.show(getActivity(), "",
                "Indlæser ord...", true);
        final AsyncTask task = new Game_main_frag.DownloadWordsTask().execute(this);
        return root;
    }


    //Android klager hvis AsyncTask ikke er statisk, problemer med Garbage collector aabenbart
    static class DownloadWordsTask extends AsyncTask {

        Game_main_frag game;
        @Override
        protected Object doInBackground(Object[] objects) {
            game = (Game_main_frag)objects[0];
            game.ready = false;
            try {
                game.logic.hentOrdFraDr();
                System.out.println("Hentede ord fra DR");
            } catch (Exception e) {
                System.out.println("Kunne ikke hente ord fra DR");
            }
            game.logic.nulstil();

            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            game.ready = true;
            game.dialog.cancel();
            game.updateScreen();
        }


    }

    public void newGame()
    {
        createNewKeyboard();
        logic.nulstil();
    }


    public void updateScreen()
    {
        String visibleWord = logic.getSynligtOrd();
        theWord.removeAllViews();
        ArrayList<View> allLetters = new ArrayList<>();
        float textSize = visibleWord.length() <= 4? 30 : visibleWord.length()<= 10? 24 : 18;
        for(int i = 0 ; i<visibleWord.length() ; i++)
        {
            TextView tv = new TextView(getActivity());
            String letter = ""+visibleWord.charAt(i);

            if(letter.equals("*"))
                tv.setText("_");
            else
                tv.setText(letter);

            tv.setWidth(0);
            tv.setTextSize(textSize);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT , LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            theWord.addView(tv, i);
        }

        if(logic.getAntalForkerteBogstaver()<=6)
            gallows.setImageResource(gallowsMap.get(logic.getAntalForkerteBogstaver()));
        logic.logStatus();

    }

    public void createNewKeyboard()
    {


        String[] tempAlphabet = {"A" , "B" , "C" , "D" , "E" , "F" , "G" , "H",
                             "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P",
                             "Q" , "R" , "S" , "T" , "U" , "V" , "W" , "X",
                             "Y" , "Z" , "Æ" , "Ø" , "Å"};

        alphabet =new ArrayList<>(Arrays.asList(tempAlphabet));

        arrayAdapter = new ArrayAdapter<>(getActivity() , R.layout.keyboard_element , R.id.textView2, alphabet);
        keyboard.setAdapter(arrayAdapter);
        keyboard.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String letter = ((String)parent.getItemAtPosition(position)).toLowerCase();
        if(ready)
        {
            view.animate().alpha(0.5f).translationZ(-4f).setDuration(19).setInterpolator(new AccelerateDecelerateInterpolator());
            view.setOnClickListener(null);
            guess(letter);

        }

    }

    public void guess(String letter)
    {

        logic.gætBogstav(letter);
        if(logic.erSpilletSlut())
        {
            Fragment gameOver = new Game_over_frag();
            Bundle arg = new Bundle();
            if(logic.erSpilletTabt())
            {
                arg.putString("word", logic.getOrdet());
                arg.putBoolean("won" , false);
                gameOver.setArguments(arg);
                updateScreen();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_content , gameOver)
                        .addToBackStack(null)
                        .commit();
            }
            else if(logic.erSpilletVundet())
            {
                arg.putInt("tries" , logic.getAntalForkerteBogstaver());
                arg.putBoolean("won" , true);
                gameOver.setArguments(arg);
                updateScreen();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_content , gameOver)
                        .addToBackStack(null)
                        .commit();
            }
        }
        else
            updateScreen();


    }


}
