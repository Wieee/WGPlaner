package vorlesung.hslu.wgapp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class ActivityMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private Wohngemeinschaft wg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set initial screen
        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, new HomeFragment());
        transaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.nav_view_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.findViewById(R.id.nav_view_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wohngemeinschaft.setInstance(null);
                FirebaseAuth.getInstance().signOut();
                Intent login = new Intent(ActivityMain.this, ActivityLogin.class);
                finish();
                startActivity(login);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.nav_view_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here
        int id = item.getItemId();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (id == R.id.nav_einkaufszettel) {
            transaction.replace(R.id.fragment_container, new EinkaufszettelFragment());
            transaction.addToBackStack("einkaufszettel_fragment");
            transaction.commit();
        } else if (id == R.id.nav_haushaltsbuch) {
            transaction.replace(R.id.fragment_container, new HaushaltsbuchFragment());
            transaction.addToBackStack("haushaltsbuch_fragment");
            transaction.commit();
        } else if (id == R.id.nav_putzplan) {
            transaction.replace(R.id.fragment_container, new PutzplanFragment());
            transaction.addToBackStack("putzplan_fragment");
            transaction.commit();
        } else if (id == R.id.nav_manage) {
            transaction.replace(R.id.fragment_container, new OptionsFragment());
            transaction.addToBackStack("options_fragment");
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.nav_view_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}