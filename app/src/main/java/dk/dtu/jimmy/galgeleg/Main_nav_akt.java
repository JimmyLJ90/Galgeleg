package dk.dtu.jimmy.galgeleg;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import fragments.Front_page_frag;
import fragments.Game_main_frag;
import fragments.Help_frag;
import fragments.Highscore_list_frag;
import galgeleg_logik.Galgelogik;

public class Main_nav_akt extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nav_akt);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState == null)
        {
            Fragment frag = new Front_page_frag();
            FragmentTransaction fragTrans = getSupportFragmentManager().beginTransaction();
            fragTrans.add(R.id.fragment_content , frag).commit();
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;
        getSupportFragmentManager().popBackStack("popit" , FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch(id)
        {
            case R.id.nav_restart:
                Fragment activeGame = new Game_main_frag();
                GalgelegApp.getInstance().logic.nulstil();
                getSupportFragmentManager().popBackStack("game" , FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ft.replace(R.id.fragment_content , activeGame).addToBackStack("game").commit();
                break;
            case R.id.nav_highscore:
                fragment = new Highscore_list_frag();
                ft.add(R.id.fragment_content , fragment).addToBackStack("popit").commit();
                break;
            case R.id.nav_help:
                fragment = new Help_frag();
                ft.add(R.id.fragment_content , fragment).addToBackStack("popit").commit();
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
