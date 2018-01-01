package fragments;


import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import dao.HighscorePrefManDAO;
import daoInterface.IHighscoreDAO;
import dk.dtu.jimmy.galgeleg.R;
import dto.HighscoreDTO;

/**
 * A simple {@link Fragment} subclass.
 */
public class Game_won_frag extends Fragment {


    public Game_won_frag() {
        // Required empty public constructor
    }

    private EditText input;
    private int score;
    private String word;
    private MediaPlayer winSound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_game_won_frag, container, false);

        winSound = MediaPlayer.create(getActivity() , R.raw.winner_sound);
        winSound.start();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String lastName = prefs.getString("lastHighscoreName", "");
        int tries = getArguments().getInt("tries");
        score = getArguments().getInt("score");
        word = getArguments().getString("word");


        input = (EditText) root.findViewById(R.id.winnner_message_inputname);

        ((TextView) root.findViewById(R.id.winner_message_score)).setText("Score: " + score);
        ((TextView) root.findViewById(R.id.winnner_message_inputname)).setText(lastName);
        ((TextView) root.findViewById(R.id.winner_message_wrongs)).setText("" + tries + " forkerte");

        ((Button) root.findViewById(R.id.winner_message_addToHighscore)).setOnClickListener(
                (View v) -> {
                    String name = input.getText().toString();
                    if (!name.matches("[A-Za-z0-9æøåÆØÅ]+")) {
                        input.setError("Navn kan kun indeholde bogstaver og tal");
                    } else {
                        ((Button) v).setOnClickListener(null);
                        v.animate().alpha(0.5f);
                        IHighscoreDAO highDAO = new HighscorePrefManDAO(getActivity());
                        highDAO.addHighscore(new HighscoreDTO(name, score, System.currentTimeMillis(), word));
                        prefs.edit().putString("lastHighscoreName", name).apply();
                    }
                });

        ((Button) root.findViewById(R.id.winner_message_playagain)).setOnClickListener(
                (View v) -> {
                    Fragment newGame = new Game_main_frag();
                    getActivity().getSupportFragmentManager().
                            popBackStack("popit" , FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getActivity().getSupportFragmentManager().
                            beginTransaction().
                            replace(R.id.fragment_content, newGame).
                            addToBackStack("game").
                            commit();
                });

        return root;
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        winSound.stop();
    }

}
