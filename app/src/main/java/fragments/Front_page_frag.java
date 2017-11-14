package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import dk.dtu.jimmy.galgeleg.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Front_page_frag extends Fragment implements View.OnClickListener {


    public Front_page_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_front_page_frag, container, false);
        root.findViewById(R.id.button2).setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        Fragment frag = new Game_main_frag();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content , frag)
                .commit();
    }
}
