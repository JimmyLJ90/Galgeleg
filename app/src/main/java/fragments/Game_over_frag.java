package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dk.dtu.jimmy.galgeleg.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Game_over_frag extends Fragment {


    public Game_over_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_game_over_frag, container, false);
        Bundle args = getArguments();
        String message;
        if(args.getBoolean("won"))
        {
            int tries = args.getInt("tries");
            String forkert = tries == 1?"forkert":"forkerte";

            message = "Tilykke! Du vandt med "+tries+ " "+forkert+" gæt";
        }
        else
        {
            String word = args.getString("word");
            message = "Du tabte, ordet var \""+word+"\"";
        }
        ((TextView)root.findViewById(R.id.game_over_text)).setText(message);
        return root;
    }

}
