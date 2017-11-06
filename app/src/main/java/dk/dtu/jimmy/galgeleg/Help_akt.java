package dk.dtu.jimmy.galgeleg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Help_akt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_akt);


        ((TextView)findViewById(R.id.textView5)).setText("" +
                "I Galgeleg gælder det om at " +
                "redde en mand fra at blive hængt ved " +
                "at gætte et tilfældigt ord - Ét bogstav ad gangen.\n\n" +
                "Hver gang du gætter forkert bliver han tegnet mere og mere op," +
                " hvis han er fuldt optegnet og du gætter forkert har du tabt.");
    }
}
