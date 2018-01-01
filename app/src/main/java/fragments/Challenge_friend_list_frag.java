package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import dk.dtu.jimmy.galgeleg.GalgelegApp;
import dk.dtu.jimmy.galgeleg.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Challenge_friend_list_frag extends Fragment {


    public Challenge_friend_list_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_challenge_friend_list_frag, container, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1 ,
                GalgelegApp.getInstance().wordList);
        ((ListView)root.findViewById(R.id.listView2)).setAdapter(adapter);

        ((ListView)root.findViewById(R.id.listView2)).
                setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            getFragmentManager().popBackStack("game" , FragmentManager.POP_BACK_STACK_INCLUSIVE);
            Fragment frag = new Game_main_frag();
            Bundle bundle = new Bundle();
            bundle.putString("word" , GalgelegApp.getInstance().wordList.get(position));
            frag.setArguments(bundle);
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_content , frag)
                    .addToBackStack("game")
                    .commit();
        });

        return root;
    }


}
