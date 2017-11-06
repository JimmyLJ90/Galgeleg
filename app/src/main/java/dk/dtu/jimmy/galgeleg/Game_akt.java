package dk.dtu.jimmy.galgeleg;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import galgeleg_logik.Galgelogik;

public class Game_akt extends AppCompatActivity implements View.OnClickListener {

    private Galgelogik logic = Galgelogik.getInstance();
    private LinearLayout theWord, usedLetters;
    private HashMap<Integer , Integer> gallowsMap;
    private EditText input;
    private TextView usedLettersText, gameOverText;
    private Button guessButton, cancelButton, playAgainButton;
    private ImageView gallows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_akt);


        theWord = (LinearLayout) findViewById(R.id.LinearLayout);
        usedLetters = (LinearLayout) findViewById(R.id.linearLayout);
        input = (EditText) findViewById(R.id.editText);
        usedLettersText = (TextView)findViewById(R.id.textView3);
        guessButton = (Button)findViewById(R.id.button);
        gallows = (ImageView)findViewById(R.id.imageView2);
        cancelButton = (Button)findViewById(R.id.button4);
        playAgainButton = (Button) findViewById(R.id.button3);
        gameOverText = (TextView) findViewById(R.id.textView4);

        guessButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        playAgainButton.setOnClickListener(this);

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
        try
        {
            logic.hentOrdFraDr();
            System.out.print("Det var muligt at hente ord fra dr");
        }
        catch (Exception e)
        {
            System.out.println("Kunne ikke hente ord fra DR");
            logic.nulstil();
            e.printStackTrace();
        }
        String ord = logic.getOrdet();
        logic.logStatus();
        updateScreen();


    }

    @Override
    public void onClick(View v) {
        if(v == guessButton)
        {
            String letter = input.getText().toString().toLowerCase();
            input.setText("");
            if(letter.length() != 1)
            {
                input.setError("Du skal skrive ÉT bogstav");
                return;
            }
            if(!letter.substring(0, 1).matches("[a-zæøå]"))
            {
                input.setError("Bogstav ikke godkendt");
                return;
            }
            if(logic.getBrugteBogstaver().contains(letter))
                return;
            logic.gætBogstav(letter);
            usedLettersText.setVisibility(View.VISIBLE);

            TextView addToUsedLetters = new TextView(this);
            addToUsedLetters.setText(letter);
            addToUsedLetters.setTextSize(18f);
            //Giver bogstavet en grøn farve hvis det var korrekt, og en rød farve hvis det var forkert
            if(logic.erSidsteBogstavKorrekt())
                addToUsedLetters.setTextColor(0xff00b33c);
            else
                addToUsedLetters.setTextColor(Color.RED);

            usedLetters.addView(addToUsedLetters);
            if(logic.erSpilletSlut())
                endOfGame();

            updateScreen();
        }
        else if(v == cancelButton)
        {
            finish();
        }
        else if(v == playAgainButton)
        {
            startActivity(new Intent(this, Game_akt.class));
            finish();
        }

    }

    public void endOfGame()
    {
        guessButton.setOnClickListener(null);
        input.setInputType(InputType.TYPE_NULL);
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
        cancelButton.setVisibility(View.VISIBLE);
        playAgainButton.setVisibility(View.VISIBLE);

        //Fjerner keyboardet
        InputMethodManager inputMethodManager = (InputMethodManager)  this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    public void updateScreen()
    {
        String visibleWord = logic.getSynligtOrd();
        theWord.removeAllViews();
        ArrayList<View> allLetters = new ArrayList<View>();
        for(int i = 0 ; i<visibleWord.length() ; i++)
        {
            TextView tv = new TextView(this);
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


}
