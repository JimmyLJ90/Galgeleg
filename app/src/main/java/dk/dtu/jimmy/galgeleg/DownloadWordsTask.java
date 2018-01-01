package dk.dtu.jimmy.galgeleg;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fragments.Front_page_frag;
import fragments.Game_main_frag;

/**
 * Created by Jimmy on 30-12-2017.
 */

public class DownloadWordsTask extends AsyncTask
{

    private Front_page_frag caller;

    @Override
    protected Object doInBackground(Object[] objects) {
        caller = (Front_page_frag) objects[0];
        GalgelegApp.getInstance().downloading = true;
        try
        {
            GalgelegApp.getInstance().logic.hentOrdFraDr();
            System.out.println("Hentede ord fra DR");
        }
        catch (Exception e)
        {
            System.out.println("Kunne ikke hente ord fra DR");
        }
        GalgelegApp.getInstance().downloading = false;
        return null;
    }

    @Override
    protected void onPostExecute(Object result) {
        caller.finishedDownloading();

    }
}
