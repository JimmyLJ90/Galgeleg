package fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import dk.dtu.jimmy.galgeleg.R;
import galgeleg_logik.Galgelogik;


/**
 * A simple {@link Fragment} subclass.
 */
public class Game_main_frag extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Galgelogik logic = Galgelogik.getInstance();
    private LinearLayout theWord, usedLetters;
    private HashMap<Integer , Integer> gallowsMap;
    private TextView usedLettersText, gameOverText;
    private ImageView gallows;
    private GridView keyboard;
    private ArrayList<String> alphabet;
    private ArrayAdapter<String> arrayAdapter;


    public Game_main_frag() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_game_main_frag, container, false);


        getActivity().setTitle("Galgelg");
        theWord = (LinearLayout) root.findViewById(R.id.LinearLayout);
        usedLetters = (LinearLayout) root.findViewById(R.id.linearLayout);
        usedLettersText = (TextView)root.findViewById(R.id.textView3);
        gallows = (ImageView)root.findViewById(R.id.imageView2);
        gameOverText = (TextView) root.findViewById(R.id.textView4);
        keyboard = (GridView) root.findViewById(R.id.game_keyboard);



        //Denne Hashmap gør det nemmere at hente det rigtige billede alt efter hvor mange bogstaver man har forkert
        gallowsMap = new HashMap<Integer , Integer>();
        gallowsMap.put(0 , R.mipmap.galge_0_forkert);
        gallowsMap.put(1 , R.mipmap.galge_1_forkert);
        gallowsMap.put(2 , R.mipmap.galge_2_forkert);
        gallowsMap.put(3 , R.mipmap.galge_3_forkert);
        gallowsMap.put(4 , R.mipmap.galge_4_forkert);
        gallowsMap.put(5 , R.mipmap.galge_5_forkert);
        gallowsMap.put(6 , R.mipmap.galge_6_forkert);

        //Prøver at hente ord fra dr, hvis det ikke virker bruger vi default ord fra Galgelogik
       /* try
        {
            logic.hentOrdFraDr();
            System.out.print("Det var muligt at hente ord fra dr");
        }
        catch (Exception e)
        {
            System.out.println("Kunne ikke hente ord fra DR");

            e.printStackTrace();
        }*/

        logic.nulstil();
        String ord = logic.getOrdet();
        logic.logStatus();
        createNewKeyboard();
        updateScreen();

        return root;
    }

    @Override
    public void onClick(View v) {

    }
    public void endOfGame()
    {

        if(logic.erSpilletVundet())
        {
            gameOverText.setText("Du har vundet!");
            gameOverText.setTextColor(0xff00b33c);
        }
        else if(logic.erSpilletTabt())
        {
            gameOverText.setText("Du har tabt!");
            gameOverText.setTextColor(Color.RED);
        }
        gameOverText.setVisibility(View.VISIBLE);

    }
    public void updateScreen()
    {
        String visibleWord = logic.getSynligtOrd();
        theWord.removeAllViews();
        ArrayList<View> allLetters = new ArrayList<View>();
        for(int i = 0 ; i<visibleWord.length() ; i++)
        {
            TextView tv = new TextView(getActivity());
            String letter = ""+visibleWord.charAt(i);

            if(letter.equals("*"))
                tv.setText("_");
            else
                tv.setText(letter);

            tv.setWidth(0);
            tv.setTextSize(24f);
            tv.setLayoutParams(new LinearLayout.LayoutParams(0 , LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
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

        arrayAdapter = new ArrayAdapter(getActivity() , R.layout.keyboard_element , R.id.textView2, alphabet);
        keyboard.setAdapter(arrayAdapter);
        keyboard.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String letter = ((String)parent.getItemAtPosition(position)).toLowerCase();
        alphabet.remove(position);
        arrayAdapter.notifyDataSetChanged();
        logic.gætBogstav(letter);
        updateScreen();


    }
}
