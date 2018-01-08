package fragments;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import dk.dtu.jimmy.galgeleg.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Game_lost_frag extends Fragment {


    private MediaPlayer loseSound;

    public Game_lost_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_game_lost_frag, container, false);
        loseSound = MediaPlayer.create(getActivity(), R.raw.loser_sound);
        if(savedInstanceState == null)
            loseSound.start();


        String word = getArguments().getString("word");

        ((TextView) root.findViewById(R.id.loser_message_word)).setText(word);

        ((Button) root.
                findViewById(R.id.loser_message_playagain)).
                setOnClickListener((View v) ->
                {
                    getActivity().getSupportFragmentManager().
                            popBackStack("popit", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getActivity().
                            getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_content, new Game_main_frag())
                            .addToBackStack("game")
                            .commit();
                });

        return root;
    }

    @Override
    public void onDestroy() {
        loseSound.stop();
        loseSound.release();
        super.onDestroy();

    }
}
