package vorlesung.hslu.wgapp;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar = null;
    NavigationView navigationView = null;
    TextView username = null;
    FirebaseAuth mAuth;
    String name = "";
    Wohngemeinschaft wg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Set initial screen
        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, new HaushaltsbuchFragment());
        transaction.commit();

     /**   username = (TextView) findViewById((R.id.textview_show_username));
        mAuth = FirebaseAuth.getInstance();
       FirebaseUser currentUser = mAuth.getCurrentUser();
        try {
            name = currentUser.getDisplayName();
        }
        catch(NullPointerException exception)
        {
            name = "Name not given";
        }
        username.setText(name.toString()); **/

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

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

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}




