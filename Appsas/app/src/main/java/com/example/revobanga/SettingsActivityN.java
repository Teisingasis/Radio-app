package com.example.revobanga;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SettingsActivityN extends AppCompatActivity {

    Button deleteButton, backButton;
    FirebaseAuth firebaseAuth;

    TextView tekstas;
    Switch colorSw;
    ConstraintLayout ekranas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ekranas = (ConstraintLayout)findViewById(R.id.ekranas);
        /*tekstas = findViewById(R.id.spalvos_tekstas);
        colorSw = (Switch)findViewById(R.id.color_switch);*/
        deleteButton = (Button)findViewById(R.id.rm_acc_button);
        firebaseAuth = FirebaseAuth.getInstance();

        /*colorSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ekranas.setBackgroundColor(Color.GREEN);
                    tekstas.setText("Ne baltas");
                }
                else{
                    ekranas.setBackgroundColor(Color.WHITE);
                    tekstas.setText("Baltas");
                }
            }
        });*/

        backButton = (Button)findViewById(R.id.settings_back); //Back button
        backButton.setOnClickListener(new View.OnClickListener() { //Back button pressed
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        delete();

    }

    //Account delete
    private void delete(){
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(SettingsActivityN.this);
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Deleting this account will result in completely removing your profile from the system.");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(SettingsActivityN.this, "Account Deleted", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(SettingsActivityN.this, LoginActivity.class));
//                                    if(MainActivity.player.mediaPlayer.isPlaying()){
//                                        MainActivity.player.mediaPlayer.stop();
//                                    }else if(MainActivity.player2.mediaPlayer.isPlaying()){
//                                        MainActivity.player2.mediaPlayer.stop();
//                                    }
//                                    else {
                                    MainActivity.Reset(MainActivity.player.mediaPlayer,MainActivity.player2.mediaPlayer);
                                  //  }
                                    finish();
                                }
                                else{
                                    //Toast.makeText(SettingsActivityN.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    Toast.makeText(SettingsActivityN.this, "Re-log to delete your account", Toast.LENGTH_LONG).show();

                                    // **** Google account sign out nepatvarkyta **** //
                                    /* Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                            new ResultCallback<Status>() {
                                                @Override
                                                public void onResult(Status status) {
                                                    startActivity(new Intent(SettingsActivityN.this, LoginActivity.class));
                                                    finish();
                                                }
                                            });*/


                                    firebaseAuth.signOut();
                                    MainActivity.Reset(MainActivity.player.mediaPlayer,MainActivity.player2.mediaPlayer);
                                    startActivity(new Intent(SettingsActivityN.this, LoginActivity.class));
                                }
                            }
                        });

                    }
                });

                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = dialog.create();
                alertDialog.show();


            }
        });
    }
}
