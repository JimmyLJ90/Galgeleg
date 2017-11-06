package dk.dtu.jimmy.galgeleg;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.app.PendingIntent.getActivity;

public class Menu_akt extends AppCompatActivity implements View.OnClickListener {


    private Button helpButton;
    private Button gameButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameButton = (Button)findViewById(R.id.gameButton);
        helpButton = (Button)findViewById(R.id.helpButton);
        gameButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v == gameButton)
        {
            startActivity(new Intent(this, Game_akt.class));
            System.out.println("Der blev trykket på Start spil knappen");
        }
        else if(v == helpButton)
        {
            startActivity(new Intent(this, Help_akt.class));
            System.out.println("Der blev trykket på Hjælp knappen");
        }

    }


}
