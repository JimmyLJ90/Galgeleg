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
public class Help_frag extends Fragment {


    public Help_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_help_frag, container, false);

        ((TextView)root.findViewById(R.id.textView5)).setText("" +
                "I Galgeleg gælder det om at " +
                "redde en mand fra at blive hængt ved " +
                "at gætte et tilfældigt ord - Ét bogstav ad gangen.\n\n" +
                "Hver gang du gætter forkert bliver han tegnet mere og mere op," +
                " hvis han er fuldt optegnet og du gætter forkert har du tabt.");
        return root;
    }

}
