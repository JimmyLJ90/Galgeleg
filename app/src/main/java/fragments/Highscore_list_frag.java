package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return root;
    }

}
