package com.example.revobanga;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
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

import	android.text.TextUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.HashMap;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    public static MediaPlayer mediaPlayer;
    public static MediaPlayer mediaPlayer2;
    public static Station1Fragment fragment;
    public static Station2Fragment fragment2;
    private TextView txtName;
    private TextView txtEmail;

    private SQLiteHandler db;
    private SessionManager session;
    private long backPressedTime;
    public static String screen = "stations";
    FirebaseAuth firebaseAuth;
    GoogleApiClient mGoogleApiClient;
    private FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference usRef;
    String username,currentUserID;
    FirebaseUser user;
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
        mediaPlayer = new MediaPlayer();
        mediaPlayer2 = new MediaPlayer();
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
        currentUserID=firebaseAuth.getCurrentUser().getUid();
        GetUser();

        Intent intent = new Intent( getApplicationContext(), MediaPlayerService.class );
        intent.setAction( MediaPlayerService.ACTION_PAUSE );
        startService( intent );

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
       ImageView prof=findViewById(R.id.profile);
        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
            }
        });
        switch (item.getItemId()) {
            case R.id.nav_profile:
                user=FirebaseAuth.getInstance().getCurrentUser();
                if (user.isAnonymous()){
                    Toast.makeText(getApplicationContext(),"You have to be a registered user",Toast.LENGTH_SHORT).show();
                }
                screen = "profile";
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
            case R.id.nav_radioStations:
                screen = "stations";
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StationsFragment()).commit();
                break;
            case R.id.nav_chat:
                user=FirebaseAuth.getInstance().getCurrentUser();
                if(username != null && !username.isEmpty()) {
                    startActivity(new Intent(MainActivity.this, ChatActivity.class));
                }
                else if(user.isAnonymous()){
                    Toast.makeText(getApplicationContext(),"You have to be a registered user",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"You have to have username to enter",Toast.LENGTH_SHORT).show();
                }
                break;
//            case R.id.nav_favorites:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavoritesFragment()).commit();
//                break;
            case R.id.nav_settings:
              //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                //startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                startActivity(new Intent(MainActivity.this, SettingsActivityN.class));
                break;
//            case R.id.nav_timetable:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimetableFragment()).commit();
//                break;
            case R.id.nav_support:
                Intent supportIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.me/rokjan"));
                startActivity(supportIntent);
                break;
            case R.id.nav_facebook:
                Intent supportIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/RevoBanga-2103338803291279/"));
                startActivity(supportIntent1);
                break;
            case R.id.logout:
                user=FirebaseAuth.getInstance().getCurrentUser();
                if (user.isAnonymous()){
                    if (currentUserID != null && username != null) {
                        DatabaseReference drUsers = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID);
                        drUsers.removeValue();
                    }
                    user.delete();
                }

                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // ...
                                Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(MainActivity.this,LoginActivity.class);
                                startActivity(i);
                                Intent myService = new Intent(MainActivity.this, MediaPlayerService.class);
                                stopService(myService);
//                                if(player.mediaPlayer.isPlaying()){
//                                    player.mediaPlayer.stop();
//                                }else if(player2.mediaPlayer.isPlaying()){
//                                    player2.mediaPlayer.stop();
//                                }
                                Reset(player.mediaPlayer,player2.mediaPlayer);
                                finish();
                            }
                        });
                    firebaseAuth.signOut();

                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                Intent myService = new Intent(MainActivity.this, MediaPlayerService.class);
                stopService(myService);

                Reset(player.mediaPlayer,player2.mediaPlayer);
                finish();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public static void Reset(MediaPlayer play,MediaPlayer play2){
        play.reset();
        play.reset();
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if ( screen == "stations" ){
                //stationsFrag
                if (backPressedTime + 2000 > System.currentTimeMillis()){
                    //super.onBackPressed();
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isAnonymous() /*&& username.isEmpty()*/){

                        firebaseAuth.signOut();
                        user.delete();


                    }

                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
                else {
                    Toast.makeText(getBaseContext(), "Double press to exit", Toast.LENGTH_SHORT).show();
                }

                backPressedTime = System.currentTimeMillis();
            }
            else {
                screen = "stations";
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StationsFragment()).commit();
            }

        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
    public void GetUser(){

        usRef= FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        usRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String myUserName=dataSnapshot.child("username").getValue().toString();
                    username=myUserName;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public static void setHideKeyboardOnTouch(final Context context, View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        try {
            //Set up touch listener for non-text box views to hide keyboard.
            if (!(view instanceof EditText || view instanceof ScrollView)) {

                view.setOnTouchListener(new View.OnTouchListener() {

                    public boolean onTouch(View v, MotionEvent event) {
                        InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        in.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        return false;
                    }

                });
            }
            //If a layout container, iterate over children and seed recursion.
            if (view instanceof ViewGroup) {

                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                    View innerView = ((ViewGroup) view).getChildAt(i);

                    setHideKeyboardOnTouch(context, innerView);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
