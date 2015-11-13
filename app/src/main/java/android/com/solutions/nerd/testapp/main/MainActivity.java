package android.com.solutions.nerd.testapp.main;

import android.com.solutions.nerd.testapp.R;
import android.com.solutions.nerd.testapp.boat.BoatFragment;
import android.com.solutions.nerd.testapp.search.SearchActivity;
import android.com.solutions.nerd.testapp.servlet.ServletPostAsyncTask;
import android.com.solutions.nerd.testapp.ui.BaseActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        new ServletPostAsyncTask().execute(new Pair<Context, String>(this, "Manfred"));

        // Set Initial fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, BoatFragment.getInstance())
                .commit();

    }


    @Override
    public boolean onSearchRequested() {
        Log.i(TAG, "onSearchRequested");
        startActivity(new Intent(this, SearchActivity.class));
        return true;
    }

}
