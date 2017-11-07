package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import dk.dtu.jimmy.galgeleg.HighscoreArrayDAO;
import dk.dtu.jimmy.galgeleg.HighscoreDTO;
import dk.dtu.jimmy.galgeleg.IHighscoreDAO;
import dk.dtu.jimmy.galgeleg.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Highscore_list_frag extends Fragment {


    public Highscore_list_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_highscore_list_frag, container, false);
        getActivity().setTitle("Highscore");
        IHighscoreDAO highDao = new HighscoreArrayDAO();

        ArrayAdapter<HighscoreDTO> arrayAdapter = new ArrayAdapter<HighscoreDTO>(getActivity() , R.layout.highscore_element , R.id.textView9, highDao.getAll());
        ((ListView)root.findViewById(R.id.listView)).setAdapter(arrayAdapter);
        return root;
    }

}
