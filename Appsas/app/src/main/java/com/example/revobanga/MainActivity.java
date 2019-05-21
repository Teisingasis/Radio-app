package com.example.revobanga;

import android.content.Intent;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeTransform;
import android.util.Log;
import android.view.MenuItem;

import java.util.HashMap;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import com.example.revobanga.Chat.ChatActivity;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    public static Player player,player2;

    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public static MediaPlayer mediaPlayer2 = new MediaPlayer();
    public static MemberData member;
    public static Station1Fragment fragment;
    public static Station2Fragment fragment2;
    private TextView txtName;
    private TextView txtEmail;
    private SQLiteHandler db;
    private SessionManager session;
    private long backPressedTime;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private static final String TAG = MainActivity.class.getSimpleName();
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


        player = new Player(mediaPlayer, getString(R.string.link1),1);
        fragment = (Station1Fragment) getSupportFragmentManager().findFragmentByTag("station1");
        player.stationInitialize(getString(R.string.link1));
        fragment2 = (Station2Fragment) getSupportFragmentManager().findFragmentByTag("station2");
        player2 = new Player(mediaPlayer2, getString(R.string.link1),2);
        player2.stationInitialize(getString(R.string.link1));
        // editText = (EditText) findViewById(R.id.editText);
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }
        };


        // --------------------------------------
        // DATABASE
        // --------------------------------------
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

//        txtName = (TextView) findViewById(R.id.name);
//        txtEmail = (TextView) findViewById(R.id.email);
//
//        // SqLite database handler
//        db = new SQLiteHandler(getApplicationContext());
//
//        // session manager
//        session = new SessionManager(getApplicationContext());
//
//        if (!session.isLoggedIn()) {
//            logoutUser();
//        }
//
//        // Fetching user details from sqlite
//        HashMap<String, String> user = db.getUserDetails();
//
//        String name = user.get("name");
//        String email = user.get("email");
//
//        // Displaying the user details on the screen
//        txtName.setText(name);
//        txtEmail.setText(email);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Button prof=findViewById(R.id.profile);
        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
            }
        });
        switch (item.getItemId()) {
            case R.id.nav_radioStations:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StationsFragment()).commit();
                break;
            case R.id.nav_chat:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatFragment()).commit();
                startActivity(new Intent(MainActivity.this, ChatActivity.class));
                break;
            case R.id.nav_favorites:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavoritesFragment()).commit();
                break;
            case R.id.nav_settings:
              //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                //startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.nav_timetable:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimetableFragment()).commit();
                break;
            case R.id.logout:
                firebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
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
            if (backPressedTime + 2000 > System.currentTimeMillis()){
                //super.onBackPressed();

                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
            else {
                Toast.makeText(getBaseContext(), "Double press to exit", Toast.LENGTH_SHORT).show();
            }

            backPressedTime = System.currentTimeMillis();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}
