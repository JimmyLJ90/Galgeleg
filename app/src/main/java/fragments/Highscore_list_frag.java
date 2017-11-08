package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
        final IHighscoreDAO highDao = new HighscoreArrayDAO();

        highDao.addHighscore(new HighscoreDTO("Sebastian" , 55));
        highDao.addHighscore(new HighscoreDTO("Didrik" , 200));
        highDao.addHighscore(new HighscoreDTO("Georg" , 11));
        highDao.addHighscore(new HighscoreDTO("Sebastian" , 55));
        highDao.addHighscore(new HighscoreDTO("Didrik" , 200));
        highDao.addHighscore(new HighscoreDTO("Georg" , 11));
        highDao.addHighscore(new HighscoreDTO("Sebastian" , 55));
        highDao.addHighscore(new HighscoreDTO("Didrik" , 200));
        highDao.addHighscore(new HighscoreDTO("Georg" , 11));
        highDao.addHighscore(new HighscoreDTO("Sebastian" , 55));
        highDao.addHighscore(new HighscoreDTO("Didrik" , 200));
        highDao.addHighscore(new HighscoreDTO("Georg" , 11));
        highDao.addHighscore(new HighscoreDTO("Sebastian" , 55));
        highDao.addHighscore(new HighscoreDTO("Didrik" , 200));
        highDao.addHighscore(new HighscoreDTO("Georg" , 11));


        ArrayAdapter<HighscoreDTO> arrayAdapter = new ArrayAdapter<HighscoreDTO>(getActivity() ,
                R.layout.highscore_element , R.id.highscore_rank, highDao.getAllHighscores()){

            @Override
            public View getView(int position , View cachedView , ViewGroup parent)
            {
                View view = super.getView(position , cachedView , parent);
                TextView name = (TextView)view.findViewById(R.id.highscore_name);
                TextView score = (TextView)view.findViewById(R.id.highscore_score);
                TextView rank = (TextView)view.findViewById(R.id.highscore_rank);
                rank.setText(""+(position+1));
                name.setText("Navn: "+highDao.getAllHighscores().get(position).getName());
                score.setText("Score: "+highDao.getAllHighscores().get(position).getScore());
                return view;
            }
        };
        ((ListView)root.findViewById(R.id.listView)).setAdapter(arrayAdapter);
        return root;
    }

}
