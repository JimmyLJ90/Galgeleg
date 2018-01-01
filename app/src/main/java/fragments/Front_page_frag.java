package fragments;


import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

import dk.dtu.jimmy.galgeleg.DownloadWordsTask;
import dk.dtu.jimmy.galgeleg.GalgelegApp;
import dk.dtu.jimmy.galgeleg.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Front_page_frag extends Fragment{


    ProgressDialog dialog;
    TextView wordListInfo;
    public Front_page_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_front_page_frag, container, false);
        wordListInfo = (TextView)root.findViewById(R.id.textView3);

        String lastUpdated = getDate(Long.
                parseLong(GalgelegApp.getInstance().
                        prefs.getString("lastUpdate","0")));

        wordListInfo.setText(
                String.format("Din ordliste indeholder: %d ord\nSidste opdatering af liste: %s",
                        GalgelegApp.getInstance().wordList.size() , lastUpdated));


        root.findViewById(R.id.button).setOnClickListener((View v) -> {
            Fragment frag = new Challenge_friend_list_frag();

            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_content,frag)
                    .addToBackStack("game")
                    .commit();
        });


        root.findViewById(R.id.button2).setOnClickListener((View v) -> {
            Fragment frag = new Game_main_frag();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_content , frag)
                    .addToBackStack("game")
                    .commit();
        });

        root.findViewById(R.id.button3).setOnClickListener((View v) -> {
            dialog = ProgressDialog.show(getActivity(), "",
                    "Indlæser ord fra DR...", true);

            new DownloadWordsTask().execute(this);
        });


        return root;
    }

    private String getDate(long timestamp)
    {
        if(timestamp == 0)
            return "Har ikke opdateret";
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(timestamp);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;

    }

    public void finishedDownloading()
    {
        dialog.cancel();

        GalgelegApp.getInstance()
                .prefs.edit()
                .putStringSet("wordList" , new HashSet<String>(GalgelegApp.getInstance().logic.getMuligeOrd()))
                .putString("lastUpdate" , ""+System.currentTimeMillis())
                .apply();
        GalgelegApp.getInstance().wordList = GalgelegApp.getInstance().logic.getMuligeOrd();
        wordListInfo.setText(
                String.format("Din ordliste indeholder: %d ord\nSidste opdatering af liste: %s",
                        GalgelegApp.getInstance().wordList.size() , getDate(System.currentTimeMillis()))
        );

    }
}
