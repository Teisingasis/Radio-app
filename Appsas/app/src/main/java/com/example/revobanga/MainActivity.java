package com.example.revobanga;

import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import android.os.AsyncTask;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    public static Player player;
    public static Player player2;
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public static MediaPlayer mediaPlayer2 = new MediaPlayer();
    ArrayList<UserEntry> mContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StationsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_radioStations);
        }
        player = new Player(mediaPlayer, getString(R.string.link1));
        player.stationInitialize(getString(R.string.link1));
        player2 = new Player(mediaPlayer2, getString(R.string.link1));
        player2.stationInitialize(getString(R.string.link1));

        //prepareContent();
       //new WebTask().execute(new String[] {""});
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_radioStations:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StationsFragment()).commit();
                break;
            case R.id.nav_chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatFragment()).commit();
                break;
            case R.id.nav_favorites:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavoritesFragment()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingFragment()).commit();
                break;
            case R.id.nav_timetable:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimetableFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void prepareContent()
    {
        mContents = new ArrayList<>();

        UserDataBaseHandler dbhandler = new UserDataBaseHandler(this);

        dbhandler.addEntry(new UserEntry(0, "Jonas", "aaa"));
        dbhandler.addEntry(new UserEntry(0, "Juozas", "111"));
        dbhandler.addEntry(new UserEntry(0, "Petras", "test"));

        ArrayList<UserEntry> entries = dbhandler.getAllEntries();
        mContents = entries;
    }

    /*class WebTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            StringBuilder builder = new StringBuilder();
            if(params.length > 0) {
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    InputStream in = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    while((line = reader.readLine()) != null)
                    {
                        builder.append(line);
                    }
                    con.disconnect();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            return builder.toString();

        }

        @Override
        protected void onPostExecute(String s)
        {
            parseJson(s);
        }
    }

    public void parseJson(String json)
    {
        try {
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("Users_Connection_Info");
            if(mContents != null) {
                mContents.clear();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    UserEntry entry = new UserEntry(i, obj.getString("name"), obj.getString("password"));
                    mContents.add(entry);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }*/
}
